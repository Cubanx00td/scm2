package com.scm.scm.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile profileImage, String filename);

    String getUrlFromPublicId(String publicId);

}
