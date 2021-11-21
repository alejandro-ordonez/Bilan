package org.bilan.co.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
public class S3Config {

    private final Environment env;

    public S3Config(Environment env) {
        this.env = env;
    }

    @Value("s3.key")
    private String ACCESS_KEY;

    @Value("s3.secret")
    private String SECRET_ACCESS_KEY;


    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(Objects.requireNonNull(ACCESS_KEY),
                Objects.requireNonNull(SECRET_ACCESS_KEY));
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
