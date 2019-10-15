package br.com.maddytec.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.maddytec.domain.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateDTO {

	@NotBlank(message = "{@NotBlank}")
	@Size(min = 3, max = 99, message = "{nome.size}")
	private String nome;

	@NotBlank(message = "{email.NotBlank}")
	@Email(message = "{Email}")
	private String email;

	@NotBlank(message = "Password must be fill")
	@Size(min = 6, max = 10, message = "Password must be between 6 and 10 character")
	private String senha;

	public Usuario converterToUsuario(UsuarioUpdateDTO usuarioUpdateDTO) {
		return Usuario.builder()
				.nome(usuarioUpdateDTO.getNome())
				.email(usuarioUpdateDTO.getEmail())
				.senha(usuarioUpdateDTO.getSenha())
				.build();
	}
}
