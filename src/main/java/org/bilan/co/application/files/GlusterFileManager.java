package org.bilan.co.application.files;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
public class GlusterFileManager implements IFileManager{

    private final Environment env;

    public GlusterFileManager(Environment env){
        this.env = env;
    }
    
    @Override
    public void uploadFile(String path, String fileName, Map<String, String> metadata, InputStream inputStream) {
        String basePath = env.getProperty("bilan.storage.path");
        if(basePath == null){
            throw new IllegalStateException("File Path not found in environmental variables");
        }
        basePath += "/"+path;
        File file = new File(basePath, fileName);

        try {
            FileUtils.copyInputStreamToFile(inputStream, file);

        } catch (IOException e) {
            log.error("Failed to save the file ", e);
        }

    }

    @Override
    public byte[] downloadFile(String path, String fileName) {
        Path filePath = Paths.get(path, fileName);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("Failed to read the file");
            return null;
        }

    }
}
