package com.stupa.stupa_backend.service;

import java.io.IOException;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  String uploadProfilePicture(MultipartFile file) throws IOException;

  byte[] downloadImage(UUID id);

  byte[] getProfilePicture();
}
