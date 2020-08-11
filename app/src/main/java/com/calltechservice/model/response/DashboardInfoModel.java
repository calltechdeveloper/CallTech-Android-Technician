package com.calltechservice.model.response;

import com.google.gson.annotations.SerializedName;

public class DashboardInfoModel {

	@SerializedName("referral")
	private int referral;

	@SerializedName("unpaid_downline")
	private int unpaidDownline;

	@SerializedName("downlines")
	private int downlines;

	@SerializedName("paid_downline")
	private int paidDownline;

	@SerializedName("register_wallet")
	private String registerWallet;

	@SerializedName("ewallet_balance")
	private String ewalletBalance;

	public int getReferral(){
		return referral;
	}

	public int getUnpaidDownline(){
		return unpaidDownline;
	}

	public int getDownlines(){
		return downlines;
	}

	public int getPaidDownline(){
		return paidDownline;
	}

	public String getRegisterWallet(){
		return registerWallet;
	}

	public String getEwalletBalance(){
		return ewalletBalance;
	}
}