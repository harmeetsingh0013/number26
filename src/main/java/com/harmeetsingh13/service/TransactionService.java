/**
 * 
 */
package com.harmeetsingh13.service;

import java.util.Set;

import com.harmeetsingh13.dtos.TransactionDto;
import com.harmeetsingh13.entities.Transaction;
import com.harmeetsingh13.exceptions.TransactionNotFound;

/**
 * @author Harmeet Singh(Taara)
 *
 */
public interface TransactionService {

	public Transaction findTransactionByTransactionId(long transactionId) throws TransactionNotFound;
	public void createNewTransaction(long transactionId, TransactionDto dto) throws TransactionNotFound;
	public Set<Long> findTransactionIdsByTransactionType(String transactionType) throws TransactionNotFound;
	public double findTransactionsTotalAmountByTransactionId(long transactionId) throws TransactionNotFound;
}
