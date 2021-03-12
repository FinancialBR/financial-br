package br.com.lepsistemas.financial.domain.exception;

public class MoneyCannotWorthNullValueException extends RuntimeException {

    public MoneyCannotWorthNullValueException() {
        super("Money cannot worth null value.");
    }
}
