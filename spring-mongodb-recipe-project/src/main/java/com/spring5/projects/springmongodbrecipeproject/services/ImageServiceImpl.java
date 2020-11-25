package com.spring5.projects.springmongodbrecipeproject.services;

import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(String recipeId, MultipartFile file) {

        try {
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
            Recipe recipe = recipeOptional.get();
            Byte[] bytes = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                bytes[i++] = b;
            }
            recipe.setImage(bytes);
            recipeRepository.save(recipe);
            log.info("Image file saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
