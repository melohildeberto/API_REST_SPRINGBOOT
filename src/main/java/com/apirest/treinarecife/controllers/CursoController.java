package com.apirest.treinarecife.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.treinarecife.dto.CursoDTO;
import com.apirest.treinarecife.entities.Curso;
import com.apirest.treinarecife.entities.Professor;
import com.apirest.treinarecife.repositories.CursoRepository;
import com.apirest.treinarecife.repositories.ProfessorRepository;

@RestController
@RequestMapping(value = "/curso")
public class CursoController {
	private final CursoRepository repositoryCurso;
	private final ProfessorRepository repositoryProfessor;

	CursoController(CursoRepository repositoryCurso, ProfessorRepository repositoryProfessor) {
		this.repositoryCurso = repositoryCurso;
		this.repositoryProfessor = repositoryProfessor;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/listar")
	List<Curso> listar() {
		return (List<Curso>) repositoryCurso.findAll();
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/cadastrar")
	ResponseEntity<CursoDTO> cadastrar(@Valid @RequestBody Curso dados, BindingResult bindingResult) {
		CursoDTO response = CursoDTO.builder().statusCode("200").mensagem(new ArrayList<String>()).build();
		if (bindingResult.hasErrors()) {
			response.setStatusCode("199");
			for (ObjectError obj : bindingResult.getAllErrors()) {
				response.getMensagem().add(obj.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			List<Professor> professor = (List<Professor>) repositoryProfessor.findByMatricula(dados.getProfessor().getMatricula());
			if (professor.size() == 0) {
				response.getMensagem().add("Professor não encontrado");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				dados.setProfessor(professor.get(0));
				dados = repositoryCurso.save(dados);
				response.setCurso(dados);
				response.getMensagem().add("Curso cadastrado com sucesso");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get/{id}")
	ResponseEntity<CursoDTO> get(@PathVariable Long id) {
		CursoDTO response = CursoDTO.builder().statusCode("200").mensagem(new ArrayList<String>()).build();
		Optional<Curso> curso = repositoryCurso.findById(id);
		if (curso.isPresent() == false) {
			response.getMensagem().add("Curso não encontrado");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.setCurso(curso.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@CrossOrigin(origins = "*")
	@PutMapping("/atualizar/{id}")
	ResponseEntity<CursoDTO> atualizar(@Valid @RequestBody Curso dadosNovo, BindingResult bindingResult,
			@PathVariable Long id) {
		CursoDTO response = CursoDTO.builder().statusCode("200").mensagem(new ArrayList<String>()).build();
		if (bindingResult.hasErrors()) {
			response.setStatusCode("199");
			for (ObjectError obj : bindingResult.getAllErrors()) {
				response.getMensagem().add(obj.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Optional<Curso> curso = repositoryCurso.findById(id);
		if (curso.isPresent() == false) {
			response.getMensagem().add("Curso não encontrado");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			dadosNovo.setId(id);
			repositoryCurso.save(dadosNovo);
			response.setCurso(dadosNovo);
			response.getMensagem().add("Curso atualizado com sucesso");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("/remover/{id}")
	ResponseEntity<String> remover(@PathVariable Long id) {
		Optional<Curso> curso = repositoryCurso.findById(id);
		//Curso curso = repositoryCurso.buscarCurso(id);
		if (curso == null) {
			return new ResponseEntity<>("Curso não encontrado", HttpStatus.BAD_REQUEST);
		} else {
			repositoryCurso.deleteById(id);
			return new ResponseEntity<>("Curso removido", HttpStatus.OK);
		}

	}
}
