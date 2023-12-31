package com.ifes.projetoorigame.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ifes.projetoorigame.dto.HistoriaUsuarioDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.model.Epico;
import com.ifes.projetoorigame.model.HistoriaUsuario;
import com.ifes.projetoorigame.model.Tarefa;
import com.ifes.projetoorigame.model.TipoHistoriaUsuario;
import com.ifes.projetoorigame.repository.EpicoRepository;
import com.ifes.projetoorigame.repository.HistoriaUsuarioRepository;
import com.ifes.projetoorigame.repository.TipoHistoriaUsuarioRepository;

@Component
@Service
public class HistoriaUsuarioApplication {

    @Autowired
    private HistoriaUsuarioRepository repository;
    @Autowired
    private EpicoRepository epicoRepo;
    @Autowired
    private TipoHistoriaUsuarioRepository repoTHU;
    @Autowired
    private TarefaApplication tarefaApp;


    public void gera(Epico epico) {

        List<TipoHistoriaUsuario> listaTHU =repoTHU.findAll();
        List<HistoriaUsuario> historias = new ArrayList<>();
        
        if(listaTHU!= null){
            for (TipoHistoriaUsuario tipoHU : listaTHU) {
                if(tipoHU.getTipoEpico() == epico.getTipoEpico() ){
                    HistoriaUsuario histUser = new HistoriaUsuario();                 
                    histUser.setCategoria(epico.getCategoria());
                    histUser.setRelevancia(epico.getRelevancia());
                    histUser.setTipoHistoria(tipoHU);
                    histUser.setEpico(epico);
                    histUser.setDescricao(epico.getDescricao());
                    String titulo = this.geraTitulo(epico.getTitulo(), tipoHU.getDescricao());
                    histUser.setTitulo(titulo);
                    repository.save(histUser); 
                    historias.add(histUser);                   
                    tarefaApp.gera(histUser);

                }
            }       

        }
        
        for(HistoriaUsuario hu: historias){
            gerarDependentes(hu, historias);
        }
    }

    public String geraTitulo(String texto, String tipoHU){
        String[] palavras = texto.split(" ");
        String texto2 = String.join(" ", Arrays.copyOf(palavras, Math.min(4, palavras.length))) + " " + tipoHU + " " + palavras[palavras.length-1];

        return texto2;

    }

    public HistoriaUsuario getById(int id)throws NotFoundException{

        Optional<HistoriaUsuario> opitional = repository.findById(id);

        if(opitional.isPresent()){
            return opitional.get();
        }
        throw new NotFoundException("Historia não encontrada.");
    }

    public List<HistoriaUsuario> getAll(int idEpico){
        return repository.findByEpicoId(idEpico);
    }

    public List<HistoriaUsuario> getAllTipo(int idTipoHU){
        return repository.findByTipoHistoriaUserId(idTipoHU);
    }

    public void update(HistoriaUsuarioDTO dto,int id) {
        HistoriaUsuario hUser;
       
        try {
            Optional<Epico>op =  epicoRepo.findById(id);
            Optional<TipoHistoriaUsuario>op2 =  repoTHU.findById(id); // isso é feito para conseguir deletar em cascata

            if(op.isPresent()&& op2!=null){
                Epico epico = op.get();
                hUser= getById(id);

                hUser.setTitulo(dto.getTitulo());
                hUser.setRelevancia(dto.getRelevancia());
                hUser.setCategoria(dto.getCategoria());
                hUser.setDescricao(dto.getDescricao());
                hUser.setTipoHistoria(op2.get());
                hUser.setEpico(epico);
                this.repository.save(hUser);
            }
        } catch (NotFoundException e) {
            e.getMessage();
          
        } 

    }
    public void delete(int id){
        List<Tarefa> tarefas = tarefaApp.getAll(id);
        for(Tarefa tarefa: tarefas){
            tarefaApp.delete(tarefa.getId());
        }
       try {
         HistoriaUsuario historinha = getById(id);
            List<HistoriaUsuario> lista = repository.findByEpicoId(historinha.getEpico().getId());
            for(HistoriaUsuario histo: lista){
                List<HistoriaUsuario> dependencias = histo.getDependentes();
                if(dependencias.contains(historinha)){
                    dependencias.remove(historinha);
                    histo.setDependentes(dependencias);

                }
            }
            historinha.setDependentes(null);
            repository.deleteById(id);
       } catch (NotFoundException e) {
        e.getMessage();
       }
        
    }

    public void gerarDependentes(HistoriaUsuario hu, List<HistoriaUsuario> historias){
        List<HistoriaUsuario> dependentes = new ArrayList<>(); 
        TipoHistoriaUsuario thu = hu.getTipoHistoria();
        List<TipoHistoriaUsuario> lista = thu.getListaDependentes();
        for(HistoriaUsuario histU: historias){
            if(lista.contains(histU.getTipoHistoria())){
                dependentes.add(histU);
            }
        }
        hu.setDependentes(dependentes);
        repository.save(hu);

    }
    
}
