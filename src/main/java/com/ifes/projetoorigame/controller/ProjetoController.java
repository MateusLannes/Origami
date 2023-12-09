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

import com.ifes.projetoorigame.application.ProjetoApplication;
import com.ifes.projetoorigame.dto.ProjetoDTO;

import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Projeto;



@RestController
@RequestMapping("/api/projeto")
public class ProjetoController{
    @Autowired
    private ProjetoApplication projetoApplication;

   @PostMapping("/")
    public Projeto create(@RequestBody ProjetoDTO projetoDto)
    {
        return projetoApplication.create(projetoDto);
    }

    @GetMapping("/{id}")
    public Projeto getById(@PathVariable int id)
    {
       
        try{
           return projetoApplication.getById(id);
        }
        catch (NotFoundException e){
            e.getMessage();
        }

        return null;
    }

    @GetMapping()
    public List<Projeto> getAll(){
        return projetoApplication.getAll();
    }

    @PutMapping("/{id}")
    public void update (@PathVariable int id, @RequestBody ProjetoDTO projetoCTO){
        projetoApplication.update(id, projetoCTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id)
    {
        projetoApplication.delete(id);
    }
}