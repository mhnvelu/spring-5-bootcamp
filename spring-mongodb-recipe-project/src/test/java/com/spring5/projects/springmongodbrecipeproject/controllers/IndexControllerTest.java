package com.spring5.projects.springmongodbrecipeproject.controllers;

import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.RecipeReactiveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeReactiveService recipeReactiveService;

    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeReactiveService);
    }

    @Test
    void getIndexPage() {
        Recipe recipe_1 = new Recipe();
        recipe_1.setId("1");

        Recipe recipe_2 = new Recipe();
        recipe_2.setId("2");


        // Mockito Argument captor
        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        when(recipeReactiveService.getRecipes()).thenReturn(Flux.just(recipe_1, recipe_2));

        String page = indexController.getIndexPage(model);
        assertEquals("index", page);
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        verify(recipeReactiveService, times(1)).getRecipes();

        List<Recipe> recipeList = argumentCaptor.getValue();
        assertEquals(2, recipeList.size());
    }

    @Test
    void testMockMVC() throws Exception {
        // Spring MockMVC
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        when(recipeReactiveService.getRecipes()).thenReturn(Flux.empty());
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }
}