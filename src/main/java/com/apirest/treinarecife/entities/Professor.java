package com.apirest.treinarecife.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "professor")
public class Professor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "matricula", unique=true)
	@Min(value = 0, message = "A matricula deverá ser maior que zero")
	private int matricula;
	@Column(name = "nome")
	@NotNull(message = "O nome não pode ser nulo")
	@NotBlank(message = "O nome não pode estar em branco")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "O nome só deve conter letras")
	private String nome;
	@Column(name = "salario")
	@Min(value = 1, message = "O salário deverá ser maior que zero")
	@Max(value = 10000, message = "O salário deverá ser menor ou igual a R$ 10.000,00")
	private float salario;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "professor", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("professor")
    private List<Curso> cursos = new ArrayList<>();

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
