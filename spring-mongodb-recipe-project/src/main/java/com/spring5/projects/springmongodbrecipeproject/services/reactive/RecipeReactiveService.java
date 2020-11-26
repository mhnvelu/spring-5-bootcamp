package com.spring5.projects.springmongodbrecipeproject.services.reactive;

import com.spring5.projects.springmongodbrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeReactiveService {

    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String id);

    Mono<RecipeCommand> findCommandById(String id);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    Mono<Void> deleteById(String id);
}
