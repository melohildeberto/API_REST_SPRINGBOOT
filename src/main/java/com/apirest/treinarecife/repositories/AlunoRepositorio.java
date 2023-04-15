package com.apirest.treinarecife.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apirest.treinarecife.entities.Aluno;

@Repository
public interface AlunoRepositorio extends CrudRepository<Aluno, Long> {
	List<Aluno> findByNomeLikeIgnoreCase(String nome);
}
