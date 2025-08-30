package com.stupa.stupa_backend.controller;

import com.stupa.stupa_backend.dto.response.BaseResponse;
import com.stupa.stupa_backend.service.ImageService;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @PostMapping(path = "/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BaseResponse<String>> uploadProfilePicture(
      @RequestPart("image") MultipartFile file) throws IOException {
    log.info("#uploadProfilePicture - uploading profile picture for user");
    return ResponseEntity.ok(BaseResponse.<String>builder()
        .data(imageService.uploadProfilePicture(file)).build());
  }

  @GetMapping("/{imageId}")
  public ResponseEntity<BaseResponse<byte[]>> downloadImage(@PathVariable String imageId) {
    byte[] imageData = imageService.downloadImage(UUID.fromString(imageId));
    if (imageData != null) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(BaseResponse.<byte[]>builder()
              .data(imageData).build());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.<byte[]>builder()
          .data("Image not found".getBytes()).build());
    }
  }

  @GetMapping("/profile-picture")
  public ResponseEntity<BaseResponse<byte[]>> getProfilePicture() {
    byte[] imageData = imageService.getProfilePicture();
    if (imageData != null) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(BaseResponse.<byte[]>builder()
              .data(imageData).build());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.<byte[]>builder()
          .data("Image not found".getBytes()).build());
    }
  }
}
