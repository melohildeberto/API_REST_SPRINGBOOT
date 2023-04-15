package com.apirest.treinarecife.dto;


import com.apirest.treinarecife.entities.Professor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ProfessorDTO extends BasicDTO{
	private Professor professor;
}
