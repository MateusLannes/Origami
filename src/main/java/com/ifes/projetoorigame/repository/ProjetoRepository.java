package com.ifes.projetoorigame.repository;

import com.ifes.projetoorigame.model.Projeto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer>
{
}
