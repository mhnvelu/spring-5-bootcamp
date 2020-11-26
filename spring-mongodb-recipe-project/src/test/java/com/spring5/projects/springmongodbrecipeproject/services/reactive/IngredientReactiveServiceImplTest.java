package com.spring5.projects.springmongodbrecipeproject.services.reactive;

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
import com.spring5.projects.springmongodbrecipeproject.repositories.reactive.RecipeReactiveRepository;
import com.spring5.projects.springmongodbrecipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import com.spring5.projects.springmongodbrecipeproject.services.IngredientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class IngredientReactiveServiceImplTest {
    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientReactiveService ingredientReactiveService;

    IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientToIngredientCommand =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient =
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientReactiveService = new IngredientReactiveServiceImpl(ingredientToIngredientCommand,
                                                      ingredientCommandToIngredient,
                                                                      recipeReactiveRepository, unitOfMeasureReactiveRepository);
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
        when(recipeReactiveRepository.findById(anyString())).thenReturn((Mono.just(recipe)));

        //when
        IngredientCommand ingredientCommand =
                ingredientReactiveService.findByRecipeIdAndIngredientId("1", "3").block();

        //assert
        assertEquals("3", ingredientCommand.getId());
        assertEquals("1", ingredientCommand.getRecipeId());

        //verify
        verify(recipeReactiveRepository, times(1)).findById(anyString());
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

//        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        IngredientCommand savedIngredientCommand =
                ingredientReactiveService.saveIngredient(ingredientCommand).block();

        //verify
        assertEquals("1", savedIngredientCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
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

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        IngredientCommand savedIngredientCommand =
                ingredientReactiveService.saveIngredient(ingredientCommand).block();

        //verify
        assertEquals("1", savedIngredientCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void deleteIngredient() {
        //given
        Recipe savedRecipe = new Recipe();
        savedRecipe.setId("1");
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1");
        savedRecipe.addIngredient(ingredient);

//        Optional<Recipe> savedRecipeOptional = Optional.of(savedRecipe);
        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(savedRecipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        ingredientReactiveService.deleteByRecipeIdAndIngredientId("1", "1");

        //verify
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
    }
}
