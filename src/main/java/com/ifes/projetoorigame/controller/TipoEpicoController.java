package com.ifes.projetoorigame.controller;

import com.ifes.projetoorigame.application.TipoEpicoApplication;
import com.ifes.projetoorigame.dto.TipoEpicoDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.TipoEpico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tipo_Epico")
public class TipoEpicoController
{
    @Autowired
    private TipoEpicoApplication tipoEpicoApplication;

    @PostMapping("criar")
    public TipoEpico create(@RequestBody TipoEpicoDTO tipoEpicoDTO)
    {
        return tipoEpicoApplication.create(tipoEpicoDTO);
    }

    @GetMapping("Buscar/{id}")
    public TipoEpico get(@PathVariable int id)
    {
        TipoEpico tipoEpico;

        try
        {
           return tipoEpicoApplication.retrieve(id);
        }
        catch (NotFoundException e)
        {
            e.getMessage();
        }

        return null;
    }

    @GetMapping("listar")
    public List<TipoEpico> getAll()
    {
        return tipoEpicoApplication.retrieveAll();
    }

    @PutMapping("atualizar/{id}")
    public void update (@PathVariable int id, @RequestBody TipoEpicoDTO tipoEpicoDTO)
    {
        tipoEpicoApplication.update(id, tipoEpicoDTO);
    }

    @DeleteMapping("deletar/{id}")
    public void delete(@PathVariable int id)
    {
        tipoEpicoApplication.delete(id);
    }
}