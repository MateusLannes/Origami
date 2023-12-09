package com.ifes.projetoorigame.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ifes.projetoorigame.dto.TarefaDTO;
import com.ifes.projetoorigame.exception.NotFoundException;
import com.ifes.projetoorigame.lib.ArvoreBinaria;
import com.ifes.projetoorigame.model.HistoriaUsuario;
import com.ifes.projetoorigame.model.Tarefa;
import com.ifes.projetoorigame.model.TipoTarefa;
import com.ifes.projetoorigame.repository.HistoriaUsuarioRepository;
import com.ifes.projetoorigame.repository.TarefaRepository;
import com.ifes.projetoorigame.repository.TipoTarefaRepository;

@Component
@Service
public class TarefaApplication {

    @Autowired
    private TarefaRepository repository;
    @Autowired 
    private HistoriaUsuarioRepository hu_repository;
    @Autowired 
    private TipoTarefaRepository repoTipoTarefa;
    @Autowired
    private ArvoreBinaria<Tarefa> arvore;

    

    public void gera (HistoriaUsuario hu){
        List<Tarefa> tarefas = new ArrayList<>();
        List<TipoTarefa> tipoTarefas = repoTipoTarefa.findAll();

        for(TipoTarefa tipoTarefa: tipoTarefas){
            if(tipoTarefa.getTipoHistoriaUsuario()!= null && tipoTarefa.getTipoHistoriaUsuario().equals(hu.getTipoHistoria())){
                Tarefa tarefa = new Tarefa();
                tarefa.setHistoria_usuario(hu);
                tarefa.setTipoTarefa(tipoTarefa);
                tarefa.setDescricao(tipoTarefa.getDescricao());
                tarefa.setTitulo(geraTitulo(hu.getTitulo(), tipoTarefa.getDescricao()));

                tarefas.add(tarefa);
                arvore.adicionar(tarefa);
                
            
            }
        }
        this.repository.saveAll(tarefas);
        for(Tarefa tarefa: tarefas){
            gerarDependentes(tarefa, tarefas);
        }
    }

    private String geraTitulo(String texto, String texto2){
        String[] word = (texto.split(" "));
        String titulo = texto2 + " " + "de " + word[word.length -1];
        return titulo;

    }

    public Tarefa getById(int id) throws NotFoundException{
        Optional<Tarefa> optional = repository.findById(id);

        if(optional.isPresent()){
            Tarefa tarefa = optional.get();
            return tarefa;
        }
        throw new NotFoundException("Tarefa não Encontrada");
    }
    
    public List<Tarefa> getAll(int idHistoria){
        return this.repository.findByHistoriaUsuarioId(idHistoria);
    }

    public Tarefa update(int id, TarefaDTO dto)
    {
        Tarefa tarefa;
        HistoriaUsuario hu;
         try {
                Optional<HistoriaUsuario> op= hu_repository.findById(id);
                Optional<TipoTarefa> op2 = repoTipoTarefa.findById(dto.getTipoTarefa());
                if(op.isPresent()&& op2.isPresent()){ 
                    hu = op.get();
                    tarefa = getById(id);
                    tarefa.setDescricao(dto.getDescricao());
                    tarefa.setHistoria_usuario(hu);
                    tarefa.setTipoTarefa(op2.get());
                return repository.save(tarefa);
                }
            } catch (NotFoundException e){
            e.getMessage();
            
        }
        return null;
    }

    public void delete(int id){
        try {
            arvore.remover(getById(id));
        } catch (NotFoundException e) {
            e.getMessage();
        }
        
       
    }

    public void gerarDependentes(Tarefa tarefa, List<Tarefa> tarefas){
        List<Tarefa> dependentes = new ArrayList<>(); 
        TipoTarefa tipoTarefa = tarefa.getTipoTarefa();
        List<TipoTarefa> lista = tipoTarefa.getListaDependentes();
        for(Tarefa taref: tarefas){
            if(lista.contains(taref.getTipoTarefa())){
                dependentes.add(taref);
            }
        }
        tarefa.setDependentes(dependentes);
        repository.save(tarefa);

    }

        
}
