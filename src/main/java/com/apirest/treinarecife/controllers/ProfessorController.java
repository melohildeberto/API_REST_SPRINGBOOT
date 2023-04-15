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

import com.apirest.treinarecife.dto.ProfessorDTO;
import com.apirest.treinarecife.entities.Professor;
import com.apirest.treinarecife.repositories.ProfessorRepository;

@RestController
@RequestMapping(value = "/professor")
public class ProfessorController {
	private final ProfessorRepository repository;

	ProfessorController(ProfessorRepository repository) {
		this.repository = repository;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/listar")
	List<Professor> listar() {
		return (List<Professor>) repository.findAll();
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/cadastrar")
	ResponseEntity<ProfessorDTO> cadastrar(@Valid @RequestBody Professor dados, BindingResult bindingResult) {
		ProfessorDTO response = ProfessorDTO.builder().statusCode("200").mensagem(new ArrayList<String>()).build();
		if (bindingResult.hasErrors()) {
			response.setStatusCode("199");
			for (ObjectError obj : bindingResult.getAllErrors()) {
				response.getMensagem().add(obj.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			List<Professor> professor = (List<Professor>) repository.findByMatricula(dados.getMatricula());
			if (professor.size() > 0) {
				response.getMensagem().add("A matricula já existe");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			dados = repository.save(dados);
			response.setProfessor(dados);
			response.getMensagem().add("Professor cadastrado com sucesso");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get/{id}")
	ResponseEntity<ProfessorDTO> get(@PathVariable Long id) {
		ProfessorDTO response = ProfessorDTO.builder().statusCode("200").mensagem(new ArrayList<String>()).build();
		Optional<Professor> professor = repository.findById(id);
		if(professor.isPresent() == false) {
			response.getMensagem().add("Professor não encontrado");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response.setProfessor(professor.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@CrossOrigin(origins = "*")
	@PutMapping("/atualizar/{id}")
	ResponseEntity<ProfessorDTO> atualizar(@Valid @RequestBody Professor dadosNovo,BindingResult bindingResult, @PathVariable Long id ) {
		ProfessorDTO response = ProfessorDTO.builder().statusCode("200").mensagem(new ArrayList<String>()).build();
		if (bindingResult.hasErrors()) {
			response.setStatusCode("199");
			for (ObjectError obj : bindingResult.getAllErrors()) {
				response.getMensagem().add(obj.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Optional<Professor> professor = repository.findById(id);
		if(professor.isPresent() == false) {
			response.getMensagem().add("Professor não encontrado");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			dadosNovo.setId(id);
			repository.save(dadosNovo);
			response.setProfessor(dadosNovo);
			response.getMensagem().add("Professor atualizado com sucesso");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("/remover/{id}")
	ResponseEntity<String> remover(@PathVariable Long id) {
		Optional<Professor> professor = repository.findById(id);
		if(professor.isPresent() == false) {
			return new ResponseEntity<>("Professor não encontrado", HttpStatus.BAD_REQUEST);
		}else {
			repository.deleteById(id);
			return new ResponseEntity<>("Professor removido", HttpStatus.OK);
		}
		
	}
}
