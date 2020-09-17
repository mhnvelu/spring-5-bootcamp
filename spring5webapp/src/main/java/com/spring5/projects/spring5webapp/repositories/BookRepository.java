package com.spring5.projects.spring5webapp.repositories;

import com.spring5.projects.spring5webapp.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Long> {
}
