package com.spring5.projects.springmongodbrecipeproject.services.reactive;

import com.spring5.projects.springmongodbrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RecipeReactiveServiceImpl implements RecipeReactiveService {
    @Override
    public Flux<Recipe> getRecipes() {
        return null;
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return null;
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return null;
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return null;
    }
}
