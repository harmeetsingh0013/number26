/**
 * 
 */
package com.harmeetsingh13.repo.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.harmeetsingh13.entities.Transaction;
import com.harmeetsingh13.repo.TransactionRepo;

/**
 * @author Harmeet Singh(Taara)
 *
 */

@Repository
public class TransactionRepoImpl implements TransactionRepo{

	private static Map<Long, Transaction> transactions = new HashMap<>();
	private static Map<String, Set<Long>> transactionTypes = new HashMap<>();
	
	@Override
	public Transaction findTransactionByTransactionId(long transactionId) {
		return transactions.get(transactionId);
	}

	@Override
	public void saveNewTransaction(Transaction transaction) {
		transactions.put(transaction.getId(), transaction);
	}

	@Override
	public Set<Long> findTransactionsIdsByTransactionType(String transactionType) {
		return transactionTypes.get(transactionType);
	}

	@Override
	public void saveOrUpdateTransactionTypes(String transactionType, Set<Long> transactionIds) {
		transactionTypes.put(transactionType, transactionIds);
	}

	@Override
	public double findChildTransactionsTotalAmountByParentTransactionId(long transactionId) {
		return transactions.values().stream()
				.filter(transaction -> transaction.getParentTransaction() != null)
				.filter(transaction -> transaction.getParentTransaction().getId().equals(transactionId))
				.mapToDouble(transaction -> transaction.getAmount())
				.sum();
	}

	@Override
	public void resetMemoryDB() {
		transactions = new HashMap<>();
		transactionTypes = new HashMap<>();
	}
}
