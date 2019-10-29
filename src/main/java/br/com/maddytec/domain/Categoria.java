package br.com.maddytec.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "categoria")
public class Categoria implements Serializable{

	private static final long serialVersionUID = -5044963551985742089L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	@NotBlank
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String descricao;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name = "categoria_pai_id")
	private Categoria categoriaPai;
	
	@JsonBackReference
	@OneToMany(mappedBy = "categoriaPai", cascade = CascadeType.ALL)
	private List<Categoria> subcategorias;
}
