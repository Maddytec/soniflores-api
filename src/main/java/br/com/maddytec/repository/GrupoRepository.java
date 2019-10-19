package br.com.maddytec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.maddytec.domain.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{

}
