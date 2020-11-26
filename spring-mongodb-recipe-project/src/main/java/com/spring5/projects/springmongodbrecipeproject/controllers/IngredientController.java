package com.spring5.projects.springmongodbrecipeproject.controllers;

import com.spring5.projects.springmongodbrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springmongodbrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.commands.UnitOfMeasureCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.services.RecipeService;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.IngredientReactiveService;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.UnitOfMeasureReactiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientController {

    private RecipeService recipeService;
    private IngredientReactiveService ingredientReactiveService;
    private UnitOfMeasureReactiveService unitOfMeasureReactiveService;

    public IngredientController(RecipeService recipeService,
                                IngredientReactiveService ingredientReactiveService,
                                UnitOfMeasureReactiveService unitOfMeasureReactiveService) {
        this.recipeService = recipeService;
        this.ingredientReactiveService = ingredientReactiveService;
        this.unitOfMeasureReactiveService = unitOfMeasureReactiveService;
    }

    @GetMapping("/{recipeId}/ingredients")
    public String listRecipeIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting Ingredients list for Recipe id : " + recipeId);
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        model.addAttribute("recipe", recipeCommand);
        return "recipe/ingredient/list";
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientReactiveService
                .findByRecipeIdAndIngredientId(recipeId, ingredientId).block());
        return "recipe/ingredient/show";
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/update")
    public String getUpdateRecipeIngredientForm(@PathVariable String recipeId,
                                                @PathVariable String ingredientId, Model model) {

        model.addAttribute("ingredient", ingredientReactiveService
                .findByRecipeIdAndIngredientId(recipeId, ingredientId));
        model.addAttribute("uomList", unitOfMeasureReactiveService.listAll().collectList().block());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/{recipeId}/ingredient/new")
    public String getCreateRecipeIngredientForm(@PathVariable String recipeId, Model model) {
        Recipe recipe = recipeService.findById(recipeId);
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipe.getId());
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureReactiveService.listAll().collectList().block());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/{recipeId}/ingredient")
    public String saveOrUpdateRecipeIngredient(
            @ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand =
                ingredientReactiveService.saveIngredient(ingredientCommand).block();
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" +
               savedIngredientCommand.getId() + "/show";
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId, Model model) {
        ingredientReactiveService.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
