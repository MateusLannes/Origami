package com.ifes.projetoorigame.repository;

import com.ifes.projetoorigame.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer>
{
}
