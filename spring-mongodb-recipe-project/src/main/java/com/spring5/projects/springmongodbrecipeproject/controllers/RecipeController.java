package com.spring5.projects.springmongodbrecipeproject.controllers;

import com.spring5.projects.springmongodbrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.exceptions.NotFoundException;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.RecipeReactiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    RecipeReactiveService recipeReactiveService;
    private WebDataBinder webDataBinder;

    public RecipeController(RecipeReactiveService recipeReactiveService) {
        this.recipeReactiveService = recipeReactiveService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable(name = "id") String id, Model model) {
        Mono<Recipe> recipe = recipeReactiveService.findById(id);
        model.addAttribute("recipe", recipe);
        return "recipe/show";
    }

    @GetMapping("/create")
    public String getCreateRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping
    public String saveOrUpdateRecipe(@ModelAttribute("recipe") RecipeCommand recipeCommand) {
        // workaround for @Valid. it doesnt work for webflux
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
            });
            return "recipe/recipeform";
        }
        RecipeCommand savedRecipeCommand =
                recipeReactiveService.saveRecipeCommand(recipeCommand).block();
        return "redirect:/recipe/" + savedRecipeCommand.getId();
    }

    @GetMapping("/{id}/update")
    public String getUpdateRecipeForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeReactiveService.findCommandById(id));
        return "recipe/recipeform";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        recipeReactiveService.deleteById(id);
        return "redirect:/index";
    }

    //Exception Handler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public ModelAndView handleNotFound(Exception exception) {
//        log.error("Handling Not Found Exception...");
//        log.error(exception.getMessage());
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("404error");
//        modelAndView.addObject("exception", exception);
//        return modelAndView;
//    }


    //Exception handling for web-flux
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model) {
        log.error("Handling Not Found Exception...");
        log.error(exception.getMessage());
        model.addAttribute("exception", exception);
        return "404error";
    }


}
