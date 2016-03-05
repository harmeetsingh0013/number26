/**
 * 
 */
package com.harmeetsingh13.entities;


/**
 * Represent a transaction with details like 
 * "transaction-id", "transaction-type", "transaction amount" and "parent transaction" if exist.
 * 
 * @author Harmeet Singh(Taara)
 *
 */
public class Transaction {

	private Long id;
	private String type;
	private double amount;
	private Transaction parentTransaction;
	
	public Transaction(long id){
		this.id = id;
	}
	
	/**
	 * Gets the transaction-id
	 * @return	transaction-id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Create transaction with given id.
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Gets the transaction-type
	 * @return transaction-type
	 */
	public String getType() {
		return type;
	}
	/**
	 * Create transaction with given type.
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Gets the transaction amount
	 * @return	transaction amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * Create transaction with given amount.
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * Gets the parent transaction otherwise null.
	 * @return	parent transaction
	 */
	public Transaction getParentTransaction() {
		return parentTransaction;
	}
	/**
	 * Create transaction with given parent transaction.
	 * @param parentTransaction	parent transaction object
	 */
	public void setParentTransaction(Transaction parentTransaction) {
		this.parentTransaction = parentTransaction;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", parentId=" + parentTransaction.getId() + ", type="
				+ type + ", amount=" + amount + "]";
	}
}
