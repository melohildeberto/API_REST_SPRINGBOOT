package com.apirest.treinarecife.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.treinarecife.entities.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{
	Iterable<Professor> findByNomeLike(String nome);
	Iterable<Professor> findByMatricula(int matricula);
}
