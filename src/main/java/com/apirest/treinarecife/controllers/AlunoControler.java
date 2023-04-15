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

import com.apirest.treinarecife.dto.AlunoDTO;
import com.apirest.treinarecife.entities.Aluno;
import com.apirest.treinarecife.repositories.AlunoRepositorio;

@RestController
@RequestMapping(value = "/aluno")
public class AlunoControler {
	private final AlunoRepositorio repository;

	AlunoControler(AlunoRepositorio repository) {
		this.repository = repository;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/listar")
	List<Aluno> listar() {
		return (List<Aluno>) repository.findAll();
	}
	@CrossOrigin(origins = "*")
	@GetMapping("/listarpornome/{nome}")
	List<Aluno> listarPorNome(@PathVariable String nome) {
		return (List<Aluno>) repository.findByNomeLikeIgnoreCase("%"+nome+"%");
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/cadastrar")
	ResponseEntity<AlunoDTO> cadastrar(@Valid @RequestBody Aluno dados, BindingResult bindingResult) {
		AlunoDTO response = AlunoDTO.builder().statusCode("200").mensagem(new ArrayList<String>()).build();
		if (bindingResult.hasErrors()) {
			response.setStatusCode("199");
			for (ObjectError obj : bindingResult.getAllErrors()) {
				response.getMensagem().add(obj.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			dados = repository.save(dados);
			response.setAluno(dados);
			response.getMensagem().add("Aluno cadastrado com sucesso");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get/{id}")
	ResponseEntity<AlunoDTO> get(@PathVariable Long id) {
		AlunoDTO response = AlunoDTO.builder().statusCode("200").mensagem(new ArrayList<String>()).build();
		Optional<Aluno> aluno = repository.findById(id);
		if(aluno.isPresent() == false) {
			response.getMensagem().add("Aluno não encontrado");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			response.setAluno(aluno.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@CrossOrigin(origins = "*")
	@PutMapping("/atualizar/{id}")
	ResponseEntity<AlunoDTO> atualizar(@Valid @RequestBody Aluno dadosNovo,BindingResult bindingResult, @PathVariable Long id ) {

		AlunoDTO response = AlunoDTO.builder().statusCode("200").mensagem(new ArrayList<String>()).build();
		if (bindingResult.hasErrors()) {
			response.setStatusCode("199");
			for (ObjectError obj : bindingResult.getAllErrors()) {
				response.getMensagem().add(obj.getDefaultMessage());
			}
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Optional<Aluno> aluno = repository.findById(id);
		if(aluno.isPresent() == false) {
			response.getMensagem().add("Aluno não encontrado");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}else {
			dadosNovo.setId(id);
			repository.save(dadosNovo);
			response.setAluno(dadosNovo);
			response.getMensagem().add("Aluno atualizado com sucesso");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("/remover/{id}")
	ResponseEntity<String> remover(@PathVariable Long id) {
		Optional<Aluno> aluno = repository.findById(id);
		if(aluno.isPresent() == false) {
			return new ResponseEntity<>("Aluno não encontrado", HttpStatus.BAD_REQUEST);
		}else {
			repository.deleteById(id);
			return new ResponseEntity<>("Aluno removido", HttpStatus.OK);
		}
		
	}
}
