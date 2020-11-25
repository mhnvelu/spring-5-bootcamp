package com.spring5.projects.springmongodbrecipeproject.services;

import com.spring5.projects.springmongodbrecipeproject.converters.RecipeCommandToRecipe;
import com.spring5.projects.springmongodbrecipeproject.converters.RecipeToRecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.exceptions.NotFoundException;
import com.spring5.projects.springmongodbrecipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe,
                                              recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(new Recipe());
        when(recipeRepository.findAll()).thenReturn(recipeData);
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(optionalRecipe);

        Recipe recipeReturned = recipeService.findById("1");
        assertNotNull(recipeReturned);

        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeByIdNotFound() throws Exception {
        //given
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //when
        assertThrows(NotFoundException.class, () -> {
            recipeService.findById("1");
        });
    }

    @Test
    public void deleteRecipeById() {
        String recipeId = "1";
        recipeService.deleteById(recipeId);

        // no when, since method has void return type

        verify(recipeRepository, times(1)).deleteById(anyString());
    }
}