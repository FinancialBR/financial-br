package br.com.lepsistemas.financialbr.domain.model;

import br.com.lepsistemas.financialbr.domain.exception.AbsentInstallmentException;
import br.com.lepsistemas.financialbr.domain.valueobject.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpenseTest {

    @Test
    void should_create_a_lump_sum_expense() {
        LocalDate emissionDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().plusDays(1L);
        Expense expense = new Expense(Money.worth("100"), emissionDate, dueDate);

        assertEquals(Money.worth("100"), expense.grossValue());
        assertEquals(emissionDate, expense.emissionDate());
        assertEquals(dueDate, expense.dueDate());
        assertTrue(expense.isLumpSum());
    }

    @Test
    void should_create_an_expense_with_multiple_installments() {
        LocalDate emissionDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().plusDays(1L);
        Expense expense = new Expense(Money.worth("100"), emissionDate, dueDate);
        LocalDate installmentDueDate = LocalDate.now().plusMonths(1L);
        expense.addInstallment(Money.worth("50"), installmentDueDate);

        assertEquals(expense.dueDate(), installmentDueDate);
        assertFalse(expense.isLumpSum());
    }

    @Test
    void should_be_fully_paid() {
        LocalDate emissionDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().plusDays(1L);
        Expense expense = new Expense(Money.worth("100"), emissionDate, dueDate);

        LocalDate paymentDate = LocalDate.now().plusWeeks(1L);
        expense.pay(Money.worth("100"), paymentDate, dueDate);

        assertTrue(expense.isFullyPaid());
    }

    @Test
    void should_be_partially_paid() {
        LocalDate emissionDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().plusDays(1L);
        Expense expense = new Expense(Money.worth("100"), emissionDate, dueDate);

        LocalDate paymentDate = LocalDate.now().plusWeeks(1L);
        expense.pay(Money.worth("99.99"), paymentDate, dueDate);

        assertFalse(expense.isFullyPaid());
    }

    @Test
    void should_throw_exception_when_trying_to_pay_absent_installment() {
        LocalDate emissionDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().plusDays(1L);
        Expense expense = new Expense(Money.worth("100"), emissionDate, dueDate);

        assertThrows(AbsentInstallmentException.class, () -> {
            LocalDate paymentDate = LocalDate.now().plusWeeks(1L);
            expense.pay(Money.worth("99.99"), paymentDate, dueDate.plusDays(1L));
        });
    }

}