package com.mandt.lk.model;

public class Entitlement {
    public String getWireReporting() {
        return wireReporting;
    }

    public Entitlement(String wireReporting) {
        this.wireReporting = wireReporting;
    }

    public void setWireReporting(String wireReporting) {
        this.wireReporting = wireReporting;
    }

    private String wireReporting;

    @Override
    public String toString() {
        return "Entitlement{" +
                "wireReporting='" + wireReporting + '\'' +
                '}';
    }
}
