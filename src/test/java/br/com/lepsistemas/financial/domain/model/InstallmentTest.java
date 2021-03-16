package br.com.lepsistemas.financial.domain.model;

import br.com.lepsistemas.financial.domain.exception.OverPaymentException;
import br.com.lepsistemas.financial.domain.exception.ZeroedInstallmentException;
import br.com.lepsistemas.financial.domain.valueobject.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InstallmentTest {

    @Test
    void should_create_an_installment() {
        LocalDate dueDate = LocalDate.now();
        Installment installment = new Installment(Money.worth("100"), dueDate);

        assertEquals(Money.worth("100"), installment.grossValue());
        assertEquals(dueDate, installment.dueDate());
    }

    @Test
    void should_throw_exception_when_trying_to_create_a_zeroed_installment() {
        assertThrows(ZeroedInstallmentException.class, () -> {
            new Installment(Money.worth(BigDecimal.ZERO), LocalDate.now());
        });
    }

    @Test
    void should_pay_in_full() {
        LocalDate dueDate = LocalDate.now();
        Installment installment = new Installment(Money.worth("100"), dueDate);

        LocalDate paymentDate = LocalDate.now();
        installment.pay(Money.worth("100"), paymentDate);

        assertTrue(installment.isFullyPaid());
        assertEquals(paymentDate, installment.paymentDate());
    }

    @Test
    void should_pay_partially() {
        LocalDate dueDate = LocalDate.now();
        Installment installment = new Installment(Money.worth("100"), dueDate);

        LocalDate paymentDate = LocalDate.now();
        installment.pay(Money.worth("99.99"), paymentDate);

        assertFalse(installment.isFullyPaid());
        assertEquals(paymentDate, installment.paymentDate());
    }

    @Test
    void should_throw_exception_when_trying_to_pay_more_than_net_value() {
        LocalDate dueDate = LocalDate.now();
        Installment installment = new Installment(Money.worth("100"), dueDate);

        assertThrows(OverPaymentException.class, () -> {
            LocalDate paymentDate = LocalDate.now();
            installment.pay(Money.worth("100.01"), paymentDate);
        });
    }

}