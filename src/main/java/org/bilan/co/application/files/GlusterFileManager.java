package org.bilan.co.application.files;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bilan.co.domain.dtos.user.ImportResultDto;
import org.bilan.co.domain.dtos.user.StagedImportRequestDto;
import org.bilan.co.domain.dtos.user.enums.RejectedRow;
import org.bilan.co.domain.enums.BucketName;
import org.bilan.co.utils.Constants;
import org.springframework.core.env.Environment;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

@Slf4j
public class GlusterFileManager implements IFileManager{

    private final String basePath;

    public GlusterFileManager(Environment env){
        basePath = getBasePath(env);
        log.debug("File path configured to {}", basePath);
    }

    @Override
    public Path buildPath(BucketName bucket, String subFolder, String fileName, String extension) {
        return Paths.get(
                basePath,
                bucket.getBucketName(),
                subFolder,
                fileName + extension
        );
    }

    @Override
    public boolean stageImportFile(InputStream file, BucketName bucket, String fileName, String extension) {
        var filePath = Constants.STAGED_PATH + "\\" + fileName;
        return uploadFile(file, bucket, filePath, extension);
    }

    @Override
    public boolean uploadFile(InputStream file, BucketName bucket, String fileName, String extension) {
        Path path = Paths.get(
                basePath,
                bucket.getBucketName(),
                fileName + extension
                );
        try{
            // Create parent directories if they don't exist
            Files.createDirectories(path.getParent());
            Files.copy(file, path, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            log.error("Failed to copy the file");
            return false;
        }
    }

    @Override
    public String uploadFile(Path path, InputStream inputStream) {
        File file = new File(path.toString());
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
            FileUtils.getFile(path.toString());
            return path.toString();
        } catch (IOException e) {
            log.error("Failed to save the file ", e);
            return null;
        }
    }

    @Override
    public String uploadEvidence(String fileName, InputStream inputStream) {
        Path fullPath = Paths.get(basePath, BucketName.BILAN_EVIDENCES.getBucketName(), fileName);
        return uploadFile(fullPath, inputStream);
    }

    @Override
    public String uploadTeacherEnrollment(String fileName, InputStream inputStream) {
        Path fullPath = Paths.get(basePath, BucketName.BILAN_TEACHER_ENROLLMENT.getBucketName(), fileName);
        return uploadFile(fullPath, inputStream);
    }

    @Override
    public <T> void saveVerifiedUsers(StagedImportRequestDto<T> importDto) {
        // Save valid users to be processed.
        ObjectMapper mapper = new ObjectMapper();
        try {
            byte[] json = mapper.writeValueAsBytes(importDto);
            InputStream stream = new ByteArrayInputStream(json);

            Path fullPath = Paths.get(
                    basePath,
                    importDto.getBucket().getBucketName(),
                    Constants.QUEUED_PATH,
                    importDto.getImportRequestId() + ".json");

            uploadFile(fullPath, stream);

        } catch (JsonProcessingException e) {
            log.error("Failed to store to store the json file {}", e.getMessage());
        }
    }

    @Override
    public String saveRejectedImport(ImportResultDto importResult) {
        // Nothing to write
        if (importResult.getRejectedRows().isEmpty())
            return null;

        Path fullPath = Paths.get(
                basePath,
                importResult.getBucket().getBucketName(),
                Constants.FAILED_PATH,
                importResult.getImportId() + ".csv");

        String output = importResult.getHeaders() + ", Errores" + "\n";

        output += importResult.getRejectedRows()
                .stream()
                .map(RejectedRow::getLineWithErrors)
                .collect(Collectors.joining("\n"));

        InputStream stream = new ByteArrayInputStream(output.getBytes());
        return uploadFile(fullPath, stream);
    }

    @Override
    public byte[] downloadRejected(String importId, BucketName bucket) {

        Path path = Paths.get(
                bucket.getBucketName(),
                Constants.FAILED_PATH);

        return downloadFile(path.toString(), importId + ".csv");
    }

    @Override
    public <T> T getFromJsonFile(String path, TypeReference<T> reference) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(new File(path), reference);
        } catch (IOException e) {
            log.error("Error retrieving staged import", e);
            return null;
        }

    }

    @Override
    public byte[] downloadFile(String path, String fileName) {
        Path filePath = Paths.get(basePath, path, fileName);
        try {
            log.debug("Looking for: {}", filePath);
            if(!Files.exists(filePath))
                throw new FileNotFoundException();
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("Failed to read the file");
            return null;
        }

    }

    private String getBasePath(Environment env){
        String path = env.getProperty(Constants.ENV_FILE_PATH);

        if(path == null) {
            path = Constants.DEFAULT_FILE_PATH;
        }

        return path;
    }
}
