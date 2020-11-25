package com.spring5.projects.springmongodbrecipeproject.controllers;

import com.spring5.projects.springmongodbrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springmongodbrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.commands.UnitOfMeasureCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.services.IngredientService;
import com.spring5.projects.springmongodbrecipeproject.services.RecipeService;
import com.spring5.projects.springmongodbrecipeproject.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
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
        model.addAttribute("ingredient",
                           ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
        return "recipe/ingredient/show";
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/update")
    public String getUpdateRecipeIngredientForm(@PathVariable String recipeId,
                                                @PathVariable String ingredientId, Model model) {

        model.addAttribute("ingredient",
                           ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
        model.addAttribute("uomList", unitOfMeasureService.listAll());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/{recipeId}/ingredient/new")
    public String getCreateRecipeIngredientForm(@PathVariable String recipeId, Model model) {
        Recipe recipe = recipeService.findById(recipeId);
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipe.getId());
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.listAll());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/{recipeId}/ingredient")
    public String saveOrUpdateRecipeIngredient(
            @ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand =
                ingredientService.saveIngredient(ingredientCommand);
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" +
               savedIngredientCommand.getId() + "/show";
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId, Model model) {
        ingredientService.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
