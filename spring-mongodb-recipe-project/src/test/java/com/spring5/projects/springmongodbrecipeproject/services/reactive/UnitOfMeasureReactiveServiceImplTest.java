package com.spring5.projects.springmongodbrecipeproject.services.reactive;

import com.spring5.projects.springmongodbrecipeproject.commands.UnitOfMeasureCommand;
import com.spring5.projects.springmongodbrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.UnitOfMeasure;
import com.spring5.projects.springmongodbrecipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureReactiveServiceImplTest {
    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    private UnitOfMeasureReactiveService unitOfMeasureReactiveService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureReactiveService =
                new UnitOfMeasureReactiveServiceImpl(unitOfMeasureReactiveRepository,
                                                     unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAll() {

        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure unitOfMeasure_1 = new UnitOfMeasure();
        unitOfMeasure_1.setId("1");
        unitOfMeasure_1.setUom("Teaspoon");

        UnitOfMeasure unitOfMeasure_2 = new UnitOfMeasure();
        unitOfMeasure_1.setId("2");
        unitOfMeasure_1.setUom("Cup");

        unitOfMeasures.add(unitOfMeasure_1);
        unitOfMeasures.add(unitOfMeasure_2);

        //when
        when(unitOfMeasureReactiveRepository.findAll())
                .thenReturn(Flux.just(unitOfMeasure_1, unitOfMeasure_2));

        //then
        List<UnitOfMeasureCommand> unitOfMeasureCommands =
                unitOfMeasureReactiveService.listAll().collectList().block();
        assertEquals(2, unitOfMeasureCommands.size());

        //verify
        verify(unitOfMeasureReactiveRepository, times(1)).findAll();
    }
}
