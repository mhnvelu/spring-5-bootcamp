package com.spring5.projects.springmongodbrecipeproject.services.reactive;

import com.spring5.projects.springmongodbrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.converters.RecipeCommandToRecipe;
import com.spring5.projects.springmongodbrecipeproject.converters.RecipeToRecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeReactiveServiceImpl implements RecipeReactiveService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeReactiveServiceImpl(RecipeReactiveRepository recipeReactiveRepository,
                                     RecipeCommandToRecipe recipeCommandToRecipe,
                                     RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeReactiveRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
//        return Mono.just(recipeToRecipeCommand.convert(findById(id).block()));
        return recipeReactiveRepository.findById(id).map(recipe -> {
            RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

            recipeCommand.getIngredients().forEach(rc -> {
                rc.setRecipeId(recipeCommand.getId());
            });

            return recipeCommand;
        });
    }


    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Mono<Recipe> savedRecipe = recipeReactiveRepository.save(detachedRecipe);
//        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return savedRecipe.map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        recipeReactiveRepository.deleteById(id).subscribe();
        return Mono.empty();
    }
}
