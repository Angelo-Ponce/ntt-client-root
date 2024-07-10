package com.nttdata.service.Impl;

import com.nttdata.exception.ModelNotFoundException;
import com.nttdata.model.ClientEntity;
import com.nttdata.repository.IClientRepository;
import com.nttdata.repository.IGenericRepository;
import com.nttdata.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends CRUDImpl<ClientEntity, Long> implements IClientService {

    private final IClientRepository repository;

    @Override
    protected IGenericRepository<ClientEntity, Long> getRepository() {
        return repository;
    }

    @Override
    public void deleteLogic(Long id) {
        ClientEntity client = repository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID not found: " + id));
        client.setStatus(Boolean.FALSE);
        repository.save(client);
    }
}
