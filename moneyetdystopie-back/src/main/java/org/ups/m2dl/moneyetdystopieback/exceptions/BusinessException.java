package org.ups.m2dl.moneyetdystopieback.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessException extends Exception {
    public BusinessException(String s) {
        super(s);
    }
}