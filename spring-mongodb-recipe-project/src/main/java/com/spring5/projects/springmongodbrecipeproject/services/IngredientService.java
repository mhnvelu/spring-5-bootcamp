package com.spring5.projects.springmongodbrecipeproject.services;

import com.spring5.projects.springmongodbrecipeproject.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    IngredientCommand saveIngredient(IngredientCommand ingredientCommand);

    void deleteByRecipeIdAndIngredientId(String recipeId, String ingredientId);
}
