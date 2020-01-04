package br.com.maddytec.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.Cliente;
import br.com.maddytec.domain.Endereco;
import br.com.maddytec.dto.ClienteDTO;

@Service
public class EnderecoConverter {

	public List<Endereco> converterToEnderecosPorClienteDTO(ClienteDTO clienteDTO, Cliente cliente) {
		return clienteDTO.getEnderecos().stream().map(enderecoDto -> {
			Optional<Endereco> result = cliente.getEnderecos().stream()
					.filter(endereco -> endereco.getId().equals(enderecoDto.getId())).findAny();

			if (result.isPresent()) {
				BeanUtils.copyProperties(enderecoDto, result.get());
				result.get().setCliente(cliente);
				return result.get();
			} else {
				Endereco endereco = new Endereco();
				BeanUtils.copyProperties(enderecoDto, endereco, "cliente");
				endereco.setCliente(cliente);
				return endereco;
			}
		}).collect(Collectors.toList());

	}
}
