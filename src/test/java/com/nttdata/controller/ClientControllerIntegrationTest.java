package com.nttdata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.dto.ClientDTO;
import com.nttdata.model.ClientEntity;
import com.nttdata.service.IClientService;
import com.nttdata.util.MapperUtil;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerIntegrationTest {

    @MockBean
    private IClientService service;

    @MockBean
    private MapperUtil mapperUtil;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"admin_client_role", "user_client_role"})
    void testFindAll() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setPersonId(1L);
        clientDTO.setName("Angelo");

        List<ClientDTO> clientDTOs = Collections.singletonList(clientDTO);

        when(service.findAll()).thenReturn(Collections.singletonList(new ClientEntity()));
        when(mapperUtil.mapList(anyList(), eq(ClientDTO.class))).thenReturn(clientDTOs);

        mockMvc.perform(get("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].personId").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Angelo"));
    }

    @Test
    @WithMockUser(roles = {"admin_client_role", "user_client_role"})
    void testFindById() throws Exception {
        Long id = 1L;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setPersonId(id);
        clientDTO.setName("Angelo");

        when(service.findById(id)).thenReturn(new ClientEntity());
        when(mapperUtil.map(any(ClientEntity.class), eq(ClientDTO.class))).thenReturn(clientDTO);

        mockMvc.perform(get("/api/v1/clients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.personId").value(id))
                .andExpect(jsonPath("$.data.name").value("Angelo"));
    }

    @Test
    @WithMockUser(roles = {"admin_client_role"})
    void testSave() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setIdentificacion("0909090935");
        clientDTO.setName("Angelo");
        clientDTO.setGender("Masculino");
        clientDTO.setAge(10);
        clientDTO.setAddress("13 junio y Equinoccial");
        clientDTO.setPhone("09887458");
        clientDTO.setClientId("AngeloPonce");
        clientDTO.setPassword("12345");
        clientDTO.setStatus(Boolean.TRUE);
        clientDTO.setPersonId(1L);

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setPersonId(1L);

        when(service.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(mapperUtil.map(any(ClientDTO.class), eq(ClientEntity.class))).thenReturn(clientEntity);

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", CoreMatchers.containsString("/api/v1/clients/1")));
    }

    @Test
    @WithMockUser(roles = {"admin_client_role", "user_client_role"})
    void testUpdate() throws Exception {
        Long id = 1L;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setIdentificacion("0909090935");
        clientDTO.setName("Angelo");
        clientDTO.setGender("Masculino");
        clientDTO.setAge(10);
        clientDTO.setAddress("13 junio y Equinoccial");
        clientDTO.setPhone("09887458");
        clientDTO.setClientId("AngeloPonce");
        clientDTO.setPassword("12345");
        clientDTO.setStatus(Boolean.TRUE);
        clientDTO.setPersonId(1L);

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setPersonId(id);

        when(service.update(eq(id), any(ClientEntity.class))).thenReturn(clientEntity);
        when(mapperUtil.map(any(ClientDTO.class), eq(ClientEntity.class))).thenReturn(clientEntity);
        when(mapperUtil.map(any(ClientEntity.class), eq(ClientDTO.class))).thenReturn(clientDTO);

        mockMvc.perform(put("/api/v1/clients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.personId").value(id))
                .andExpect(jsonPath("$.data.name").value("Angelo"));
    }

    @Test
    @WithMockUser(roles = {"admin_client_role"})
    void testDelete() throws Exception {
        Long id = 1L;

        doNothing().when(service).deleteLogic(id);

        mockMvc.perform(delete("/api/v1/clients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
