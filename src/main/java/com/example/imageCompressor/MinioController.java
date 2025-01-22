package com.example.imageCompressor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/minio")
public class MinioController {

    @Autowired
    private MinioService minioService;

    // Endpoint to upload a file to MinIO
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("bucketName") String bucketName) {
        try {
            // Extract necessary information from the MultipartFile
            String objectName = file.getOriginalFilename();  // Object name in MinIO (can be the same as the original filename)
            InputStream inputStream = file.getInputStream();  // InputStream of the file content
            String contentType = file.getContentType();      // Content type (e.g., "image/jpeg")

            // Call the service to upload the file
            minioService.uploadFile(bucketName, objectName, inputStream, contentType);

            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error reading file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Error uploading file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
