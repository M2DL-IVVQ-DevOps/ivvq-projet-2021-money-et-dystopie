package org.ups.m2dl.moneyetdystopieback.exceptions;

public class BusinessMessage {

    private String errorMessage;

    public BusinessMessage(BusinessException businessexception) {
        this.errorMessage = businessexception.getMessage();
    }

    public BusinessMessage() {}

    public String getErrorMessage() {
        return errorMessage;
    }
}