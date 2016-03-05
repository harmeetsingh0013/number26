/**
 * 
 */
package com.harmeetsingh13.dtos;

/**
 * @author Harmeet Singh(Taara)
 *
 */
public class TransactionDto {

	private String type;
	private double amount;
	private Long parentId;
	
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
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
