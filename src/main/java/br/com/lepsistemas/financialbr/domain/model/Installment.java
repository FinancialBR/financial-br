package br.com.lepsistemas.financialbr.domain.model;

import br.com.lepsistemas.financialbr.domain.exception.OverPaymentException;
import br.com.lepsistemas.financialbr.domain.exception.ZeroedInstallmentException;
import br.com.lepsistemas.financialbr.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Installment {

    private final Money grossValue;
    private final LocalDate dueDate;

    List<Payment> payments;

    public Installment(Money grossValue, LocalDate dueDate) {
        if (grossValue.isZero()) {
            throw new ZeroedInstallmentException();
        }
        this.grossValue = grossValue;
        this.dueDate = dueDate;
        this.payments = new ArrayList<>();
    }

    public void pay(Money value, LocalDateTime paymentDate) {
        if (value.compareTo(this.netValue()) > 0) {
            throw new OverPaymentException();
        }
        payments.add(new Payment(value, paymentDate));

    }

    public boolean isFullyPaid() {
        return this.netValue().compareTo(Money.worth(BigDecimal.ZERO)) == 0;
    }

    public Money grossValue() {
        return this.grossValue;
    }

    public Money netValue() {
        return this.grossValue
                .minus(payments
                        .stream()
                        .map(payment -> payment.getPaidValue())
                        .reduce(Money.worth(BigDecimal.ZERO), Money::plus));
    }

    public LocalDate dueDate() {
        return this.dueDate;
    }

    public LocalDateTime lastPayment() {
        return payments
                .stream()
                .map(payment -> payment.getPaymentDate())
                .max((date1, date2) -> date1.compareTo(date2)).orElse(null);
    }
}
