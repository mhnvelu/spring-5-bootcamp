package com.spring5.projects.springrecipeproject.services;

import com.spring5.projects.springrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springrecipeproject.converters.IngredientCommandToIngredient;
import com.spring5.projects.springrecipeproject.converters.IngredientToIngredientCommand;
import com.spring5.projects.springrecipeproject.domain.Ingredient;
import com.spring5.projects.springrecipeproject.domain.Recipe;
import com.spring5.projects.springrecipeproject.repositories.RecipeRepository;
import com.spring5.projects.springrecipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class IngredientServiceImpl implements IngredientService {

    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    RecipeRepository recipeRepository;
    UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for Id : " + recipeId);
        }

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
        if (!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient not found for Id : " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredient(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional =
                recipeRepository.findById(ingredientCommand.getRecipeId());
        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for Id : " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository.findById(
                        ingredientCommand.getUnitOfMeasure().getId()).orElseThrow(
                        () -> new RuntimeException("Uom Not Found")));
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (!savedIngredientOptional.isPresent()) {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription()
                                .equals(ingredientCommand.getDescription()))
                        .filter(ingredient -> ingredient.getAmount()
                                .equals(ingredientCommand.getAmount()))
                        .filter(ingredient -> ingredient.getUnitOfMeasure().getId()
                                .equals(ingredientCommand.getUnitOfMeasure().getId())).findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(Long.valueOf(recipeId));
        if (!recipeOptional.isPresent()) {
            log.error("Recipe Not Found for Id : " + recipeId);
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional =
                    recipe.getIngredients().stream().filter(ingredient -> {
                        return ingredient.getId().equals(ingredientId);
                    }).findFirst();
            if (ingredientOptional.isPresent()) {
                recipe.removeIngredient(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        }
    }
}
