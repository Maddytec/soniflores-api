package br.com.maddytec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.maddytec.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
		
}
