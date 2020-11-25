package com.spring5.projects.springmongodbrecipeproject.services;

import com.spring5.projects.springmongodbrecipeproject.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAll();
}
