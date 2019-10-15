package br.com.maddytec.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioLoginDTO {

	@NotBlank(message = "{email.NotBlank}")
	@Email(message = "{Email}")
	private String email;
	
	@NotBlank(message = "{NotBlank}")
	@Size(min = 6, max = 10, message = "{size}")
	private String senha;
}
