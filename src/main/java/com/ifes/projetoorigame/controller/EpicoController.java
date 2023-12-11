package com.ifes.projetoorigame.controller;
import com.ifes.projetoorigame.lib.ArvoreBinaria;
import com.ifes.projetoorigame.lib.Grafo;
import com.ifes.projetoorigame.lib.ComparadorEpicoPorTitulo;

import com.ifes.projetoorigame.application.EpicoApplication;
import com.ifes.projetoorigame.dto.EpicoDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Epico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("arvoreBinaria")
@RequestMapping("/api/epico")
public class EpicoController {


    @Autowired
    private EpicoApplication epicoApplication;

    @Autowired
    private ComparadorEpicoPorTitulo comp = new ComparadorEpicoPorTitulo();

    @ModelAttribute("arvoreBinaria")
    public ArvoreBinaria<Epico> setupArvoreBinaria() {
        return new ArvoreBinaria<>(comp); 
    }
    

    @PostMapping("/")
    public String create(@ModelAttribute("arvoreBinaria") ArvoreBinaria<Epico> arvoreBinaria, @ModelAttribute EpicoDTO dto){
        
        Epico epico = epicoApplication.create(dto);
        return "redirect:/epico/list";

    }

    @GetMapping("/{id}")
    public Epico retrieve(@PathVariable int id) {
        try
        {
            return epicoApplication.retrieve(id);
        } catch (NotFoundException e) {
            e.getMessage();
        }
        return null;
    }

    @GetMapping("/all/{idProjeto}")
    public List<Epico> retrieveAll(@PathVariable int idProjeto)
    {
        return epicoApplication.retrieveAll(idProjeto);
    }

    @PutMapping("/{id}")
    public Epico update(@PathVariable int id, @RequestBody EpicoDTO epicoDTO)
    {
        return epicoApplication.update(id, epicoDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id)
    {
        epicoApplication.delete(id);
    }
    @PostMapping("/{idEpico}")
    public Epico getDependentes(@PathVariable int idEpico,@RequestParam List<Integer> ids){
        return epicoApplication.gerarDependentes(idEpico, ids);
    }
}