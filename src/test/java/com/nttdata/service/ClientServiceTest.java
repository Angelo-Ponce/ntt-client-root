package com.nttdata.service;

import com.nttdata.dto.ClientDTO;
import com.nttdata.model.ClientEntity;
import com.nttdata.repository.IClientRepository;
import com.nttdata.service.Impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ClientServiceTest {

    @Mock
    private IClientRepository repository;

    @Mock
    private ModelMapper mapperMock;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testFindById() {
        Long id = 2L;
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setPersonId(2L);
        clientEntity.setName("Marianela Montalvo");

        ClientDTO clientVo = new ClientDTO();
        clientVo.setPersonId(2L);
        clientVo.setName("Marianela Montalvo");

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(clientEntity));
        Mockito.when(mapperMock.map(clientEntity, ClientDTO.class)).thenReturn(clientVo);

        ClientDTO actual = mapperMock.map(clientService.findById(id), ClientDTO.class);

        assertEquals(clientVo, actual);
    }
}
