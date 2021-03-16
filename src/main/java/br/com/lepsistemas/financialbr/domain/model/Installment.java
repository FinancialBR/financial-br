package br.com.lepsistemas.financialbr.domain.model;

import br.com.lepsistemas.financialbr.domain.exception.OverPaymentException;
import br.com.lepsistemas.financialbr.domain.exception.ZeroedInstallmentException;
import br.com.lepsistemas.financialbr.domain.valueobject.Money;

import java.time.LocalDate;

public class Installment {

    private final Money grossValue;
    private final LocalDate dueDate;

    private Money paidValue;
    private LocalDate paymentDate;

    public Installment(Money grossValue, LocalDate dueDate) {
        if (grossValue.isZero()) {
            throw new ZeroedInstallmentException();
        }
        this.grossValue = grossValue;
        this.dueDate = dueDate;
    }

    public void pay(Money value, LocalDate paymentDate) {
        if (value.compareTo(this.netValue()) > 0) {
            throw new OverPaymentException();
        }
        this.paidValue = value;
        this.paymentDate = paymentDate;
    }

    public boolean isFullyPaid() {
        return this.netValue().compareTo(this.paidValue) == 0;
    }

    public Money grossValue() {
        return this.grossValue;
    }

    public Money netValue() {
        return this.grossValue;
    }

    public LocalDate dueDate() {
        return this.dueDate;
    }

    public LocalDate paymentDate() {
        return this.paymentDate;
    }
}
