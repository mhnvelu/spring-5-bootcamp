package com.spring5.projects.springmongodbrecipeproject.services.reactive;

import com.spring5.projects.springmongodbrecipeproject.commands.UnitOfMeasureCommand;
import com.spring5.projects.springmongodbrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring5.projects.springmongodbrecipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureReactiveServiceImpl implements UnitOfMeasureReactiveService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureReactiveServiceImpl(
            UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
            UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAll() {
        return unitOfMeasureReactiveRepository.findAll()
                .map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
