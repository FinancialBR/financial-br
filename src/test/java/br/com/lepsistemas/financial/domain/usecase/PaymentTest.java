package br.com.lepsistemas.financial.domain.usecase;

import br.com.lepsistemas.financial.domain.model.Expense;
import br.com.lepsistemas.financial.domain.valueobject.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class PaymentTest {

    @Test
    void should_pay_expense_in_full() {
        LocalDate emissionDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().plusMonths(1L);
        Expense expense = new Expense(Money.worth("100"), emissionDate, dueDate);

        Payment payment = new Payment();
        LocalDate paymentDate = LocalDate.now().plusWeeks(1L);
        payment.pay(expense, paymentDate, Money.worth("100"));

        Assertions.assertTrue(expense.isFullyPaid());
    }

    @Test
    void should_pay_expense_partially() {
        LocalDate emissionDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().plusMonths(1L);
        Expense expense = new Expense(Money.worth("100"), emissionDate, dueDate);

        Payment payment = new Payment();
        LocalDate paymentDate = LocalDate.now().plusWeeks(1L);
        payment.pay(expense, paymentDate, Money.worth("70"));

        Assertions.assertFalse(expense.isFullyPaid());
        Assertions.assertEquals(Money.worth("30"), expense.remainingValue());
    }

}