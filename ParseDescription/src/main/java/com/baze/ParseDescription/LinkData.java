package com.baze.ParseDescription;

public class LinkData {

	private int rowNumber;
	private String data;

	public LinkData() {
	}

	public LinkData(int rowNumber, String data) {
		super();
		this.rowNumber = rowNumber;
		this.data = data;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getLink() {
		return data;
	}

	public void setLink(String data) {
		this.data = data;
	}

}
