package com.exam.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EC2ContainerCredentialsProviderWrapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3Service(@Value("${cloud.aws.region.static}") String region) {
        // EC2ContainerCredentialsProviderWrapper를 사용하여 자격 증명 자동 관리
        AWSCredentialsProvider credentialsProvider = new EC2ContainerCredentialsProviderWrapper();

        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(credentialsProvider)
                .build();
    }

    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return s3Client.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
        }
    }
}
