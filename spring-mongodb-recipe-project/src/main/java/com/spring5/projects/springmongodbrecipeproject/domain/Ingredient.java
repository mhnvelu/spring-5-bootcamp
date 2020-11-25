package com.spring5.projects.springmongodbrecipeproject.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"recipe"})
public class Ingredient {

    private String id;

    private String description;
    private BigDecimal amount;

    private UnitOfMeasure unitOfMeasure;

    private Recipe recipe;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

}
