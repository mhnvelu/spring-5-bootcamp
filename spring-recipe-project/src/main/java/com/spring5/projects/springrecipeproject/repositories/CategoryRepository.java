package com.spring5.projects.springrecipeproject.repositories;

import com.spring5.projects.springrecipeproject.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {
}
