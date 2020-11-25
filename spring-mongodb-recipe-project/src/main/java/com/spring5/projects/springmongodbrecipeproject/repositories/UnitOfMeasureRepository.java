package com.spring5.projects.springmongodbrecipeproject.repositories;

import com.spring5.projects.springmongodbrecipeproject.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

    Optional<UnitOfMeasure> findByUom(String uom);

}
