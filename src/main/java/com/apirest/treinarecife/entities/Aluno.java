package com.apirest.treinarecife.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@Entity
@Table(name = "ALUNO")
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {

//	 @Min
//	 @Max
//	 @NotBlank
//	 @NotNull
//	 @Size
//	 @Pattern
//	 Some of the most common validation annotations are:
//		@NotNull: to say that a field must not be null.
//		@NotEmpty: to say that a list field must not empty.
//		@NotBlank: to say that a string field must not be the empty string (i.e. it must have at least one character).
//		@Min and @Max: to say that a numerical field is only valid when it’s value is above or below a certain value.
//		@Pattern: to say that a string field is only valid when it matches a certain regular expression.
//		@Email: to say that a string field must be a valid email address.

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "MATRICULA", nullable = false)
	@Min(value = 1, message = "A matricula deverá ser maior que zero")
	private int matricula;

	@NotNull(message = "O nome deverá ser informado")
	@NotBlank(message = "O nome não poderá ser vazio")
	@Size(min = 5, max = 50, message = "O nome deverá ter entre 5 e 50 caracteres")
	@Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Informar apenas letras para o nome")
	@Column(name = "NOME", length = 50, nullable = false)
	private String nome;
	
	
	
}
