package br.com.maddytec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.Grupo;
import br.com.maddytec.repository.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	public List<Grupo> findAll() {
		List<Grupo> grupos = grupoRepository.findAll();
		return grupos;
	}
}
