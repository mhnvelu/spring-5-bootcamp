package com.spring5.projects.petclinic.controllers;

import com.spring5.projects.petclinic.model.Owner;
import com.spring5.projects.petclinic.model.Pet;
import com.spring5.projects.petclinic.model.PetType;
import com.spring5.projects.petclinic.services.OwnerService;
import com.spring5.projects.petclinic.services.PetService;
import com.spring5.projects.petclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    Owner owner;

    Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).build();
        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("Dog").build());
        petTypes.add(PetType.builder().id(2L).name("Cat").build());
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    public void getPetCreationForm() throws Exception {
        //given
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        //when
        mockMvc.perform(get("/owners/1/pets/new")).andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets" + "/createOrUpdatePetForm"));
    }

    @Test
    public void handlePetCreation() throws Exception {
        //given
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        //when
        mockMvc.perform(post("/owners/1/pets/new")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        //verify
        verify(petService, times(1)).save(any());
    }

    @Test
    public void getPetUpdateForm() throws Exception {
        //given
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(1L).build());

        //when
        mockMvc.perform(get("/owners/1/pets/1/edit")).andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets" + "/createOrUpdatePetForm"));
    }

    @Test
    public void handlePetUpdate() throws Exception {
        //given
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        //when
        mockMvc.perform(post("/owners/1/pets/1/edit")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        //verify
        verify(petService, times(1)).save(any());
    }
}