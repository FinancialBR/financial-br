package br.com.lepsistemas.financialbr.domain.model;

import br.com.lepsistemas.financialbr.domain.valueobject.Money;

import java.time.LocalDateTime;

public class Payment {

    private Money paidValue;
    private LocalDateTime paymentDate;

    public Payment(Money paidValue, LocalDateTime paymentDate) {
        this.paidValue = paidValue;
        this.paymentDate = paymentDate;
    }

    public Money getPaidValue() {
        return paidValue;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }
}
