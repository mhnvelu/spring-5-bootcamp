package com.spring5.projects.spring5webapp.repositories;

import com.spring5.projects.spring5webapp.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author,Long> {
}
