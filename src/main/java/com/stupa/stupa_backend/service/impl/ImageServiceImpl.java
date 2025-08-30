package com.stupa.stupa_backend.service.impl;

import com.stupa.stupa_backend.exception.BusinessException;
import com.stupa.stupa_backend.model.Image;
import com.stupa.stupa_backend.model.User;
import com.stupa.stupa_backend.repository.ImageRepository;
import com.stupa.stupa_backend.repository.UserRepository;
import com.stupa.stupa_backend.service.ImageService;
import com.stupa.stupa_backend.util.AuthUtil;
import com.stupa.stupa_backend.util.ImageUtil;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  private final ImageRepository imageRepository;

  private final UserRepository userRepository;

  @Transactional
  @Override
  public String uploadProfilePicture(MultipartFile file) throws IOException {
    UUID userId = AuthUtil.getCurrentUserId();
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BusinessException("USERNAME_NOT_FOUND",
            "#uploadProfilePicture - User not found with id: " + userId));

    Image image = Image.builder()
        .name(file.getOriginalFilename())
        .type(file.getContentType())
        .imageData(ImageUtil.compressImage(file.getBytes()))
        .build();

    user.setProfilePicture(image);

    userRepository.save(user);

    return "Profile picture for user " + userId + " uploaded successfully: "
        + file.getOriginalFilename();
  }

  @Override
  public byte[] downloadImage(UUID id) {
    Optional<Image> dbImage = imageRepository.findById(id);
    return dbImage.map(image -> ImageUtil.decompressImage(image.getImageData()))
        .orElse(null);
  }

  @Override
  public byte[] getProfilePicture() {
    UUID userId = AuthUtil.getCurrentUserId();
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BusinessException("USERNAME_NOT_FOUND",
            "#getProfilePicture - User not found with id: " + userId));

    Image profilePicture = user.getProfilePicture();
    if (profilePicture == null) {
      return null;
    }
    
    Optional<Image> dbImage = imageRepository.findById(user.getProfilePicture().getId());
    return dbImage.map(image -> ImageUtil.decompressImage(image.getImageData()))
        .orElse(null);
  }
}

