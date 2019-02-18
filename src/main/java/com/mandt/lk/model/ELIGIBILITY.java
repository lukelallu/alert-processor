package com.mandt.lk.model;

public enum ELIGIBILITY {

    NA("NA"),
    SUMMARY_ONLY("SUMMARY_ONLY"),//BT DOES NOT SUPPORT SUMMARY TYPE CODES, IS IT NEEDED ? WAITING FOR GLENN
    STANDARD_DETAILS("STANDARD_DETAILS"),//standard details
    EXPANDED("EXPANDED"),
    EXPANDED_REMIT("EXPANDED_REMIT")
    ;

    private String value;
    private ELIGIBILITY(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }



}
