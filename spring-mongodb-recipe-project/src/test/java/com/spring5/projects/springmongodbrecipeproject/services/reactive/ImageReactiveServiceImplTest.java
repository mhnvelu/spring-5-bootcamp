package com.spring5.projects.springmongodbrecipeproject.services.reactive;

import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.repositories.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ImageReactiveServiceImplTest {
    ImageReactiveService imageReactiveService;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        imageReactiveService = new ImageReactiveServiceImpl(recipeReactiveRepository);
    }

    @Test
    void saveImageFile() throws Exception {
        //given
        String id = "1";
        MultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                                      "Spring 5 bootcamp".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageReactiveService.saveImageFile(id, multipartFile);

        //verify
        verify(recipeReactiveRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);


    }
}
