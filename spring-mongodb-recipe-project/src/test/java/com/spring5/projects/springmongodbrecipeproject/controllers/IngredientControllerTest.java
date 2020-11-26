package com.spring5.projects.springmongodbrecipeproject.controllers;

import com.spring5.projects.springmongodbrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springmongodbrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.commands.UnitOfMeasureCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.services.RecipeService;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.IngredientReactiveService;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.UnitOfMeasureReactiveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientReactiveService ingredientReactiveService;
    IngredientController ingredientController;
    MockMvc mockMvc;
    @Mock
    private UnitOfMeasureReactiveService unitOfMeasureReactiveService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientReactiveService,
                                                        unitOfMeasureReactiveService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void listRecipeIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients")).andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(any());
    }

    @Test
    public void showRecipeIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientReactiveService.findByRecipeIdAndIngredientId(anyString(), anyString()))
                .thenReturn(Mono.just(ingredientCommand));

        mockMvc.perform(get("/recipe/1/ingredient/2/show")).andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientReactiveService, times(1))
                .findByRecipeIdAndIngredientId(anyString(), anyString());
    }

    @Test
    public void updateRecipeIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();

        //when
        when(ingredientReactiveService.findByRecipeIdAndIngredientId(anyString(), anyString()))
                .thenReturn(Mono.just(ingredientCommand));
        when(unitOfMeasureReactiveService.listAll())
                .thenReturn(Flux.just(new UnitOfMeasureCommand(), new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/update")).andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        //verify
        verify(ingredientReactiveService, times(1))
                .findByRecipeIdAndIngredientId(anyString(), anyString());
        verify(unitOfMeasureReactiveService, times(1)).listAll();
    }

    @Test
    public void saveOrUpdateRecipeIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("2");
        ingredientCommand.setRecipeId("1");

        //when
        when(ingredientReactiveService.saveIngredient(any())).thenReturn(Mono.just(ingredientCommand));

        //then
        mockMvc.perform(
                post("/recipe/1/ingredient").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "").param("description", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/2/show"));
    }

    @Test
    public void getCreateRecipeIngredientForm() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        //when
        when(recipeService.findById(anyString())).thenReturn(recipe);
        when(unitOfMeasureReactiveService.listAll())
                .thenReturn(Flux.just(new UnitOfMeasureCommand(), new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new")).andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

    }

    @Test
    public void deleteRecipeIngredient() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredient/1/delete")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredients"));

        //verify
        verify(ingredientReactiveService, times(1))
                .deleteByRecipeIdAndIngredientId(anyString(), anyString());
    }

}
