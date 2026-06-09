package com.transportista.gestionguias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    private final S3Properties s3Properties;

    public S3Config(S3Properties s3Properties) {
        this.s3Properties = s3Properties;
    }

    @Bean
    public S3Client s3Client() {

        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String sessionToken = System.getenv("AWS_SESSION_TOKEN");

        AwsSessionCredentials credentials =
                AwsSessionCredentials.create(
                        accessKey,
                        secretKey,
                        sessionToken);

        return S3Client.builder()
                .region(Region.of(s3Properties.getRegion()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials))
                .build();
    }
}