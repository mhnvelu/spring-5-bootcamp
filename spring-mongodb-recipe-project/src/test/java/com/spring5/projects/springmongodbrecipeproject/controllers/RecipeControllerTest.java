package com.spring5.projects.springmongodbrecipeproject.controllers;

import com.spring5.projects.springmongodbrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.exceptions.NotFoundException;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.RecipeReactiveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeReactiveService recipeReactiveService;

    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeReactiveService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).
                setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void getRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId("1");


        when(recipeReactiveService.findById(anyString())).thenReturn(Mono.just(recipe));

        mockMvc.perform(get("/recipe/1")).andExpect(status().isOk())
                .andExpect(view().name("recipe/show")).andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void getRecipeByIdNotFoundStatusCode() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeReactiveService.findById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1")).andExpect(status().isNotFound());
    }

    @Test
    public void getRecipeByIdNotFoundStatusCodeAndView() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeReactiveService.findById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1")).andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

//    @Test
//    public void getRecipeByIdBadRequestStatusCodeAndView() throws Exception {
//        mockMvc.perform(get("/recipe/one")).andExpect(status().isBadRequest())
//                .andExpect(view().name("400error"));
//    }


    @Test
    public void getNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/create")).andExpect(status().isOk())
                .andExpect(view().name("recipe" + "/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void createNewRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeReactiveService.saveRecipeCommand(any())).thenReturn(Mono.just(recipeCommand));

        mockMvc.perform(
                post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
                        .param("description", "some desc").param("directions", "some directions")
                        .param("url", "http://recipe.com")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + recipeCommand.getId()));
    }

    @Test
    public void createNewRecipeWithValidationFail() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeReactiveService.saveRecipeCommand(any())).thenReturn(Mono.just(recipeCommand));

        mockMvc.perform(
                post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
                        .param("description", "some desc")).andExpect(status().isOk())
                .andExpect(view().name("recipe" + "/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void getUpdateView() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeReactiveService.findCommandById(anyString()))
                .thenReturn(Mono.just(recipeCommand));

        mockMvc.perform(get("/recipe/" + 1 + "/update")).andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void deleteRecipe() throws Exception {
        mockMvc.perform(get("/recipe/" + 1 + "/delete")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));

        verify(recipeReactiveService, times(1)).deleteById(anyString());
    }
}
