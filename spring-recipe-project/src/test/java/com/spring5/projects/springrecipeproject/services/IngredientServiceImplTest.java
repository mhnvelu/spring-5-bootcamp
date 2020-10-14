package com.spring5.projects.springrecipeproject.services;

import com.spring5.projects.springrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springrecipeproject.converters.IngredientToIngredientCommand;
import com.spring5.projects.springrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring5.projects.springrecipeproject.domain.Ingredient;
import com.spring5.projects.springrecipeproject.domain.Recipe;
import com.spring5.projects.springrecipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientService ingredientService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientToIngredientCommand =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService =
                new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository);
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
}