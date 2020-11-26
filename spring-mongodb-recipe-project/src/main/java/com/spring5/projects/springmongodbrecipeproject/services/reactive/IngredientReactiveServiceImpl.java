package com.spring5.projects.springmongodbrecipeproject.services.reactive;

import com.spring5.projects.springmongodbrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springmongodbrecipeproject.converters.IngredientCommandToIngredient;
import com.spring5.projects.springmongodbrecipeproject.converters.IngredientToIngredientCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Ingredient;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.repositories.reactive.RecipeReactiveRepository;
import com.spring5.projects.springmongodbrecipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class IngredientReactiveServiceImpl implements IngredientReactiveService {
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    RecipeReactiveRepository recipeReactiveRepository;
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public IngredientReactiveServiceImpl(
            IngredientToIngredientCommand ingredientToIngredientCommand,
            IngredientCommandToIngredient ingredientCommandToIngredient,
            RecipeReactiveRepository recipeReactiveRepository,
            UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId,
                                                                 String ingredientId) {

        //using Reactive Streams
        return recipeReactiveRepository.findById(recipeId).flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equals(ingredientId)).single()
                .map(ingredient -> {
                    IngredientCommand ingredientCommand =
                            ingredientToIngredientCommand.convert(ingredient);
                    ingredientCommand.setRecipeId(recipeId);
                    return ingredientCommand;
                });
    }

    @Override
    @Transactional
    public Mono<IngredientCommand> saveIngredient(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional =
                recipeReactiveRepository.findById(ingredientCommand.getRecipeId()).blockOptional();
        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for Id : " + ingredientCommand.getRecipeId());
            return Mono.just(new IngredientCommand());
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureReactiveRepository.findById(
                        ingredientCommand.getUnitOfMeasure().getId()).block());
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();
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

            IngredientCommand ingredientCommandSaved =
                    ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());
            return Mono.just(ingredientCommandSaved);
        }
    }

    @Override
    public Mono<Void> deleteByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        Optional<Recipe> recipeOptional =
                recipeReactiveRepository.findById(recipeId).blockOptional();
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
                recipeReactiveRepository.save(recipe).block();
            }
        }
        return Mono.empty();
    }

}
