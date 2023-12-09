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

import com.ifes.projetoorigame.application.HistoriaUsuarioApplication;
import com.ifes.projetoorigame.dto.HistoriaUsuarioDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.HistoriaUsuario;

@RestController
@RequestMapping("/api/historiaUsuario")

public class HistoriaUsuarioController {

    @Autowired
    private HistoriaUsuarioApplication application;

    
    @GetMapping("/{id}")
    public HistoriaUsuario getById(@PathVariable int id){
        try {
            return application.getById(id);

        } catch (NotFoundException e) {
            e.getMessage();
        }
        return null;

    }

    @GetMapping("/all/{idEpico}")
    public List<HistoriaUsuario> getAll(@PathVariable int idEpico){
        return application.getAll(idEpico);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody HistoriaUsuarioDTO dto){
        application.update(dto, id);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        application.delete(id);
    }
}
