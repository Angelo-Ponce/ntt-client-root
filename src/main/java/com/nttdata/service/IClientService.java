package com.nttdata.service;

import com.nttdata.exception.ModelNotFoundException;
import com.nttdata.model.ClientEntity;

public interface IClientService extends ICRUD<ClientEntity, Long>{

    void deleteLogic(Long id);
}
