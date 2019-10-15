package br.com.maddytec.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.maddytec.domain.Grupo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRoleDTO {

	@NotNull(message = "{NotNull}")
	private List<Grupo> grupos;
}
