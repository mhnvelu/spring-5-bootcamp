package com.spring5.projects.springmongodbrecipeproject.services.reactive;

import com.spring5.projects.springmongodbrecipeproject.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientReactiveService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand> saveIngredient(IngredientCommand ingredientCommand);

    Mono<Void> deleteByRecipeIdAndIngredientId(String recipeId, String ingredientId);
}
