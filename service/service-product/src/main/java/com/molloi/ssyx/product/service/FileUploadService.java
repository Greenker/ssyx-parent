package com.molloi.ssyx.product.service;

import org.springframework.web.multipart.MultipartFile;
/**
 * @author Molloi
 * @date 2023/6/14 11:07
 */
public interface FileUploadService {
    String fileUpload(MultipartFile file);
}
