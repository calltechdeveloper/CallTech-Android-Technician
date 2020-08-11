package com.calltechservice.model.response;

import com.google.gson.annotations.SerializedName;

public class InvoiceResponseModel {

	@SerializedName("lifejacket_id")
	private String lifejacketId;
	@SerializedName("transaction_no")
	private String transactionNo;
	@SerializedName("date")
	private String date;
	@SerializedName("amount")
	private String amount;
	@SerializedName("package")
	private String pPackage;
	@SerializedName("remark")
	private String remark;
	@SerializedName("name")
	private String name;

	public String getLifejacketId() {
		return lifejacketId;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public String getDate() {
		return date;
	}

	public String getAmount() {
		return amount;
	}

	public String getpPackage() {
		return pPackage;
	}

	public String getRemark() {
		return remark;
	}

	public String getName() {
		return name;
	}
}
