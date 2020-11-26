package com.spring5.projects.springmongodbrecipeproject.services.reactive;

import com.spring5.projects.springmongodbrecipeproject.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureReactiveService {

    Flux<UnitOfMeasureCommand> listAll();
}
