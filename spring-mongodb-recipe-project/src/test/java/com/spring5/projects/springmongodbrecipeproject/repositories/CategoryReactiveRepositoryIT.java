package com.spring5.projects.springmongodbrecipeproject.repositories;

import com.spring5.projects.springmongodbrecipeproject.domain.Category;
import com.spring5.projects.springmongodbrecipeproject.repositories.reactive.CategoryReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void saveCategory() {
        Category category = new Category();
        category.setDescription("Foo");
        categoryReactiveRepository.save(category).block();
        Long count = categoryReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void findByDescription() {
        Category category = new Category();
        category.setDescription("Foo");
        categoryReactiveRepository.save(category).block();
        Category fetchedFoo = categoryReactiveRepository.findByDescription("Foo").block();
        assertNotNull(fetchedFoo.getId());
    }
}
