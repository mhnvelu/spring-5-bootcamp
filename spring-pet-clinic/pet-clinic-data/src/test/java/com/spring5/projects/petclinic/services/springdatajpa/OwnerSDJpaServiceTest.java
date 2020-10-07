package com.spring5.projects.petclinic.services.springdatajpa;

import com.spring5.projects.petclinic.model.Owner;
import com.spring5.projects.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private final Long OWNER_ID = 1L;
    private final String OWNER_LAST_NAME = "test";

    //    OwnerService ownerService;
    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;
    @Mock
    private OwnerRepository ownerRepository;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        ownerService = new OwnerSDJpaService(ownerRepository);

    }

    @Test
    void findByLastName() {
        Owner ownerData = Owner.builder().id(OWNER_ID).lastName(OWNER_LAST_NAME).build();
        when(ownerRepository.findByLastName(eq(OWNER_LAST_NAME))).thenReturn(ownerData);

        Owner owner = ownerSDJpaService.findByLastName(OWNER_LAST_NAME);
        assertNotNull(owner);
        assertEquals(OWNER_ID, owner.getId());
        assertEquals(OWNER_LAST_NAME, owner.getLastName());

        verify(ownerRepository, times(1)).findByLastName(eq(OWNER_LAST_NAME));
    }

    @Test
    void findById() {
        Owner ownerData = Owner.builder().id(OWNER_ID).build();
        when(ownerRepository.findById(eq(OWNER_ID))).thenReturn(Optional.of(ownerData));

        Owner owner = ownerSDJpaService.findById(OWNER_ID);
        assertNotNull(owner);
        assertEquals(OWNER_ID, owner.getId());

        verify(ownerRepository, times(1)).findById(eq(OWNER_ID));
    }

    @Test
    void save() {
        Owner ownerData = Owner.builder().id(OWNER_ID).build();
        when(ownerRepository.save(any())).thenReturn(ownerData);

        Owner savedOwner = ownerSDJpaService.save(ownerData);
        assertNotNull(savedOwner);
        assertEquals(OWNER_ID, savedOwner.getId());

        verify(ownerRepository, times(1)).save(any());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(Owner.builder().id(1L).build());
        ownerSet.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(ownerSet);

        Set<Owner> owners = ownerSDJpaService.findAll();

        assertNotNull(owners);
        assertEquals(2, owners.size());

    }

    @Test
    void delete() {
        Owner ownerData = Owner.builder().id(OWNER_ID).build();
        ownerSDJpaService.delete(ownerData);
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(1L);
        verify(ownerRepository, times(1)).deleteById(anyLong());
    }
}