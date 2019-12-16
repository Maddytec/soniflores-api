package br.com.maddytec.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.maddytec.domain.Grupo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

	@NotBlank(message = "{NotBlank}")
	@Size(min = 3, max = 99, message = "{nome.size}")
	private String nome;

	@NotBlank(message = "{email.NotBlank}")
	@Email(message = "{Email}")
	private String email;

	@NotBlank(message = "{NotBlank}")
	@Size(min = 6, max = 10, message = "{senha.size}")
	private String senha;

	@NotNull(message = "Required profile")
	private List<Grupo> grupos= new ArrayList<>();

}
