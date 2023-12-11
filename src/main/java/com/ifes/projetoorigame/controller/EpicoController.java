package com.ifes.projetoorigame.controller;
import com.ifes.projetoorigame.lib.ArvoreBinaria;
import com.ifes.projetoorigame.lib.Grafo;
import com.ifes.projetoorigame.lib.ComparadorEpicoPorTitulo;

import com.ifes.projetoorigame.application.EpicoApplication;
import com.ifes.projetoorigame.dto.EpicoDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Epico;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@SessionAttributes("arvoreBinaria")
@RequestMapping("/api/epico")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EpicoController {


    @Autowired
    private EpicoApplication epicoApplication;

    @Autowired
    private ComparadorEpicoPorTitulo comp = new ComparadorEpicoPorTitulo();

    @ModelAttribute("arvore")
   public ArvoreBinaria<Epico> setupArvoreBinaria() {
        // Obtém a árvore da sessão se ela já existir
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        ArvoreBinaria<Epico> arvore = (ArvoreBinaria<Epico>) session.getAttribute("arvore");

        // Se a árvore ainda não foi criada, cria uma nova
        if (arvore == null) {
            arvore = new ArvoreBinaria<>(comp);
            session.setAttribute("arvore", arvore);
        }

        return arvore;
    }

    @PostConstruct
    public void initializeTree() {
        List<Epico> epicos = epicoApplication.getAllEpicos();
        ArvoreBinaria<Epico> arvore = setupArvoreBinaria();

        for (Epico epico : epicos) {
            arvore.adicionar(epico);
        }
    }

    @PostMapping("/")
    public Epico create(@RequestBody EpicoDTO dto){
        
        
        Epico epico = epicoApplication.create(dto);
        ArvoreBinaria<Epico> arvoreBinaria = setupArvoreBinaria();
        arvoreBinaria.adicionar(epico);
        
        return epico;

    }


    @GetMapping("/{id}")
    public Epico retrieve(@PathVariable int id) {

        try {
            ArvoreBinaria<Epico> arvoreBinaria = setupArvoreBinaria();
            return arvoreBinaria.pesquisar(epicoApplication.retrieve(id));
        } catch (NotFoundException e) {
           e.getMessage();
        }
        return null;
    }

    @GetMapping("/all/{idProjeto}")
    public String retrieveAll(@PathVariable int idProjeto)
    {   
        String arvoreString= " ";
        ArvoreBinaria<Epico> arvoreBinaria = setupArvoreBinaria();
        arvoreBinaria.reiniciarNavegacao();
        for(int i = 0; i<arvoreBinaria.quantidadeNos(); i++){
            arvoreString = arvoreString + arvoreBinaria.obterProximo() + " \n";
        }
        return arvoreString;
        
    }

    @PutMapping("/{id}")
    public Epico update(@PathVariable int id, @RequestBody EpicoDTO epicoDTO)
    {
        return epicoApplication.update(id, epicoDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id)
    {
        try {
            
            ArvoreBinaria<Epico> arvoreBinaria = setupArvoreBinaria();
            arvoreBinaria.remover(epicoApplication.retrieve(id));
        } catch (NotFoundException e) {
            e.getMessage();
        }
        //epicoApplication.delete(id);
    }
    @PostMapping("/{idEpico}")
    public Epico getDependentes(@PathVariable int idEpico,@RequestParam List<Integer> ids){
        return epicoApplication.gerarDependentes(idEpico, ids);
    }
}