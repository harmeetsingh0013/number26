/**
 * 
 */
package com.harmeetsingh13.entities;


/**
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public Transaction getParentTransaction() {
		return parentTransaction;
	}
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
