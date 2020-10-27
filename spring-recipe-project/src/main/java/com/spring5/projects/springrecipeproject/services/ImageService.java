package com.spring5.projects.springrecipeproject.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Long recipeId, MultipartFile file);
}
