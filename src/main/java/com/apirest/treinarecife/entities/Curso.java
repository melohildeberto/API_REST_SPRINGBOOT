package com.apirest.treinarecife.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "curso")
public class Curso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	@Column(name = "nome")
	@NotNull(message = "O nome não pode ser nulo")
	@NotBlank(message = "O nome não pode estar em branco")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "O nome só deve conter letras")
	private String nome;
	@Column(name = "ementa")
	@NotNull(message = "A ementa não pode ser nulo")
	@NotBlank(message = "A ementa não pode estar em branco")
	private String ementa;
	@Column(name = "observacao")
	private String observacao;
	@Column(name = "status")
	@Min(value = 1, message = "O status ser maior que zero")
	@Max(value = 4, message = "O staus deverá ser menor que 4")
	private int status;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "ProfessorId", referencedColumnName = "id")
	@JsonIgnoreProperties("cursoList")
	private Professor professor;

	public String getStatusStr() {
		String retorno = "";
		switch (status) {
		case 1:
			retorno = "Ativo";
			break;
		case 2:
			retorno = "Inativo";
			break;
		case 3:
			retorno = "Em andamento";
			break;
		case 4:
			retorno = "Concluído";
			break;
		default:
			retorno = "Status inválidoo";
			break;
		}
		return retorno;
	}

	@Override
	public String toString() {
		Map<String, String> myMap = new HashMap<String, String>();
		myMap.put("CursoID", this.id + "");
		myMap.put("nome", this.nome);
		myMap.put("ementa", this.ementa);
		myMap.put("observacao", this.observacao);
		myMap.put("status", this.status + "");
		return new Gson().toJson(myMap);
	}
}
