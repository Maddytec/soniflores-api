package br.com.maddytec.domain.enums;
import lombok.Getter;

@Getter
public enum Role {
	
	ADMINISTRADOR("Administrador"),
	CLIENTE("Cliente"),
	USUARIO("Usuario"),
	VISITANTE("Visitante");
	
	private String descricao;
		
	Role(String descricao) {
		this.descricao = descricao;
	}
}
