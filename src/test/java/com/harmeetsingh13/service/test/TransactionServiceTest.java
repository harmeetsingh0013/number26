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
	public void testA_TransactionNotFound() throws TransactionNotFound {
		thrown.expect(TransactionNotFound.class);
		thrown.expectMessage("Transaction not found");
		thrown.expect(hasProperty("errorCode", is("1001")));
		
		transactionService.findTransactionByTransactionId(12);
	}
	
	@Test
	public void testB_ParentTransactionNotFound() throws TransactionNotFound {
		thrown.expect(TransactionNotFound.class);
		thrown.expectMessage("Parent Transaction not found");
		thrown.expect(hasProperty("errorCode", is("1002")));
		
		TransactionDto dto = new TransactionDto();
		dto.setParentId(1L);
		
		transactionService.createNewTransaction(10, dto);
	}
	
	@Test
	public void testC_NewTransactionWithoutParent() throws TransactionNotFound {
		
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
	public void testD_NewTransactionWithParent() throws TransactionNotFound {
		
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
	public void testE_TransationsNotFoundByTransactionType() throws TransactionNotFound {
		thrown.expect(TransactionNotFound.class);
		thrown.expectMessage("Transactions not found");
		thrown.expect(hasProperty("errorCode", is("1003")));
		
		transactionService.findTransactionIdsByTransactionType("shopping");
	}
	
	@Test
	public void testF_TransactionsByTransactionType() throws TransactionNotFound {
		
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
	public void testG_TransactionNotFoundWhenTransactionsTotal() throws TransactionNotFound {
		thrown.expect(TransactionNotFound.class);
		thrown.expectMessage("Transaction not found");
		thrown.expect(hasProperty("errorCode", is("1001")));
		
		transactionService.findTransactionsTotalAmountByTransactionId(12);
	}
	
	@Test
	public void testH_TransactionTotalAmount() throws TransactionNotFound {
		
		double transaction1Amount = transactionService.findTransactionsTotalAmountByTransactionId(1);
		double transaction2Amount = transactionService.findTransactionsTotalAmountByTransactionId(10);
		
		assertThat(new Double(5000), is(transaction2Amount));
		assertThat(new Double(6000), is(transaction1Amount));
	}
	
	@Test
	public void testI_MultipleTransctionsTotalAmount() throws TransactionNotFound {
		
		for (long i = 0; i <= 5; i++) {
			TransactionDto dto = new TransactionDto();
			dto.setType("shopping");
			if(i == 0){
				dto.setAmount(10000);
				transactionService.createNewTransaction(1000, dto);
				continue;
			}
			dto.setAmount(1000 * i);
			dto.setParentId(1000L);
			transactionService.createNewTransaction(i + 1000, dto);
		}
		
		double parentTransactionAmount = transactionService.findTransactionsTotalAmountByTransactionId(1000);
		double transaction1Amount = transactionService.findTransactionsTotalAmountByTransactionId(1001);
		double transaction2Amount = transactionService.findTransactionsTotalAmountByTransactionId(1002);
		double transaction3Amount = transactionService.findTransactionsTotalAmountByTransactionId(1003);
		double transaction4Amount = transactionService.findTransactionsTotalAmountByTransactionId(1004);
		double transaction5Amount = transactionService.findTransactionsTotalAmountByTransactionId(1005);
		
		assertThat(new Double(25000), is(parentTransactionAmount));
		assertThat(new Double(1000), is(transaction1Amount));
		assertThat(new Double(2000), is(transaction2Amount));
		assertThat(new Double(3000), is(transaction3Amount));
		assertThat(new Double(4000), is(transaction4Amount));
		assertThat(new Double(5000), is(transaction5Amount));
	}
	
	@Test
	public void testJ_MultipleTransactionsByTransactionType() throws TransactionNotFound {
		
		Set<Long> transactionIds = transactionService.findTransactionIdsByTransactionType("shopping");
		assertThat(transactionIds, notNullValue());
		assertThat(6, is(transactionIds.size()));
		assertThat(transactionIds, containsInAnyOrder(1000l, 1001l, 1002l, 1003l, 1004l, 1005l));
	}
}
