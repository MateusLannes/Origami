package com.ifes.projetoorigame.controller;
import com.ifes.projetoorigame.lib.Grafo;

import com.ifes.projetoorigame.application.EpicoApplication;
import com.ifes.projetoorigame.dto.EpicoDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Epico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Epico")
public class EpicoController {


    @Autowired
    private EpicoApplication epicoApplication;
    

    @PostMapping("criar")
    public Epico create(@RequestBody EpicoDTO dto) {
        
        Epico epico = epicoApplication.create(dto);
        return epico;

    }

    @GetMapping("Buscar/{id}")
    public Epico retrieve(@PathVariable int id) {
        try
        {
            return epicoApplication.retrieve(id);
        } catch (NotFoundException e) {
            e.getMessage();
        }
        return null;
    }

    @GetMapping("Listar/")
    public List<Epico> retrieveAll()
    {
        return epicoApplication.retrieveAll();
    }

    @PutMapping("atualizar/{id}")
    public Epico update(@PathVariable int id, @RequestBody EpicoDTO epicoDTO)
    {
        return epicoApplication.update(id, epicoDTO);
    }

    @DeleteMapping("deletar/{id}")
    public void delete(@PathVariable int id)
    {
        epicoApplication.delete(id);
    }
}