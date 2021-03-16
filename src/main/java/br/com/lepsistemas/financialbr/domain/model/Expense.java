package br.com.lepsistemas.financialbr.domain.model;

import br.com.lepsistemas.financialbr.domain.exception.AbsentInstallmentException;
import br.com.lepsistemas.financialbr.domain.valueobject.Money;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Expense {

    private final LocalDate emissionDate;
    private final List<Installment> installments;

    public Expense(Money grossValue, LocalDate emissionDate, LocalDate dueDate) {
        this.emissionDate = emissionDate;
        this.installments = new ArrayList<>();
        this.addInstallment(grossValue, dueDate);
    }

    public void pay(Money value, LocalDate paymentDate, LocalDate dueDate) {
        Optional<Installment> optionalOfInstallment = this.installments.stream()
                .filter(installment -> installment.dueDate().equals(dueDate))
                .findFirst();
        if (!optionalOfInstallment.isPresent()) {
            throw new AbsentInstallmentException();
        }
        Installment installment = optionalOfInstallment.get();
        installment.pay(value, paymentDate);
    }

    public boolean isFullyPaid() {
        return this.installments.stream()
                .allMatch(Installment::isFullyPaid);
    }

    public void addInstallment(Money grossValue, LocalDate dueDate) {
        this.installments.add(new Installment(grossValue, dueDate));
    }

    public Money grossValue() {
        return this.installments.stream()
                .map(installment -> installment.grossValue())
                .reduce(Money.worth(BigDecimal.ZERO), Money::plus);
    }

    public LocalDate emissionDate() {
        return this.emissionDate;
    }

    public LocalDate dueDate() {
        return this.installments.stream()
                .sorted(Comparator.comparing(Installment::dueDate).reversed())
                .findFirst().get().dueDate();
    }
}
