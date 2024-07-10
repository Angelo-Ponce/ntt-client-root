package com.nttdata.controller;

import com.nttdata.dto.BaseResponse;
import com.nttdata.dto.ClientDTO;
import com.nttdata.model.ClientEntity;
import com.nttdata.service.IClientService;
import com.nttdata.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService service;

    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<BaseResponse> findAll(){
        List<ClientDTO> list = mapperUtil.mapList(service.findAll(), ClientDTO.class);

        return ResponseEntity.ok(BaseResponse.builder().data(list).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findById( @PathVariable("id") Long id){
        ClientEntity obj = service.findById(id);

        return ResponseEntity.ok(BaseResponse.builder().data(mapperUtil.map(obj, ClientDTO.class)).build());
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ClientDTO dto){
        ClientEntity obj = service.save(mapperUtil.map(dto, ClientEntity.class));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getPersonId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update( @PathVariable("id") Long id, @RequestBody ClientDTO dto){
        dto.setPersonId(id);
        ClientEntity obj = service.update(id, mapperUtil.map(dto, ClientEntity.class));

        return ResponseEntity.ok(BaseResponse.builder().data(mapperUtil.map(obj, ClientDTO.class)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete( @PathVariable("id") Long id){
        // Eliminar registro
        //service.delete(id);
        // Eliminado logico
        service.deleteLogic(id);
        return ResponseEntity.noContent().build();
    }
}
