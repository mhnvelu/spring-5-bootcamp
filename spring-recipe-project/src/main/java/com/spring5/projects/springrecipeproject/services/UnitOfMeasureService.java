package com.spring5.projects.springrecipeproject.services;

import com.spring5.projects.springrecipeproject.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAll();
}
