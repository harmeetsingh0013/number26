/**
 * 
 */
package com.harmeetsingh13.service.test;

import java.util.Set;

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
		thrown.expect(hasProperty("errorCode", is("1001")));
		
		transactionService.findTransactionByTransactionId(12);
	}
	
	@Test
	public void test2ParentTransactionNotFound() throws TransactionNotFound {
		thrown.expect(TransactionNotFound.class);
		thrown.expectMessage("Parent Transaction not found");
		thrown.expect(hasProperty("errorCode", is("1002")));
		
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
		dto.setAmount(5000);
		dto.setType("car");
		dto.setParentId(1l);
		transactionService.createNewTransaction(10, dto);
		Transaction existTransaction = transactionService.findTransactionByTransactionId(10);
		
		assertNotNull(existTransaction);
		assertThat(10l, is(existTransaction.getId()));
		assertThat(1l, is(existTransaction.getParentTransaction().getId()));
	}
	
	@Test
	public void test5TransationsNotFoundByTransactionType() throws TransactionNotFound {
		thrown.expect(TransactionNotFound.class);
		thrown.expectMessage("Transactions not found");
		thrown.expect(hasProperty("errorCode", is("1003")));
		
		transactionService.findTransactionIdsByTransactionType("shopping");
	}
	
	@Test
	public void test6TransactionsByTransactionType() throws TransactionNotFound {
		
		Set<Long> transactionIds1 = transactionService.findTransactionIdsByTransactionType("car");
		Set<Long> transactionIds2 = transactionService.findTransactionIdsByTransactionType("CAR");
		Set<Long> transactionIds3 = transactionService.findTransactionIdsByTransactionType("CaR");
		
		assertNotNull(transactionIds1);
		assertNotNull(transactionIds2);
		assertNotNull(transactionIds3);
		
		assertThat(2, is(transactionIds1.size()));
		assertThat(2, is(transactionIds2.size()));
		assertThat(2, is(transactionIds3.size()));
		
		assertThat(1, not(transactionIds1.size()));
		assertThat(1, not(transactionIds2.size()));
		assertThat(1, not(transactionIds3.size()));
		
		assertThat(transactionIds1, containsInAnyOrder(1l, 10l));
		assertThat(transactionIds2, containsInAnyOrder(1l, 10l));
		assertThat(transactionIds3, containsInAnyOrder(1l, 10l));
		
		assertThat(transactionIds1, is(transactionIds2));
		assertThat(transactionIds2, is(transactionIds3));
	}
	
	@Test
	public void test7TransactionNotFoundWhenTransactionsTotal() throws TransactionNotFound {
		thrown.expect(TransactionNotFound.class);
		thrown.expectMessage("Transaction not found");
		thrown.expect(hasProperty("errorCode", is("1001")));
		
		transactionService.findTransactionsTotalAmountByTransactionId(12);
	}
	
	@Test
	public void test8TransactionTotalAmount() throws TransactionNotFound {
		
		double transaction1Amount = transactionService.findTransactionsTotalAmountByTransactionId(1);
		double transaction2Amount = transactionService.findTransactionsTotalAmountByTransactionId(10);
		
		assertThat(new Double(1000), is(transaction1Amount));
		assertThat(new Double(6000), is(transaction2Amount));
	}
	
	@Test
	public void test9MultiHierarchyTransctionTotalAmount() throws TransactionNotFound {
		
		for (long i = 11; i <= 20; i++) {
			TransactionDto dto = new TransactionDto();
			if(i == 11){
				dto.setAmount(1000 * i);
				dto.setType("car"+i);
				transactionService.createNewTransaction(i, dto);
				continue;
			}
			dto.setAmount(1000 * i);
			dto.setType("car"+i);
			dto.setParentId(i - 1);
			transactionService.createNewTransaction(i, dto);
		}
		
		double transaction11Amount = transactionService.findTransactionsTotalAmountByTransactionId(11);
		double transaction12Amount = transactionService.findTransactionsTotalAmountByTransactionId(12);
		double transaction13Amount = transactionService.findTransactionsTotalAmountByTransactionId(13);
		double transaction14Amount = transactionService.findTransactionsTotalAmountByTransactionId(14);
		double transaction15Amount = transactionService.findTransactionsTotalAmountByTransactionId(15);
		double transaction16Amount = transactionService.findTransactionsTotalAmountByTransactionId(16);
		double transaction17Amount = transactionService.findTransactionsTotalAmountByTransactionId(17);
		double transaction18Amount = transactionService.findTransactionsTotalAmountByTransactionId(18);
		double transaction19Amount = transactionService.findTransactionsTotalAmountByTransactionId(19);
		double transaction20Amount = transactionService.findTransactionsTotalAmountByTransactionId(20);
		
		assertThat(new Double(11000), is(transaction11Amount));
		assertThat(new Double(23000), is(transaction12Amount));
		assertThat(new Double(36000), is(transaction13Amount));
		assertThat(new Double(50000), is(transaction14Amount));
		assertThat(new Double(65000), is(transaction15Amount));
		assertThat(new Double(81000), is(transaction16Amount));
		assertThat(new Double(98000), is(transaction17Amount));
		assertThat(new Double(116000), is(transaction18Amount));
		assertThat(new Double(135000), is(transaction19Amount));
		assertThat(new Double(155000), is(transaction20Amount));
	}
}
