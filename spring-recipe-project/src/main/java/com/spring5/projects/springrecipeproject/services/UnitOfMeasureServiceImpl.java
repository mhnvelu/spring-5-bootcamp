package com.spring5.projects.springrecipeproject.services;

import com.spring5.projects.springrecipeproject.commands.UnitOfMeasureCommand;
import com.spring5.projects.springrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring5.projects.springrecipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureRepository unitOfMeasureRepository;
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAll() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasure -> unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure))
                .collect(Collectors.toSet());
    }
}
