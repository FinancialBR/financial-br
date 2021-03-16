package br.com.lepsistemas.financial.domain.model;

import br.com.lepsistemas.financial.domain.valueobject.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpenseTest {

    @Test
    void should_create_an_expense() {
        LocalDate emissionDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().plusDays(1L);
        Expense expense = new Expense(Money.worth("100"), emissionDate, dueDate);

        assertEquals(Money.worth("100"), expense.grossValue());
        assertEquals(emissionDate, expense.emissionDate());
        assertEquals(dueDate, expense.dueDate());
    }

    @Test
    void should_be_fully_paid() {
        LocalDate emissionDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().plusDays(1L);
        Expense expense = new Expense(Money.worth("100"), emissionDate, dueDate);

        LocalDate paymentDate = LocalDate.now().plusWeeks(1L);
        expense.pay(paymentDate, Money.worth("100"));

        assertTrue(expense.isFullyPaid());
    }

    @Test
    void should_be_partially_paid() {
        LocalDate emissionDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().plusDays(1L);
        Expense expense = new Expense(Money.worth("100"), emissionDate, dueDate);

        LocalDate paymentDate = LocalDate.now().plusWeeks(1L);
        expense.pay(paymentDate, Money.worth("99.99"));

        assertFalse(expense.isFullyPaid());
    }

}