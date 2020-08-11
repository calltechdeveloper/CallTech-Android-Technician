package com.calltechservice.model.response;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("ref_id")
    private String refId;

    @SerializedName("country")
    private String country;
    @SerializedName("currency")
    private String currency;
    @SerializedName("code")
    private String code;

    @SerializedName("symbol")
    private String symbol;

    @SerializedName("type")
    private String type;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @SerializedName("t_code")
    private String tCode;

    @SerializedName("master_account")
    private String masterAccount;

    @SerializedName("device_type")
    private Object deviceType;

    @SerializedName("ethereum")
    private String ethereum;

    @SerializedName("password")
    private String password;

    @SerializedName("id_no")
    private String idNo;

    @SerializedName("ethereumc")
    private String ethereumc;

    @SerializedName("admin_status")
    private String adminStatus;

    @SerializedName("id")
    private String id;

    @SerializedName("state")
    private String state;

    @SerializedName("issued")
    private String issued;

    @SerializedName("binary_pos")
    private String binaryPos;

    @SerializedName("aboutus")
    private String aboutus;

    @SerializedName("profile_pic")
    private String image;

    @SerializedName("device_id")
    private String deviceId;

    @SerializedName("swift_code")
    private String swiftCode;

    @SerializedName("user_plan")
    private String userPlan;

    @SerializedName("mobile")
    private String telephone;

    @SerializedName("last_login_date")
    private String lastLoginDate;

    @SerializedName("zipcode")
    private String zipcode;

    @SerializedName("acc_name")
    private String accName;

    @SerializedName("registration_date")
    private String registrationDate;

    @SerializedName("ripple")
    private String ripple;

    @SerializedName("ripple_tag")
    private String rippleDTag;

    @SerializedName("provider_id")
    private String userId;

    @SerializedName("dob")
    private String dob;

    @SerializedName("designation")
    private String designation;

    @SerializedName("entertainment_id")
    private String entertainmentId;

    @SerializedName("city")
    private String city;

    @SerializedName("merried_status")
    private String merriedStatus;

    @SerializedName("id_card")
    private String idCard;

    @SerializedName("bank_nm")
    private String bankNm;

    @SerializedName("admin_remark1")
    private String adminRemark1;

    @SerializedName("issue_date")
    private String issueDate;

    @SerializedName("payment_proof")
    private String paymentProof;

    @SerializedName("first_name")
    private String firstName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("user_status")
    private String userStatus;

    @SerializedName("address")
    private String address;

    @SerializedName("current_login_date")
    private String currentLoginDate;

    @SerializedName("sex")
    private String sex;

    @SerializedName("payment_status")
    private String paymentStatus;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("nom_id")
    private String nomId;

    @SerializedName("user_rank_name")
    private String userRankName;

    @SerializedName("branch_nm")
    private String branchNm;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("ac_no")
    private String acNo;

    @SerializedName("bitcoin")
    private String bitcoin;

    @SerializedName("username")
    private String username;

    @SerializedName("ts")
    private String ts;

    public String getRippleDTag() {
        return rippleDTag;
    }

    public void setRippleDTag(String rippleDTag) {
        this.rippleDTag = rippleDTag;
    }

    public String getRefId() {
        return refId;
    }

    public String getCountry() {
        return country;
    }

    public String getTCode() {
        return tCode;
    }

    public String getMasterAccount() {
        return masterAccount;
    }

    public Object getDeviceType() {
        return deviceType;
    }

    public String getEthereum() {
        return ethereum;
    }

    public String getPassword() {
        return password;
    }

    public String getIdNo() {
        return idNo;
    }

    public String getEthereumc() {
        return ethereumc;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getIssued() {
        return issued;
    }

    public String getBinaryPos() {
        return binaryPos;
    }

    public String getAboutus() {
        return aboutus;
    }

    public String getImage() {
        return image;
    }

    public Object getDeviceId() {
        return deviceId;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public String getUserPlan() {
        return userPlan;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getAccName() {
        return accName;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getRipple() {
        return ripple;
    }

    public String getUserId() {
        return userId;
    }

    public String getDob() {
        return dob;
    }

    public String getDesignation() {
        return designation;
    }

    public String getEntertainmentId() {
        return entertainmentId;
    }

    public String getCity() {
        return city;
    }

    public String getMerriedStatus() {
        return merriedStatus;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getBankNm() {
        return bankNm;
    }

    public String getAdminRemark1() {
        return adminRemark1;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getPaymentProof() {
        return paymentProof;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public String getAddress() {
        return address;
    }

    public String getCurrentLoginDate() {
        return currentLoginDate;
    }

    public String getSex() {
        return sex;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNomId() {
        return nomId;
    }

    public String getUserRankName() {
        return userRankName;
    }

    public String getBranchNm() {
        return branchNm;
    }

    public String getProductType() {
        return productType;
    }

    public String getAcNo() {
        return acNo;
    }

    public String getBitcoin() {
        return bitcoin;
    }

    public String getUsername() {
        return username;
    }

    public String getTs() {
        return ts;
    }

    public void setEthereum(String ethereum) {
        this.ethereum = ethereum;
    }

    public void setEthereumc(String ethereumc) {
        this.ethereumc = ethereumc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public void setRipple(String ripple) {
        this.ripple = ripple;
    }

    public void setBankNm(String bankNm) {
        this.bankNm = bankNm;
    }

    public void setBranchNm(String branchNm) {
        this.branchNm = branchNm;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public void setBitcoin(String bitcoin) {
        this.bitcoin = bitcoin;
    }

    public void setUserRankName(String userRankName) {
        this.userRankName = userRankName;
    }
}