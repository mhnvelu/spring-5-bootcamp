package com.spring5.projects.springmongodbrecipeproject.repositories;

import com.spring5.projects.springmongodbrecipeproject.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,String> {

    Optional<Category> findByDescription(String description);
}
