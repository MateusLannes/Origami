package com.ifes.projetoorigame.repository;

import com.ifes.projetoorigame.model.Epico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EpicoRepository extends JpaRepository<Epico, Integer>
{
    // Método para buscar todos os épicos de um projeto específico usando uma query personalizada
    @Query("SELECT e FROM Epico e WHERE e.projeto.id = :projetoId")
    List<Epico> findAllByProjeto(int projetoId);
}
