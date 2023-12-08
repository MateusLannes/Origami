package com.ifes.projetoorigame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ifes.projetoorigame.application.TipoEpicoApplication;
import com.ifes.projetoorigame.application.TipoHistoriaUsuarioApplication;
import com.ifes.projetoorigame.application.TipoTarefaApplication;
import com.ifes.projetoorigame.dto.TipoHistoriaUsuarioDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.TipoEpico;
import com.ifes.projetoorigame.model.TipoHistoriaUsuario;





@RestController
@RequestMapping("Tipo_Historia_Usuario")
public class TipoHistoriaUsuarioController{
    
    @Autowired
    private TipoTarefaApplication tipoTarefa;
    @Autowired
    private TipoEpicoApplication tEpicoApp;
    @Autowired
    private TipoHistoriaUsuarioApplication tipoHUApplication;
    
   @PostMapping("Criar/")
    public TipoHistoriaUsuario create(@RequestBody TipoHistoriaUsuarioDTO tipoHUdto)
    {
        return tipoHUApplication.create(tipoHUdto);
    }

    @GetMapping("Buscar/{id}")
    public TipoHistoriaUsuario get(@PathVariable int id)
    {
       
        try{
           return tipoHUApplication.getById(id);
        }
        catch (NotFoundException e){
            e.getMessage();
        }

        return null;
    }

    @GetMapping("Listar/")
    public List<TipoHistoriaUsuario> getAll(){
        return tipoHUApplication.getAll();
    }

    @PutMapping("Atualizar/{id}")
    public void update (@PathVariable int id, @RequestBody TipoHistoriaUsuarioDTO tipoHUdto){
        tipoHUApplication.update(id, tipoHUdto);
    }

    @DeleteMapping("Deletar/{id}")
    public void delete(@PathVariable int id)
    {
        tipoHUApplication.delete(id);
    }
}