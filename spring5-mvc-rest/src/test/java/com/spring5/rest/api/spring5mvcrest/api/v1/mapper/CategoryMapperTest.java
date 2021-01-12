package com.spring5.rest.api.spring5mvcrest.api.v1.mapper;

import com.spring5.rest.api.spring5mvcrest.api.v1.model.CategoryDTO;
import com.spring5.rest.api.spring5mvcrest.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void testCategoryToCategoryDTO() {
        Category fruits = new Category();
        fruits.setId(1L);
        fruits.setName("Fruits");

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(fruits);
        assertEquals(1L, categoryDTO.getId());
        assertEquals("Fruits", categoryDTO.getName());
    }

}