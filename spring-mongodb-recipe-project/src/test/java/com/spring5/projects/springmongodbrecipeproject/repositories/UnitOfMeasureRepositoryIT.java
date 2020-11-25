package com.spring5.projects.springmongodbrecipeproject.repositories;

import com.spring5.projects.springmongodbrecipeproject.bootstrap.RecipeBootstrap;
import com.spring5.projects.springmongodbrecipeproject.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();

        RecipeBootstrap recipeBootstrap =
                new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository);
        recipeBootstrap.onApplicationEvent(null);
    }

    @Test
    public void findByUom() throws Exception {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUom("Teaspoon");
        assertEquals("Teaspoon", unitOfMeasure.get().getUom());
    }

    @Test
    public void findByUomCup() throws Exception {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUom("Cup");
        assertEquals("Cup", unitOfMeasure.get().getUom());
    }
}