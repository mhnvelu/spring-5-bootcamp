package com.spring5.projects.spring5webapp.repositories;

import com.spring5.projects.spring5webapp.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher,Long> {
}
