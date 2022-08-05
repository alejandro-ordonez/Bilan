package org.bilan.co.application.files;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bilan.co.domain.enums.BucketName;
import org.bilan.co.utils.Constants;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
public class GlusterFileManager implements IFileManager{

    private final String basePath;


    public GlusterFileManager(Environment env){
        basePath = getBasePath(env);
        log.debug("File path configured to " + basePath);

    }
    
    @Override
    public void uploadFile(String path, String fileName, Map<String, String> metadata, InputStream inputStream) {

        File file = new File(basePath+ BucketName.BILAN_EVIDENCES.getBucketName(), fileName);

        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
            FileUtils.getFile(basePath+"/"+path, fileName);
        } catch (IOException e) {
            log.error("Failed to save the file ", e);
        }

    }

    @Override
    public byte[] downloadFile(String path, String fileName) {
        Path filePath = Paths.get(basePath, path);
        try {
            log.debug("Looking for: " + filePath);
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
