/**
 * 
 */
package com.harmeetsingh13.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.harmeetsingh13.dtos.TransactionDto;

/**
 * @author Harmeet Singh(Taara)
 *
 */

@Component
public class TransactionDtoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return TransactionDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "type", "type.not.empty", "Transaction type not empty");
		TransactionDto dto = (TransactionDto) target;
		if(dto.getAmount() <= 0){
			errors.rejectValue("amount", "amount.not.valid", "Transaction amount not valid");
		}
	}

}
