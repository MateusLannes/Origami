package com.ifes.projetoorigame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ifes.projetoorigame.application.TipoEpicoApplication;
import com.ifes.projetoorigame.application.TipoHistoriaUsuarioApplication;
import com.ifes.projetoorigame.application.TipoTarefaApplication;
import com.ifes.projetoorigame.dto.TipoHistoriaUsuarioDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Epico;
import com.ifes.projetoorigame.model.TipoEpico;
import com.ifes.projetoorigame.model.TipoHistoriaUsuario;

@RestController
@RequestMapping("/api/tipoHistoriaUsuario")
public class TipoHistoriaUsuarioController {

    @Autowired
    private TipoTarefaApplication tipoTarefa;
    @Autowired
    private TipoEpicoApplication tEpicoApp;
    @Autowired
    private TipoHistoriaUsuarioApplication tipoHUApplication;

    @PostMapping("/")
    public TipoHistoriaUsuario create(@RequestBody TipoHistoriaUsuarioDTO tipoHUdto) {
        return tipoHUApplication.create(tipoHUdto);
    }

    @GetMapping("/{id}")
    public TipoHistoriaUsuario get(@PathVariable int id) {

        try {
            return tipoHUApplication.getById(id);
        } catch (NotFoundException e) {
            e.getMessage();
        }

        return null;
    }

    @GetMapping("/all/{idTipoEpico}")
    public List<TipoHistoriaUsuario> getAll(@PathVariable int idTipoEpico) {
        return tipoHUApplication.getAll(idTipoEpico);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody TipoHistoriaUsuarioDTO tipoHUdto) {
        tipoHUApplication.update(id, tipoHUdto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        tipoHUApplication.delete(id);
    }

    @PostMapping("/{idTHU}")
    public TipoHistoriaUsuario getDependentes(
            @PathVariable int idTHU,
            @RequestParam List<Integer> ids) {
        return tipoHUApplication.gerarDependentes(idTHU, ids);
    }
}
