package com.apirest.treinarecife.exceptions;

public class AlunoNotFoundException extends RuntimeException {

	public AlunoNotFoundException(Long id) {
		super("Aluno não encontrado " + id);
	}
}