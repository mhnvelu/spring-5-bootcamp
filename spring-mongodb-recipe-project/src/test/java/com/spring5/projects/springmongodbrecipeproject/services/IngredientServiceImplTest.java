package com.spring5.projects.springmongodbrecipeproject.services;

import com.spring5.projects.springmongodbrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springmongodbrecipeproject.commands.UnitOfMeasureCommand;
import com.spring5.projects.springmongodbrecipeproject.converters.IngredientCommandToIngredient;
import com.spring5.projects.springmongodbrecipeproject.converters.IngredientToIngredientCommand;
import com.spring5.projects.springmongodbrecipeproject.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.spring5.projects.springmongodbrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Ingredient;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.domain.UnitOfMeasure;
import com.spring5.projects.springmongodbrecipeproject.repositories.RecipeRepository;
import com.spring5.projects.springmongodbrecipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientService ingredientService;

    IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientToIngredientCommand =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient =
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand,
                                                      ingredientCommandToIngredient,
                                                      recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient_1 = new Ingredient();
        ingredient_1.setId("3");

        Ingredient ingredient_2 = new Ingredient();
        ingredient_2.setId("2");

        recipe = recipe.addIngredient(ingredient_1).addIngredient(ingredient_2);
        when(recipeRepository.findById(anyString())).thenReturn(java.util.Optional.of(recipe));

        //when
        IngredientCommand ingredientCommand =
                ingredientService.findByRecipeIdAndIngredientId("1", "3");

        //assert
        assertEquals(3L, ingredientCommand.getId());
        assertEquals(1L, ingredientCommand.getRecipeId());

        //verify
        verify(recipeRepository, times(1)).findById(anyString());
    }

    @Test
    public void updateExistingIngredient() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("1");
        ingredientCommand.setRecipeId("1");

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1");
        savedRecipe.addIngredient(ingredient);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedIngredientCommand =
                ingredientService.saveIngredient(ingredientCommand);

        //verify
        assertEquals(1L, savedIngredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void saveNewIngredient() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setAmount(new BigDecimal(1.5));
        ingredientCommand.setDescription("ingredient-1");

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId("1");
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);
        ingredientCommand.setRecipeId("1");

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1");
        ingredient.setAmount(new BigDecimal(1.5));
        ingredient.setDescription("ingredient-1");
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId("1");
        ingredient.setUnitOfMeasure(unitOfMeasure);
        savedRecipe.addIngredient(ingredient);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedIngredientCommand =
                ingredientService.saveIngredient(ingredientCommand);

        //verify
        assertEquals(1L, savedIngredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void deleteIngredient() {
        //given
        Recipe savedRecipe = new Recipe();
        savedRecipe.setId("1");
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1");
        savedRecipe.addIngredient(ingredient);

        Optional<Recipe> savedRecipeOptional = Optional.of(savedRecipe);
        when(recipeRepository.findById(anyString())).thenReturn(savedRecipeOptional);

        //when
        ingredientService.deleteByRecipeIdAndIngredientId("1", "1");

        //verify
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}