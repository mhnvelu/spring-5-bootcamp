package com.spring5.projects.springrecipeproject.services;

import com.spring5.projects.springrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springrecipeproject.converters.IngredientToIngredientCommand;
import com.spring5.projects.springrecipeproject.domain.Recipe;
import com.spring5.projects.springrecipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class IngredientServiceImpl implements IngredientService {

    IngredientToIngredientCommand ingredientToIngredientCommand;
    RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for Id : " + recipeId);
        }

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                                                                      .filter(ingredient -> ingredient
                                                                              .getId()
                                                                              .equals(ingredientId))
                                                                      .map(ingredient -> ingredientToIngredientCommand
                                                                              .convert(ingredient))
                                                                      .findFirst();
        if (!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient not found for Id : " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }
}
