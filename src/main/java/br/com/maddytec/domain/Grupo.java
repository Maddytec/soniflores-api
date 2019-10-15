package br.com.maddytec.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "grupo")
public class Grupo implements Serializable {

	private static final long serialVersionUID = 5430703430307588081L;

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@Size(max = 40)
	@Column(name = "nome", nullable = false, length = 40)
	private String nome;

	@NotBlank
	@Size(max = 80)
	@Column(name = "descricao", nullable = false, length = 80)
	private String descricao;

}