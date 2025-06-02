package org.bilan.co.config;

import org.bilan.co.application.files.GlusterFileManager;
import org.bilan.co.application.files.IFileManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FileManagerBean {

    private final Environment env;

    public FileManagerBean(Environment env) {
        this.env = env;
    }

    @Bean
    public IFileManager fileManager(){
        String storage = env.getProperty("bilan.storage");

        Storage storageType = Storage.Gluster;
        if(storage!=null)
            storageType = Storage.valueOf(storage);

        return switch (storageType) {
            case AWS -> null;
            default -> buildGlusterFileManager();
        };

    }

    /*
        public AmazonS3 s3() {
            AWSCredentials awsCredentials = new BasicAWSCredentials(
                    Objects.requireNonNull(env.getProperty("S3_ACCESS_KEY")),
                    Objects.requireNonNull(env.getProperty("S3_SECRET_ACCESS_KEY")));
            return AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_EAST_2)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .build();
        }
    */
    private IFileManager buildGlusterFileManager() {
        return new GlusterFileManager(env);
    }

    /*
        private IFileManager buildAwsFileManager() {
            return new S3FileStore(s3());
        }
    */
    enum Storage{
        AWS, Gluster
    }
}
