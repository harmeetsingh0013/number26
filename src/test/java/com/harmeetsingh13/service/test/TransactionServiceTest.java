/**
 * 
 */
package com.harmeetsingh13.service.test;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.harmeetsingh13.config.WebConfiguration;
import com.harmeetsingh13.dtos.TransactionDto;
import com.harmeetsingh13.entities.Transaction;
import com.harmeetsingh13.exceptions.TransactionNotFound;
import com.harmeetsingh13.service.TransactionService;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Harmeet Singh(Taara)
 *
 */
@WebAppConfiguration
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfiguration.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TransactionServiceTest {

	@Autowired
	private TransactionService transactionService;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void test1TransactionNotFound() throws TransactionNotFound {
		thrown.expect(TransactionNotFound.class);
		thrown.expectMessage("Transaction not found");
		
		transactionService.findTransactionByTransactionId(12);
	}
	
	@Test
	public void test2ParentTransactionNotFound() throws TransactionNotFound {
		thrown.expect(TransactionNotFound.class);
		thrown.expectMessage("Parent Transaction not found");
		
		TransactionDto dto = new TransactionDto();
		dto.setParentId(1L);
		
		transactionService.createNewTransaction(10, dto);
	}
	
	@Test
	public void test3NewTransactionWithoutParent() throws TransactionNotFound {
		
		TransactionDto dto = new TransactionDto();
		dto.setAmount(1000);
		dto.setType("car");
		transactionService.createNewTransaction(1, dto);
		Transaction existTransaction = transactionService.findTransactionByTransactionId(1);
		
		assertNotNull(existTransaction);
		assertThat(1l, is(existTransaction.getId()));
		assertThat(0l, not(existTransaction.getId()));
	}
	
	@Test
	public void test4NewTransactionWithParent() throws TransactionNotFound {
		
		TransactionDto dto = new TransactionDto();
		dto.setAmount(1000);
		dto.setType("car");
		dto.setParentId(1l);
		transactionService.createNewTransaction(10, dto);
		Transaction existTransaction = transactionService.findTransactionByTransactionId(10);
		
		assertNotNull(existTransaction);
		assertThat(10l, is(existTransaction.getId()));
		assertThat(1l, is(existTransaction.getParentTransaction().getId()));
	}
	
	@Test
	public void test5TransationNotFoundByTransactionType() throws TransactionNotFound {
		thrown.expect(TransactionNotFound.class);
		thrown.expectMessage("Transactions not found");
		
		transactionService.findTransactionIdsByTransactionType("shopping");
	}
	
	public void testTransactionsByTransactionType() throws TransactionNotFound {
		
	}
}
