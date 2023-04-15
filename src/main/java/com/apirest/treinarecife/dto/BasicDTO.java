package com.apirest.treinarecife.dto;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class BasicDTO {
	private String statusCode;
	private ArrayList<String> mensagem;
	
	
}
