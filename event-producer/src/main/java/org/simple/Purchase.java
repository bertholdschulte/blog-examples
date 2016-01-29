package org.simple;

import java.io.Serializable;

/**
 * tiny purchase type, more attributes follow...
 * 
 */
public class Purchase implements Serializable{

	private static final long serialVersionUID = -3450493831191972277L;
	private String id;
	private String sureName;
	private boolean isInvoiceRequested;

	public Purchase(){
		
	}

	public Purchase(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSureName() {
		return sureName;
	}

	public boolean isInvoiceRequested() {
		return isInvoiceRequested;
	}

	public void setInvoiceRequested(boolean isInvoiceRequested) {
		this.isInvoiceRequested = isInvoiceRequested;
	}
}
