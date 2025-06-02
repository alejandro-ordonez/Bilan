package org.bilan.co.application.files;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bilan.co.domain.dtos.user.ImportResultDto;
import org.bilan.co.domain.dtos.user.StagedImportRequestDto;
import org.bilan.co.domain.dtos.user.enums.RejectedUser;
import org.bilan.co.domain.enums.BucketName;
import org.bilan.co.utils.Constants;
import org.springframework.core.env.Environment;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Slf4j
public class GlusterFileManager implements IFileManager{

    private final String basePath;

    public GlusterFileManager(Environment env){
        basePath = getBasePath(env);
        log.debug("File path configured to {}", basePath);
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
    public <T> String saveProcessedImport(StagedImportRequestDto<T> importDto) {
        // Save valid users to be processed.
        ObjectMapper mapper = new ObjectMapper();
        try {
            byte[] json = mapper.writeValueAsBytes(importDto);
            InputStream stream = new ByteArrayInputStream(json);

            Path fullPath = Paths.get(
                    basePath,
                    importDto.getBucket().getBucketName(),
                    Constants.SUCCESS_PATH,
                    importDto.getImportRequestId() + ".json");

            return uploadFile(fullPath, stream);

        } catch (JsonProcessingException e) {
            log.error("Failed to store to store the json file {}", e.getMessage());
            return null;
        }
    }

    @Override
    public String saveRejectedImport(ImportResultDto importResult) {
        // Nothing to write
        if (importResult.getRejectedUsers().isEmpty())
            return null;

        Path fullPath = Paths.get(
                basePath,
                importResult.getBucket().getBucketName(),
                Constants.FAILED_PATH,
                importResult.getImportId() + ".csv");

        String output = importResult.getRejectedUsers()
                .stream()
                .map(RejectedUser::getLine)
                .collect(Collectors.joining("\n"));

        InputStream stream = new ByteArrayInputStream(output.getBytes());
        return uploadFile(fullPath, stream);
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
        Path filePath = Paths.get(basePath, path);
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
