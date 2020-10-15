package com.spring5.projects.springrecipeproject.services;

import com.spring5.projects.springrecipeproject.commands.UnitOfMeasureCommand;
import com.spring5.projects.springrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring5.projects.springrecipeproject.domain.UnitOfMeasure;
import com.spring5.projects.springrecipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    private UnitOfMeasureService unitOfMeasureService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,
                                                            unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAll() {

        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure unitOfMeasure_1 = new UnitOfMeasure();
        unitOfMeasure_1.setId(1L);
        unitOfMeasure_1.setUom("Teaspoon");

        UnitOfMeasure unitOfMeasure_2 = new UnitOfMeasure();
        unitOfMeasure_1.setId(2L);
        unitOfMeasure_1.setUom("Cup");

        unitOfMeasures.add(unitOfMeasure_1);
        unitOfMeasures.add(unitOfMeasure_2);

        //when
        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        //then
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAll();
        assertEquals(2, unitOfMeasureCommands.size());

        //verify
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}