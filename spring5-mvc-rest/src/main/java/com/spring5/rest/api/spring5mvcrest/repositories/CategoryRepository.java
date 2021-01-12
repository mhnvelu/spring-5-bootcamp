package com.spring5.rest.api.spring5mvcrest.repositories;

import com.spring5.rest.api.spring5mvcrest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
