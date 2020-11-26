package com.spring5.projects.springmongodbrecipeproject.repositories.reactive;

import com.spring5.projects.springmongodbrecipeproject.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UnitOfMeasureReactiveRepository
        extends ReactiveMongoRepository<UnitOfMeasure, String> {

    Mono<UnitOfMeasure> findByUom(String uom);
}
