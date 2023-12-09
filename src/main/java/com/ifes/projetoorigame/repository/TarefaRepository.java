package com.ifes.projetoorigame.repository;

import com.ifes.projetoorigame.model.Tarefa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer>
{
    // Método para buscar todas as tarefas de uma História de Usuário específica usando uma query personalizada
    @Query("SELECT t FROM Tarefa t WHERE t.historia_usuario.id = :historiaUsuarioId")
    List<Tarefa> findByHistoriaUsuarioId(Integer historiaUsuarioId);

    @Query("SELECT t FROM Tarefa t WHERE t.tipoTarefa.id = :tipoHistoriaUserId")
    List<Tarefa> findByTipoHistoriaUsuarioId(Integer tipoHistoriaUserId);
}
