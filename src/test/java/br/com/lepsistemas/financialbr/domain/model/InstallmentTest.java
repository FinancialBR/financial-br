package br.com.lepsistemas.financialbr.domain.model;

import br.com.lepsistemas.financialbr.domain.exception.OverPaymentException;
import br.com.lepsistemas.financialbr.domain.exception.ZeroedInstallmentException;
import br.com.lepsistemas.financialbr.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InstallmentTest {

    public static final String AMOUNT = "100";
    public static final String AMOUNT_PARTIAL_PAYMENT = "50";
    private LocalDate dueDate;
    private Installment installment;
    LocalDateTime paymentDate;

    @BeforeEach
    void setup() {
        dueDate = LocalDate.now();
        installment = new Installment(Money.worth(AMOUNT), dueDate);
        paymentDate = LocalDateTime.now();
    }

    @Test
    void should_create_an_installment() {

        assertEquals(Money.worth(AMOUNT), installment.grossValue());
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

        installment.pay(Money.worth(AMOUNT), paymentDate);

        assertTrue(installment.isFullyPaid());
    }

    @Test
    void should_pay_partially() {

        installment.pay(Money.worth("99.99"), paymentDate);

        assertFalse(installment.isFullyPaid());
    }

    @Test
    void should_throw_exception_when_trying_to_pay_more_than_net_value() {

        assertThrows(OverPaymentException.class, () -> {
            LocalDateTime paymentDate = LocalDateTime.now();
            installment.pay(Money.worth("100.01"), paymentDate);
        });
    }


    @Test
    void should_pay_in_full_when_payment_in_two_or_more_steps() {

        installment.pay(Money.worth(AMOUNT_PARTIAL_PAYMENT), paymentDate);
        installment.pay(Money.worth(AMOUNT_PARTIAL_PAYMENT), paymentDate);

        assertTrue(installment.isFullyPaid());
    }

    @Test
    void should_throw_exception_when_trying_to_pay_more_than_net_value_in_two_or_more_steps() {

        installment.pay(Money.worth(AMOUNT_PARTIAL_PAYMENT), paymentDate);
        assertThrows(OverPaymentException.class, () -> {
            installment.pay(Money.worth("50.01"), paymentDate);
        });
    }

    @Test
    void should_return_null_when_not_exists_any_payment() {

        assertNull(installment.lastPayment());
    }

    @Test
    void should_return_last_date_when_exists_one_payment() {

        installment.pay(Money.worth(AMOUNT), paymentDate);
        assertEquals(paymentDate, installment.lastPayment());
    }

    @Test
    void should_return_last_date_when_exists_two_or_more_payments() {

        LocalDateTime oldestDate = LocalDateTime.of(2019, 1,1, 12, 00);
        LocalDateTime newestDate = LocalDateTime.of(2019, 1,1, 12, 00);
        installment.pay(Money.worth(AMOUNT_PARTIAL_PAYMENT), newestDate);
        installment.pay(Money.worth(AMOUNT_PARTIAL_PAYMENT), oldestDate);
        assertEquals(newestDate, installment.lastPayment());
    }
}