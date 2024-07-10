package com.nttdata.controller;

import com.nttdata.dto.BaseResponse;
import com.nttdata.dto.ClientDTO;
import com.nttdata.model.ClientEntity;
import com.nttdata.service.IClientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientControllerTest {

    @Mock
    private IClientService clientServiceMock;

    @Mock
    private ModelMapper mapperMock;

    @InjectMocks
    private ClientController clientController;

    @Test
    public void getClientById() throws Exception {
        Long id = 2L;

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setPersonId(id);
        clientDTO.setName("Marianela Montalvo");

        when(clientServiceMock.findById(id)).thenReturn(mapperMock.map(clientDTO, ClientEntity.class));

        ResponseEntity<BaseResponse> responseEntity = clientController.findById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientDTO, responseEntity.getBody().getData());
    }
}
