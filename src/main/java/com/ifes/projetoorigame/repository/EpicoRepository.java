package com.ifes.projetoorigame.repository;

import com.ifes.projetoorigame.model.Epico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpicoRepository extends JpaRepository<Epico, Integer>
{
}
