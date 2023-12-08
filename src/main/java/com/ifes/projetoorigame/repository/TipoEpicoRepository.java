package com.ifes.projetoorigame.repository;

import com.ifes.projetoorigame.model.TipoEpico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEpicoRepository extends JpaRepository<TipoEpico, Integer>
{
}
