/**
 * 
 */
package com.harmeetsingh13.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harmeetsingh13.dtos.TransactionDto;
import com.harmeetsingh13.exceptions.TransactionNotFound;
import com.harmeetsingh13.service.TransactionService;
import com.harmeetsingh13.validators.TransactionDtoValidator;

/**
 * @author Harmeet Singh(Taara)
 *
 */

@RestController
@RequestMapping(value = "/transactionservice")
public class TransactionController {

	@Autowired
	private TransactionDtoValidator transactionDtoValidator;
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(value = "/transaction/{transaction_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String newTransaction(@PathVariable("transaction_id") long transactionId, 
			@RequestBody TransactionDto dto, BindingResult results) throws JsonProcessingException {
		
		String response = "{\"status\": \"ok\"}";
		ObjectMapper mapper = new ObjectMapper();
		
		transactionDtoValidator.validate(dto, results);
		if(results.hasErrors()){
			Map<String, String> errors =  results.getFieldErrors().stream()
				.collect(Collectors.toMap((FieldError field) -> field.getField(), (FieldError field) -> field.getDefaultMessage()));
			response = mapper.writeValueAsString(errors);
			return response;
		}
		
		try{
			transactionService.createNewTransaction(transactionId, dto);
		}catch(TransactionNotFound ex){
			Map<String, String> errors = new HashMap<>();
			errors.put(ex.getErrorCode(), ex.getMessage());
			response = mapper.writeValueAsString(errors);
		}
		
		return response;
	}
}
