package com.spring5.projects.springrecipeproject.services;

import com.spring5.projects.springrecipeproject.converters.RecipeCommandToRecipe;
import com.spring5.projects.springrecipeproject.converters.RecipeToRecipeCommand;
import com.spring5.projects.springrecipeproject.domain.Recipe;
import com.spring5.projects.springrecipeproject.exceptions.NotFoundException;
import com.spring5.projects.springrecipeproject.repositories.RecipeRepository;
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
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        Recipe recipeReturned = recipeService.findById(1L);
        assertNotNull(recipeReturned);

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeByIdNotFound() throws Exception {
        //given
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        assertThrows(NotFoundException.class, () -> {
            recipeService.findById(1L);
        });
    }

    @Test
    public void deleteRecipeById() {
        Long recipeId = 1L;
        recipeService.deleteById(recipeId);

        // no when, since method has void return type

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}