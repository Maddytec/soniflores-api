package br.com.maddytec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.Cliente;
import br.com.maddytec.exception.NotFoundException;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
import br.com.maddytec.repository.ClienteRepository;
import br.com.maddytec.repository.EnderecoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Cliente findById(Long id) {
		Optional<Cliente> optional = clienteRepository.findById(id);
		return optional.orElseThrow(() -> new NotFoundException("Não existe cliente com id: " + id));
	}

	public Cliente findByEmail(String email) {
		Optional<Cliente> optional = clienteRepository.findByEmail(email);
		return optional.orElseThrow(() -> new NotFoundException("Não existe cliente com email: " + email));
	}

	public List<Cliente> findAll() {
		List<Cliente> clientes = clienteRepository.findAll();
		return clientes;
	}

	public PageModel<Cliente> findAllOnLazyMode(PageRequestModel pageRequestModel) {
		PageRequest pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
		Page<Cliente> page = clienteRepository.findAll(pageable);

		return new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
	}

	public void deleteById(Long id) {
		clienteRepository.deleteById(id);
	}

	public void removerAllEndereco(Cliente cliente) {
		if (cliente != null && cliente.getEnderecos() != null) {
			cliente.getEnderecos().forEach(endereco -> {
				enderecoRepository.delete(endereco);
			});
		}
	}

}