package com.spring5.projects.petclinic.controllers;

import com.spring5.projects.petclinic.model.Pet;
import com.spring5.projects.petclinic.model.Visit;
import com.spring5.projects.petclinic.services.PetService;
import com.spring5.projects.petclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    MockMvc mockMvc;
    Pet pet;
    Visit visit;
    @Mock
    private VisitService visitService;
    @Mock
    private PetService petService;
    @InjectMocks
    private VisitController visitController;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        pet.setId(1L);

        visit = Visit.builder().build();
        visit.setPet(pet);
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    public void getPetVisitForm() throws Exception {
        //given
        when(petService.findById(anyLong())).thenReturn(pet);

        //when
        mockMvc.perform(get("/owners/1/pets/1/visits/new")).andExpect(status().isOk())
                .andExpect(model().attributeExists("visit"))
                .andExpect(view().name("pets/createOrUpdateVisitForm"));

        //verify
        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    public void handlePetVisitForm() throws Exception {
        //given
        when(petService.findById(anyLong())).thenReturn(pet);

        //when
        mockMvc.perform(post("/owners/1/pets/1/visits/new")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("description", "visit desc")
                                .param("visitDate", "2020-11-03"))
                .andExpect(status().is3xxRedirection()).andExpect(model().attributeExists("visit"))
                .andExpect(view().name("redirect:/owners/1"));

        //verify
        verify(visitService, times(1)).save(any());
    }
}