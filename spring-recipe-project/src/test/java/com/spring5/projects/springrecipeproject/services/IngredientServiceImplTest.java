package com.spring5.projects.springrecipeproject.services;

import com.spring5.projects.springrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springrecipeproject.commands.UnitOfMeasureCommand;
import com.spring5.projects.springrecipeproject.converters.IngredientCommandToIngredient;
import com.spring5.projects.springrecipeproject.converters.IngredientToIngredientCommand;
import com.spring5.projects.springrecipeproject.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.spring5.projects.springrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring5.projects.springrecipeproject.domain.Ingredient;
import com.spring5.projects.springrecipeproject.domain.Recipe;
import com.spring5.projects.springrecipeproject.domain.UnitOfMeasure;
import com.spring5.projects.springrecipeproject.repositories.RecipeRepository;
import com.spring5.projects.springrecipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
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
        recipe.setId(1L);

        Ingredient ingredient_1 = new Ingredient();
        ingredient_1.setId(3L);

        Ingredient ingredient_2 = new Ingredient();
        ingredient_2.setId(2L);

        recipe = recipe.addIngredient(ingredient_1).addIngredient(ingredient_2);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(java.util.Optional.of(recipe));

        //then
        IngredientCommand ingredientCommand =
                ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //assert
        assertEquals(3L, ingredientCommand.getId());
        assertEquals(1L, ingredientCommand.getRecipeId());

        //verify
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void updateExistingIngredient() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        ingredientCommand.setRecipeId(1L);

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        savedRecipe.addIngredient(ingredient);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //then
        IngredientCommand savedIngredientCommand =
                ingredientService.saveIngredient(ingredientCommand);

        //verify
        assertEquals(1L, savedIngredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void saveNewIngredient() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setAmount(new BigDecimal(1.5));
        ingredientCommand.setDescription("ingredient-1");

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(1L);
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);
        ingredientCommand.setRecipeId(1L);

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setAmount(new BigDecimal(1.5));
        ingredient.setDescription("ingredient-1");
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(1L);
        ingredient.setUnitOfMeasure(unitOfMeasure);
        savedRecipe.addIngredient(ingredient);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //then
        IngredientCommand savedIngredientCommand =
                ingredientService.saveIngredient(ingredientCommand);

        //verify
        assertEquals(1L, savedIngredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }
}