package org.bilan.co.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.bilan.co.domain.utils.Tuple;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    public byte[] download(String path, String key) {
        try {
            S3Object object = amazonS3.getObject(path, key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }
}
