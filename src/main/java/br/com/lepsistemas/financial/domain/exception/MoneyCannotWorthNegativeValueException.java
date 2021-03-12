package br.com.lepsistemas.financial.domain.exception;

public class MoneyCannotWorthNegativeValueException extends RuntimeException {

    public MoneyCannotWorthNegativeValueException() {
        super("Money cannot worth negative value.");
    }
}
