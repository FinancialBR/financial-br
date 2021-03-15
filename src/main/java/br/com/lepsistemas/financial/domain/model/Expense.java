package br.com.lepsistemas.financial.domain.model;

import br.com.lepsistemas.financial.domain.valueobject.Money;

import java.time.LocalDate;

public class Expense {

    private final Money grossValue;
    private final LocalDate emissionDate;
    private final LocalDate dueDate;

    private Money paidValue;
    private LocalDate paymentDate;

    public Expense(Money grossValue, LocalDate emissionDate, LocalDate dueDate) {
        this.grossValue = grossValue;
        this.emissionDate = emissionDate;
        this.dueDate = dueDate;
    }

    public void pay(LocalDate paymentDate, Money value) {
        this.paymentDate = paymentDate;
        this.paidValue = value;
    }

    public boolean isFullyPaid() {
        return this.paymentDate != null && this.grossValue.compareTo(this.paidValue) == 0;
    }

    public Money remainingValue() {
        return this.grossValue.minus(this.paidValue);
    }

    public Money grossValue() { return this.grossValue; }

    public LocalDate emissionDate() {
        return this.emissionDate;
    }

    public LocalDate dueDate() {
        return this.dueDate;
    }
}
