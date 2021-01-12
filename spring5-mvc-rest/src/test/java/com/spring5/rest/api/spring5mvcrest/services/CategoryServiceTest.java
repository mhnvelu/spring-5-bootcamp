package com.spring5.rest.api.spring5mvcrest.services;

import com.spring5.rest.api.spring5mvcrest.api.v1.mapper.CategoryMapper;
import com.spring5.rest.api.spring5mvcrest.api.v1.model.CategoryDTO;
import com.spring5.rest.api.spring5mvcrest.domain.Category;
import com.spring5.rest.api.spring5mvcrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    public void getAllCategories() {
        //given
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();

        //then
        assertEquals(2, categoryDTOList.size());
    }

    @Test
    public void getCategoryByName() {
        //given
        Category fruits = new Category();
        fruits.setId(1L);
        fruits.setName("Fruits");
        when(categoryRepository.findByName(anyString())).thenReturn(fruits);

        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName("Fruits");

        //then
        assertEquals(1L, categoryDTO.getId());
        assertEquals("Fruits", categoryDTO.getName());

    }

}