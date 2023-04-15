package com.apirest.treinarecife.dto;

import com.apirest.treinarecife.entities.Curso;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CursoDTO extends BasicDTO{
	private Curso curso = new Curso();
	private String statusCurso = curso.getStatusStr();
}
