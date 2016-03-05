/**
 * 
 */
package com.harmeetsingh13.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents passed request parameter body with transaction details like: 
 * "transaction-type", "transaction-amount" and "parent transaction id".
 * 
 * @author Harmeet Singh(Taara)
 *
 */
public class TransactionDto {
	
	private String type;
	private double amount;
	@JsonProperty(value = "parent_id")
	private Long parentId;
	
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
	 * Gets parent transaction id if exist
	 * @return	parent transaction id
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 *  Create transaction with given parent transaction id.
	 * @param parentId	parent transaction id
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
