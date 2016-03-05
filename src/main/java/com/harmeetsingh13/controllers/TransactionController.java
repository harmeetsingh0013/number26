/**
 * 
 */
package com.harmeetsingh13.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
import com.harmeetsingh13.entities.Transaction;
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
	
	@RequestMapping(value = "/transaction/{transaction_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, String> newTransaction(@PathVariable("transaction_id") long transactionId, 
			@RequestBody TransactionDto dto, BindingResult results) throws JsonProcessingException {
		
		Map<String, String> response = new HashMap<>();
		response.put("status", "ok");
		
		transactionDtoValidator.validate(dto, results);
		if(results.hasErrors()){
			response =  results.getFieldErrors().stream()
				.collect(Collectors.toMap((FieldError field) -> field.getField(), (FieldError field) -> field.getDefaultMessage()));
			return response;
		}
		
		try{
			transactionService.createNewTransaction(transactionId, dto);
		}catch(TransactionNotFound ex){
			response = new HashMap<>();
			response.put(ex.getErrorCode(), ex.getMessage());
		}
		
		return response;
	}
	
	@RequestMapping(value = "/transaction/{type}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String transactionDetail(@PathVariable("transaction_id") long transactionId) throws JsonProcessingException {
		
		String response = "";
		ObjectMapper mapper = new ObjectMapper();
		try{
			Transaction transaction = transactionService.findTransactionByTransactionId(transactionId);
			TransactionDto dto = new TransactionDto();
			dto.setAmount(transaction.getAmount());
			dto.setType(transaction.getType());
			if(transaction.getParentTransaction() != null){
				dto.setParentId(transaction.getParentTransaction().getId());
			}
			response = mapper.writeValueAsString(dto);
		}catch (TransactionNotFound ex){
			Map<String, String> errors = new HashMap<>();
			errors.put(ex.getErrorCode(), ex.getMessage());
			response = mapper.writeValueAsString(errors);
		}
		return response;
	}
	
	@RequestMapping(value = "/types/{type}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String transactionsByType(@PathVariable("type") String transactionType) throws JsonProcessingException {
		
		String response = "";
		ObjectMapper mapper = new ObjectMapper();
		try{
			Set<Long> transactionsIds = transactionService.findTransactionIdsByTransactionType(transactionType);
			response = mapper.writeValueAsString(transactionsIds);
		}catch (TransactionNotFound ex){
			Map<String, String> errors = new HashMap<>();
			errors.put(ex.getErrorCode(), ex.getMessage());
			response = mapper.writeValueAsString(errors);
		}
		return response;
	}
	
	@RequestMapping(value = "/sum/{transaction_id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, String> transactionsSum(@PathVariable("transaction_id") long transactionId) throws JsonProcessingException {
		
		Map<String, String> response = new HashMap<>();
		try{
			double sum = transactionService.findTransactionsTotalAmountByTransactionId(transactionId);
			response.put("sum", String.valueOf(sum));
		}catch (TransactionNotFound ex){
			response.put(ex.getErrorCode(), ex.getMessage());
		}
		
		return response;
	}
}
