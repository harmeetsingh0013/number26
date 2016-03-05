/**
 * 
 */
package com.harmeetsingh13.repo;

import java.util.Set;

import com.harmeetsingh13.entities.Transaction;

/**
 * @author Harmeet Singh(Taara)
 *
 */
public interface TransactionRepo {

	public Transaction findTransactionByTransactionId(long transactionId);
	public void saveNewTransaction(Transaction transaction);
	public Set<Long> findTransactionsIdsByTransactionType(String transactionType);
	public void saveOrUpdateTransactionTypes(String transactionType, Set<Long> transactionIds);
}
