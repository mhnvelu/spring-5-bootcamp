package com.spring5.projects.springrecipeproject.controllers;

import com.spring5.projects.springrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springrecipeproject.domain.Recipe;
import com.spring5.projects.springrecipeproject.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void getRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);


        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1")).andExpect(status().isOk())
               .andExpect(view().name("recipe/show")).andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void getNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe")).andExpect(status().isOk())
               .andExpect(view().name("recipe" + "/recipeform"))
               .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void createNewRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(
                post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
                               .param("description", "some desc"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/recipe/" + recipeCommand.getId()));
    }

    @Test
    public void getUpdateView() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + 1 + "/update")).andExpect(status().isOk())
               .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void deleteRecipe() throws Exception {
        mockMvc.perform(get("/recipe/" + 1 + "/delete")).andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/index"));

        verify(recipeService,times(1)).deleteById(anyLong());
    }
}
