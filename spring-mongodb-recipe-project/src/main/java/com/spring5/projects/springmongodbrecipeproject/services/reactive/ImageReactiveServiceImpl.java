package com.spring5.projects.springmongodbrecipeproject.services.reactive;

import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageReactiveServiceImpl implements ImageReactiveService {

    private RecipeReactiveRepository recipeReactiveRepository;

    public ImageReactiveServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId).map(recipe -> {
            Byte[] byteObjects = new Byte[0];
            try {
                byteObjects = new Byte[file.getBytes().length];

                int i = 0;

                for (byte b : file.getBytes()) {
                    byteObjects[i++] = b;
                }

                recipe.setImage(byteObjects);

                return recipe;

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        recipeReactiveRepository.save(recipeMono.block()).block();

        return Mono.empty();
    }
}
