package com.spring5.rest.api.spring5mvcrest.api.v1.mapper;

import com.spring5.rest.api.spring5mvcrest.api.v1.model.CategoryDTO;
import com.spring5.rest.api.spring5mvcrest.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "id", target = "id")
    CategoryDTO categoryToCategoryDTO(Category category);
}
