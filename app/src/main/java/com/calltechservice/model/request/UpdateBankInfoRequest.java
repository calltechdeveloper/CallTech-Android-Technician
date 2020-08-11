package com.calltechservice.model.request;

import com.google.gson.annotations.SerializedName;

public class UpdateBankInfoRequest {

	@SerializedName("account_number")
	private String accountNumber;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("swift_code")
	private String swiftCode;

	@SerializedName("account_name")
	private String accountName;

	@SerializedName("branch_name")
	private String branchName;

	@SerializedName("bank_name")
	private String bankName;


	public String getAccountNumber() {
		return accountNumber;
	}

	public String getUserId() {
		return userId;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setAccountNumber(String accountNumber){
		this.accountNumber = accountNumber;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public void setSwiftCode(String swiftCode){
		this.swiftCode = swiftCode;
	}

	public void setAccountName(String accountName){
		this.accountName = accountName;
	}

	public void setBranchName(String branchName){
		this.branchName = branchName;
	}

	public void setBankName(String bankName){
		this.bankName = bankName;
	}
}