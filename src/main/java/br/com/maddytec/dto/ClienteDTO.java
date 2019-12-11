package br.com.maddytec.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.maddytec.domain.Cliente;
import br.com.maddytec.domain.Endereco;
import br.com.maddytec.domain.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO {

	private Long id;
	private String nome;
	private String foneMovel;
	private String foneFixo;
	private String email;
	private String documentoReceitaFederal;
	private TipoPessoa tipo;
	
	@Builder.Default
	private List<Endereco> enderecos = new ArrayList<>();
	
	public Cliente converterToCliente(ClienteDTO clienteDTO) {
		return Cliente.builder()
				.id(clienteDTO.getId())
				.nome(clienteDTO.getNome())
				.foneMovel(clienteDTO.getFoneMovel())
				.foneFixo(clienteDTO.getFoneFixo())
				.email(clienteDTO.getEmail())
				.documentoReceitaFederal(clienteDTO.getDocumentoReceitaFederal())
				.tipo(clienteDTO.getTipo())
				.enderecos(clienteDTO.getEnderecos())
				.build();
	}
}
