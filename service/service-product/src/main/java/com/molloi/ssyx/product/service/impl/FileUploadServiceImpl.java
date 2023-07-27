package com.molloi.ssyx.product.service.impl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.molloi.ssyx.product.service.FileUploadService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


/**
 * @author Molloi
 * @date 2023/6/14 11:07
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Override
    public String fileUpload(MultipartFile file) {

        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;
            // 按照当前日期，创建文件夹，上传到文件夹里面
            String timeUrl = new DateTime().toString("yyyy/MM/dd");
            fileName = timeUrl + "/" + fileName;
            PutObjectArgs objectArgs = PutObjectArgs.builder().object(fileName)
                    .bucket(bucketName)
                    .contentType(file.getContentType())
                    .stream(inputStream,file.getSize(),-1).build();
            minioClient.putObject(objectArgs);
            return endpoint+"/"+bucketName+"/"+fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
