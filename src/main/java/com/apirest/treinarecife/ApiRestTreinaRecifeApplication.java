package com.apirest.treinarecife;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.apirest.treinarecife.entities.Curso;
import com.apirest.treinarecife.repositories.CursoRepository;

@SpringBootApplication
public class ApiRestTreinaRecifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestTreinaRecifeApplication.class, args);
	}

//	@Bean
	public CommandLineRunner listarAluno(CursoRepository repository) {
		return (args) -> {
			try {
//				Curso lista = repository.buscarCurso(23l);
//					System.out.println(lista.toString());

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		};
	}
}
