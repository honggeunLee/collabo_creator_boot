package org.example.collabo_creator_boot.common;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public String upload(MultipartFile multipartFile, String folderName) {
        String fileName = generateFileName(multipartFile.getOriginalFilename(), folderName);

        try {
            // MultipartFile을 File로 변환
            File uploadFile = convertMultipartFileToFile(multipartFile);
            // S3에 파일 업로드
            String uploadUrl = uploadToS3(uploadFile, fileName);
            // 로컬 임시 파일 삭제
            deleteLocalFile(uploadFile);
            return uploadUrl;
        } catch (IOException e) {
            log.error("File upload failed", e);
            throw new RuntimeException("File upload failed", e);
        }
    }

    /**
     * 파일 조회
     */
    public String getFileUrl(String fileName, String folderName) {
        String filePath = folderName != null ? folderName + "/" + fileName : fileName;
        return amazonS3Client.getUrl(bucket, filePath).toString();
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String fileName, String folderName) {
        String filePath = folderName != null ? folderName + "/" + fileName : fileName;
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, filePath));
    }

    private String generateFileName(String originalName, String folderName) {
        String uniqueName = UUID.randomUUID().toString() + "_" + originalName;
        return folderName != null ? folderName + "/" + uniqueName : uniqueName;
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    private String uploadToS3(File file, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void deleteLocalFile(File file) {
        if (file.exists() && file.delete()) {
            log.info("Local file deleted: {}", file.getName());
        }
    }
}
