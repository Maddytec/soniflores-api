package br.com.maddytec.resource;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.maddytec.converter.EnderecoConverter;
import br.com.maddytec.domain.Cliente;
import br.com.maddytec.domain.Endereco;
import br.com.maddytec.dto.ClienteDTO;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
import br.com.maddytec.service.ClienteService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "cliente")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EnderecoConverter enderecoConverter;

	@PostMapping
	@Transactional
	public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteDTO clienteDTO) {
		Cliente cliente = new Cliente();
		BeanUtils.copyProperties(clienteDTO, cliente, "enderecos");
		cliente = clienteService.save(cliente);

		if (!CollectionUtils.isEmpty(clienteDTO.getEnderecos())) {
			List<Endereco> enderecos = enderecoConverter.converterToEnderecosPorClienteDTO(clienteDTO, cliente);
			cliente.setEnderecos(enderecos);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Cliente> update(@PathVariable(name = "id") Long id,
			@RequestBody @Valid ClienteDTO clienteDTO) {

		Cliente cliente = clienteService.findById(id);
		BeanUtils.copyProperties(clienteDTO, cliente, "enderecos", "id");
		cliente = clienteService.save(cliente);

		if (!CollectionUtils.isEmpty(clienteDTO.getEnderecos())) {
			List<Endereco> enderecos = enderecoConverter.converterToEnderecosPorClienteDTO(clienteDTO, cliente);
			clienteService.removerAllEndereco(cliente);
			cliente.setEnderecos(enderecos);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(cliente);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable(name = "id") Long id) {
		Cliente cliente = clienteService.findById(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<Cliente> findByEmail(@PathVariable(name = "email") String email) {
		Cliente cliente = clienteService.findByEmail(email);
		return ResponseEntity.ok().body(cliente);
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> findAll() {
		List<Cliente> clientes = clienteService.findAll();
		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/lazy") // Lazy loading
	public ResponseEntity<PageModel<Cliente>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		PageRequestModel pageRequestModel = new PageRequestModel(page, size);

		PageModel<Cliente> pageModel = clienteService.findAllOnLazyMode(pageRequestModel);
		return ResponseEntity.ok(pageModel);
	}

	@Secured({ "ROLE_ADMINISTRADORES" })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id) {
		clienteService.deleteById(id);
		return ResponseEntity.ok().body(HttpStatus.OK);
	}

}
