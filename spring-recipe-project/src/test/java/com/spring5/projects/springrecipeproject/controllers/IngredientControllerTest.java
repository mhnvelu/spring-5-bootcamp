package com.spring5.projects.springrecipeproject.controllers;

import com.spring5.projects.springrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springrecipeproject.services.IngredientService;
import com.spring5.projects.springrecipeproject.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    IngredientController ingredientController;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void listIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients")).andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/list"))
               .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(any());
    }

    @Test
    public void showIngredients() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong()))
                .thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredient/2/show")).andExpect(status().isOk())
               .andExpect(view().name("recipe/ingredient/show"))
               .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }
}
