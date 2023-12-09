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

import com.ifes.projetoorigame.application.TarefaApplication;
import com.ifes.projetoorigame.dto.TarefaDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Tarefa;

@RestController
@RequestMapping("/api/tarefa")

public class TarefaController {

    @Autowired
    private TarefaApplication application;
    
    @GetMapping("/{id}")
    public Tarefa getById(@PathVariable int id) throws NotFoundException{
       return application.getById(id);
    }

    @GetMapping()
    public List<Tarefa> getAll(){
        return application.getAll();
    }

    @PutMapping("/{id}")
    public Tarefa update(@PathVariable int id, @RequestBody TarefaDTO dto){
        return application.update(id, dto);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        application.delete(id);
    }
}
