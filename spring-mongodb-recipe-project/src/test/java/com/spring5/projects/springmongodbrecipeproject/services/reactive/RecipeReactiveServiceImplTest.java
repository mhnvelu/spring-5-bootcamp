package com.spring5.projects.springmongodbrecipeproject.services.reactive;

import com.spring5.projects.springmongodbrecipeproject.converters.RecipeCommandToRecipe;
import com.spring5.projects.springmongodbrecipeproject.converters.RecipeToRecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.repositories.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RecipeReactiveServiceImplTest {

    RecipeReactiveService recipeReactiveService;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeReactiveService =
                new RecipeReactiveServiceImpl(recipeReactiveRepository, recipeCommandToRecipe,
                                              recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {
        when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(new Recipe()));
        List<Recipe> recipes = recipeReactiveService.getRecipes().collectList().block();
        assertEquals(1, recipes.size());
        verify(recipeReactiveRepository, times(1)).findAll();
    }

    @Test
    public void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        Recipe recipeReturned = recipeReactiveService.findById("1").block();
        assertNotNull(recipeReturned);

        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, never()).findAll();
    }

//    @Test
//    public void getRecipeByIdNotFound() throws Exception {
//        //given
//        Mono<Recipe> recipe = Mono.empty();
//        when(recipeReactiveRepository.findById(anyString())).thenReturn(recipe);
//
//        //when
//        assertThrows(NotFoundException.class, () -> {
//            recipeReactiveService.findById("1").block();
//        });
//    }

    @Test
    public void deleteRecipeById() {
        String recipeId = "1";

        when(recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());

        //when
        recipeReactiveRepository.deleteById(recipeId);

        verify(recipeReactiveRepository, times(1)).deleteById(anyString());
    }
}
