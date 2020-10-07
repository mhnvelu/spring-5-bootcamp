package com.spring5.projects.petclinic.services.map;

import com.spring5.projects.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    private final Long OWNER_ID = 1L;
    private final String OWNER_LAST_NAME = "test";
    private OwnerServiceMap ownerServiceMap;

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetServiceMap(), new PetTypeServiceMap());
        ownerServiceMap.save(Owner.builder().id(OWNER_ID).lastName(OWNER_LAST_NAME).build());
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(1L);
        assertEquals(OWNER_ID, owner.getId());
    }

    @Test
    void saveOwnerWithId() {
        Owner savedOwner = ownerServiceMap.save(Owner.builder().id(2L).build());
        assertEquals(2L, savedOwner.getId());
    }

    @Test
    void saveOwnerNoId() {
        Owner savedOwner = ownerServiceMap.save(Owner.builder().build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerServiceMap.findAll();
        assertEquals(1, owners.size());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(OWNER_ID);
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(1L));
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner owner = ownerServiceMap.findByLastName(OWNER_LAST_NAME);
        assertNotNull(owner);
        assertEquals(OWNER_LAST_NAME, owner.getLastName());
    }

    @Test
    void findByLastNameNotFound() {
        Owner owner = ownerServiceMap.findByLastName("josh");
        assertNull(owner);
    }
}