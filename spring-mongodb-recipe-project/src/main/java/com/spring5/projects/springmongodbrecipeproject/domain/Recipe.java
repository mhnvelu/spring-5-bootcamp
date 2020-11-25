package com.spring5.projects.springmongodbrecipeproject.domain;

import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class Recipe {


    private String id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    private String directions;

    private Byte[] image;

    private Difficulty difficulty;

    private Notes notes;

    private Set<Ingredient> ingredients = new HashSet<>();

    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public Recipe removeIngredient(Ingredient ingredient) {
        ingredient.setRecipe(null);
//        this.ingredients.remove(ingredient);
        return this;
    }
}
