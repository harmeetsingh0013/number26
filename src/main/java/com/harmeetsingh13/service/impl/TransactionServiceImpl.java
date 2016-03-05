/**
 * 
 */
package com.harmeetsingh13.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harmeetsingh13.dtos.TransactionDto;
import com.harmeetsingh13.entities.Transaction;
import com.harmeetsingh13.exceptions.TransactionNotFound;
import com.harmeetsingh13.repo.TransactionRepo;
import com.harmeetsingh13.service.TransactionService;

/**
 * @author Harmeet Singh(Taara)
 *
 */

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionRepo transactionRepo;
	
	@Override
	public Transaction findTransactionByTransactionId(long transactionId) throws TransactionNotFound {
		
		Transaction transaction = transactionRepo.findTransactionByTransactionId(transactionId);
		if(transaction != null){
			return transaction;
		}
		throw new TransactionNotFound("1001", "Transaction not found.");
	}

	@Override
	public void createNewTransaction(long transactionId, TransactionDto dto) throws TransactionNotFound {
		
		Transaction existingTransaction = transactionRepo.findTransactionByTransactionId(transactionId);
		if(existingTransaction != null){
			throw new TransactionNotFound("1004", "Transaction id already exists.");
		}
		
		Transaction transaction = new Transaction(transactionId);
		if(dto.getParentId() != null){
			Transaction parentTransaction = transactionRepo.findTransactionByTransactionId(dto.getParentId());
			if(parentTransaction == null){
				throw new TransactionNotFound("1002", "Parent Transaction not found.");
			}
			transaction.setParentTransaction(parentTransaction);
		}
		
		transaction.setAmount(dto.getAmount());
		transaction.setType(dto.getType().trim());
		
		transactionRepo.saveNewTransaction(transaction);
		
		Set<Long> transactionIds = transactionRepo.findTransactionsIdsByTransactionType(transaction.getType().toLowerCase());
		if(transactionIds == null){
			transactionIds = new HashSet<>();
		}
		transactionIds.add(transactionId);
		transactionRepo.saveOrUpdateTransactionTypes(transaction.getType().toLowerCase(), transactionIds);
	}

	@Override
	public Set<Long> findTransactionIdsByTransactionType(String transactionType) throws TransactionNotFound {
		Set<Long> transactionIds = transactionRepo.findTransactionsIdsByTransactionType(transactionType.trim().toLowerCase());
		if(transactionIds == null){
			throw new TransactionNotFound("1003", "Transactions not found.");
		}
		return transactionIds;
	}

	@Override
	public double findTransactionsTotalAmountByTransactionId(long transactionId) throws TransactionNotFound {
		Transaction transaction = transactionRepo.findTransactionByTransactionId(transactionId);
		if(transaction == null){
			throw new TransactionNotFound("1001", "Transaction not found.");
		}
		double totalAmount = transaction.getAmount();
		totalAmount += transactionRepo.findChildTransactionsTotalAmountByParentTransactionId(transactionId);
		return totalAmount;
	}
	
	
}
