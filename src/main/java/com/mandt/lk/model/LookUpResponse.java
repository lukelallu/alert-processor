package com.mandt.lk.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;
import java.io.IOException;

@ApiModel(description="All details about the student. ")

public class LookUpResponse {



    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @ApiModelProperty(notes="accountNo could have 15 characters")
    @Size(min=15, message="accountNo could have 15 characters")
    private  String accountNo;

    public Entitlement getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(Entitlement entitlements) {
        this.entitlements = entitlements;
    }

    @ApiModelProperty(notes="eligibilityFlag")
    @Size(min=1, message=" Value could be any of the following" +
            " NA(\"NA\"),\n" +
            "    SUMMARY_ONLY(\"SUMMARY_ONLY\"),\n" +
            "    SUMMARY_DETAILS(\"SUMMARY_DETAILS\"),\n" +
            "    EXPANDED(\"EXPANDED\"),\n" +
            "    EXPANDED_REMIT(\"EXPANDED_REMIT\")")
    private  Entitlement entitlements;

    /**
     *
     * @param id
     * @throws IOException
     */
    public LookUpResponse(String id,Entitlement entitlement) {
        this.accountNo = id;
        //How many records....
        this.entitlements = entitlement;
    }



    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "LookUpResponse{" +
                "account='" + accountNo + '\'' +
                ", flag='" + entitlements.toString() + '\'' +
                '}';
    }
}