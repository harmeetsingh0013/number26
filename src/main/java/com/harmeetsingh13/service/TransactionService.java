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

	/**
	 * Return the existing transaction object corresponding to transaction-id.
	 * 
	 * @param transactionId			existing transaction-id.
	 * @return						existing transaction object.
	 * @throws TransactionNotFound	If passed transaction-id not exist.
	 */
	public Transaction findTransactionByTransactionId(long transactionId) throws TransactionNotFound;
	
	/**
	 * In this method, create new transaction object with new transaction-id and transaction detail passed using dto object.
	 * The dto object have details like "transaction-type", "amount" and "parent transaction id". If the details contains 
	 * "parent transaction id", then current transaction is create as child of parent transaction, otherwise transaction
	 * is independent.
	 * 
	 * For creating new transaction, pass new transaction-id, otherwise existing transaction exception will throw.
	 * If passed parent-id not exist, then it will throw and exception.
	 * 
	 * @param transactionId			new transaction-id.
	 * @param dto					transaction detail.
	 * @see							TransactionDto.
	 * @throws TransactionNotFound	If passed transaction-id already used, If passed parent-id not exist.
	 */
	public void createNewTransaction(long transactionId, TransactionDto dto) throws TransactionNotFound;
	
	/**
	 * Returns all transaction ids corresponding to passed transaction-type. If the passed transaction-type not exist,
	 * it will thrown an exception. Passed transaction-type is case-insensitive like "car", "CAR" and "CaR" all are same.
	 * 
	 * @param transactionType		type of transaction.
	 * @return						All transaction ids corresponding to transaction type.
	 * @throws TransactionNotFound	If passed transaction-type not exist.
	 */
	public Set<Long> findTransactionIdsByTransactionType(String transactionType) throws TransactionNotFound;
	
	/**
	 * Returns sum of passed transaction-id amount and also include their children's transactions amount. like 
	 * one transaction have amount = 1000 and their two children's have 2000 and 3000. Now this method return total
	 * 6000.
	 * 
	 * @param transactionId			existing transaction-id.
	 * @return						sum of passed transaction-id amount include their childern's amount.
	 * @throws TransactionNotFound	If passed transaction-id not exist.
	 */
	public double findTransactionsTotalAmountByTransactionId(long transactionId) throws TransactionNotFound;
}
