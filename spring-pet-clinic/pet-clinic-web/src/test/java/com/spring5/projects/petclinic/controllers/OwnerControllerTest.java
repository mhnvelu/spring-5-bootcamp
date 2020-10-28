package com.spring5.projects.petclinic.controllers;

import com.spring5.projects.petclinic.model.Owner;
import com.spring5.projects.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    @InjectMocks
    OwnerController ownerController;

    MockMvc mockMvc;

    Set<Owner> owners;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());
    }

    @Test
    void listOwners() throws Exception {
        when(ownerService.findAll()).thenReturn(owners);

        mockMvc.perform(get("/owners/")).andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void listOwnersByIndex() throws Exception {
        when(ownerService.findAll()).thenReturn(owners);

        mockMvc.perform(get("/owners/index")).andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find")).andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void findOwner() throws Exception {
        //given
        Owner owner = Owner.builder().id(1L).build();
        when(ownerService.findById(anyLong())).thenReturn(owner);

        //when
        mockMvc.perform(get("/owners/1")).andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attributeExists("owner"));

        //verify
        verify(ownerService, times(1)).findById(anyLong());
    }

    @Test
    void handleFindOwnerReturnMany() throws Exception {
        //given
        when(ownerService.findByLastNameLike(anyString())).thenReturn(
                Arrays.asList(Owner.builder().id(1L).build(), Owner.builder().id(2L).build()));

        //when
        mockMvc.perform(get("/owners")).andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void handleFindOwnerReturnOne() throws Exception {
        //given
        when(ownerService.findByLastNameLike(anyString()))
                .thenReturn(Arrays.asList(Owner.builder().id(1L).build()));

        //when
        mockMvc.perform(get("/owners")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    void getCreateOwnerForm() throws Exception {
        //when
        mockMvc.perform(get("/owners/new")).andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));
        //verify
        verifyNoInteractions(ownerService);
    }

    @Test
    void processCreateOwnerForm() throws Exception {
        //given
        when(ownerService.save(any())).thenReturn(Owner.builder().id(1L).build());

        //when
        mockMvc.perform(post("/owners/new")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));
        //verify
        verify(ownerService, times(1)).save(any());
    }

    @Test
    void getUpdateOwnerForm() throws Exception {
        //given
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        //when
        mockMvc.perform(get("/owners/1/edit")).andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));
        //verify
        verify(ownerService, times(1)).findById(anyLong());
    }

    @Test
    void processUpdateOwnerForm() throws Exception {
        //given
        when(ownerService.save(any())).thenReturn(Owner.builder().id(1L).build());

        //when
        mockMvc.perform(post("/owners/1/edit")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));
        //verify
        verify(ownerService, times(1)).save(any());
    }

}