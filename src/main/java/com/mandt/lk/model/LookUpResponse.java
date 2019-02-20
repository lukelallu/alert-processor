package com.mandt.lk.model;

import com.mandt.lk.util.Utility;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

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
    public LookUpResponse(Utility utility,String id) throws IOException {
        this.accountNo = id;
        //How many records....
        this.entitlements = new Entitlement ( getEligibility(utility, accountNo) );
    }

    /**
     *
     * @param accountNo
     * @return
     * @throws IOException
     */
    private String getEligibility(Utility utility, String accountNo) throws IOException {

        String eligibility;
        switch (utility.getEligibilityFromMiniFile(accountNo)) {
            case "Y":
                eligibility = ELIGIBILITY.EXPANDED.getValue();
                break;
            case "R":
                eligibility = ELIGIBILITY.EXPANDED_REMIT.getValue();
                break;
            case "N":
                eligibility = ELIGIBILITY.STANDARD_DETAILS.getValue();
                break;
            case " ":
                eligibility = ELIGIBILITY.SUMMARY_ONLY.getValue();
                break;
            default:
                eligibility = ELIGIBILITY.NA.getValue();
                break;
        }
        return eligibility;
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