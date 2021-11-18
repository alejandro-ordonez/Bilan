package org.bilan.co.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Map;

@Component
public class S3FileStore {

    private final AmazonS3 amazonS3;

    public S3FileStore(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public final void upload(String path, String fileName,
                             Map<String, String> metadata, InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        metadata.forEach(objectMetadata::addUserMetadata);
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }
}
