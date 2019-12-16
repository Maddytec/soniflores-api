package br.com.maddytec.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.maddytec.domain.Cliente;
import br.com.maddytec.domain.Endereco;
import br.com.maddytec.dto.ClienteDTO;
import br.com.maddytec.dto.EnderecoDTO;

@Service
public class EnderecoConverter {

	public List<Endereco> converterToEnderecosPorClienteDTO(ClienteDTO clienteDTO, Cliente cliente) {

		List<Endereco> enderecos = null;
		if (!CollectionUtils.isEmpty(cliente.getEnderecos())) {
			enderecos = cliente.getEnderecos();
		} else {
			enderecos = new ArrayList<>();
		}

		Endereco endereco = null;

		for (EnderecoDTO enderecoDTO : clienteDTO.getEnderecos()) {
			Boolean enderecoExiste = Boolean.FALSE;
			for (Endereco enderecoBanco : cliente.getEnderecos()) {
				if (enderecoBanco.getId() == enderecoDTO.getId()) {
					BeanUtils.copyProperties(enderecoDTO, enderecoBanco);
					enderecoBanco.setCliente(cliente);
					enderecoExiste = Boolean.TRUE;
					break;
				}

			}

			if (!enderecoExiste) {
				endereco = new Endereco();
				BeanUtils.copyProperties(enderecoDTO, endereco, "cliente");
				endereco.setCliente(cliente);
				enderecos.add(endereco);
			}
		}

		return enderecos;
	}
}
