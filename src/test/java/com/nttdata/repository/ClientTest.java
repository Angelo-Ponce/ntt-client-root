package com.nttdata.repository;

import com.nttdata.model.ClientEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ClientTest {

    @Autowired
    private IClientRepository repository;

    @Test
    public void testFindAll(){
        ClientEntity client = new ClientEntity();
        client.setIdentificacion("123456789");
        client.setName("Lola");
        client.setGender("Femenino");
        client.setAge(30);
        client.setAddress("Arizona");
        client.setPhone("0999695523");
        client.setClientId("lola2");
        client.setPassword("12345");
        client.setStatus(Boolean.TRUE);

        repository.save(client);

        // fin by name
        List<ClientEntity> data = repository.findAll();
        Assertions.assertEquals(data.size(), data.size());
    }
}
