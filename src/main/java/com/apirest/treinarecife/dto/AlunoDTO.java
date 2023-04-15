package com.apirest.treinarecife.dto;



import com.apirest.treinarecife.entities.Aluno;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
public class AlunoDTO extends BasicDTO{
	private Aluno aluno;
}
