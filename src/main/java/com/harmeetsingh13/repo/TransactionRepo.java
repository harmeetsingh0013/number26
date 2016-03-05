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

	/**
	 * Find existing transaction object corresponding to passed transaction-id, otherwise return null.
	 * 
	 * @param transactionId	existing transaction-id.
	 * @return				existing transaction object .
	 */
	public Transaction findTransactionByTransactionId(long transactionId);
	
	/**
	 * Save passed transaction to DB
	 * 
	 * @param transaction	transaction object contains transaction detail.
	 * @see 				Transaction.
	 */
	public void saveNewTransaction(Transaction transaction);
	
	/**
	 * Find transaction ids corresponding to passed transaction-type, otherwise return null.
	 * 
	 * @param transactionType	existing transaction-type.
	 * @return					set of transaction ids.
	 */
	public Set<Long> findTransactionsIdsByTransactionType(String transactionType);
	
	/**
	 * In this, save or update transaction-type with corresponding transaction-ids.
	 * 
	 * @param transactionType	existing or new transaction type.
	 * @param transactionIds	transaction-type corresponding transactions ids.
	 */
	public void saveOrUpdateTransactionTypes(String transactionType, Set<Long> transactionIds);
	
	/**
	 * Sum of child transactions amount corresponding to passed transaction-ids.
	 * 
	 * @param transactionId	parent transaction id.
	 * @return				sum of child transaction amount.
	 */
	public double findChildTransactionsTotalAmountByParentTransactionId(long transactionId);

	/**
	 * Reset memory db
	 */
	public void resetMemoryDB();
}
