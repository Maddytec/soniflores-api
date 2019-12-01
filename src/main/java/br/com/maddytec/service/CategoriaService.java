package br.com.maddytec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.Categoria;
import br.com.maddytec.exception.NotFoundException;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
import br.com.maddytec.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public Categoria findById(Long id) {
		Optional<Categoria> optional = categoriaRepository.findById(id);
		return optional.orElseThrow(() -> new NotFoundException("Não existe categoria com id: " + id));
	}

	public List<Categoria> findAll() {
		List<Categoria> categorias = categoriaRepository.findAll();
		return categorias;
	}

	public PageModel<Categoria> findAllOnLazyMode(PageRequestModel pageRequestModel) {
		PageRequest pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
		Page<Categoria> page = categoriaRepository.findAll(pageable);

		return new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
	}
	
	public void deleteById(Long id) {
		categoriaRepository.deleteById(id);
	}

}
