package com.ifes.projetoorigame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifes.projetoorigame.application.TipoTarefaApplication;
import com.ifes.projetoorigame.dto.TipoTarefaDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.TipoTarefa;

@RequestMapping("api/tipoTarefa")
@RestController
public class TipoTarefaController {
    @Autowired
    private TipoTarefaApplication application;

    @PostMapping("/")
    public TipoTarefa create(@RequestBody TipoTarefaDTO tipoTarefaDTO){
        return application.create(tipoTarefaDTO);
    }

    @GetMapping("/{id}")
    public TipoTarefa getById(@PathVariable int id) throws NotFoundException {
        return application.getById(id);
    }

    @GetMapping()
    public List<TipoTarefa> listar(){
        return application.getAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody TipoTarefaDTO tipoTarefaDTO){
        application.update(id, tipoTarefaDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        application.delete(id);
    }

    
}
