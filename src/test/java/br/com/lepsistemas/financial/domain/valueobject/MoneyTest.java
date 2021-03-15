package br.com.lepsistemas.financial.domain.valueobject;

import br.com.lepsistemas.financial.domain.exception.MoneyCannotWorthNegativeValueException;
import br.com.lepsistemas.financial.domain.exception.MoneyCannotWorthNullValueException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyTest {

    @Test
    void should_throw_exception_when_value_is_null() {
        assertThrows(MoneyCannotWorthNullValueException.class, () -> Money.worth((BigDecimal) null));
    }

    @Test
    void should_throw_exception_if_value_is_negative() {
        assertThrows(MoneyCannotWorthNegativeValueException.class, () -> Money.worth(new BigDecimal(-0.01)));
    }

    @Test
    void should_create_money_from_string() {
        Money money = Money.worth("0.01");
        assertEquals(money.value(), new BigDecimal("0.01"));
    }

    @Test
    void should_create_money_from_big_decimal() {
        Money money = Money.worth(new BigDecimal("0.01"));
        assertEquals(money.value(), new BigDecimal("0.01"));
    }

    @Test
    void should_be_equal_both_created_with_string_and_big_decimal() {
        assertEquals(Money.worth("1"), Money.worth(new BigDecimal("1")));
    }

    @Test
    void should_compare_values() {
        Money money1 = Money.worth("1");
        Money money2 = Money.worth("2");
        Money money3 = Money.worth("2");

        assertTrue(money1.compareTo(money2) < 0);
        assertTrue(money2.compareTo(money1) > 0);
        assertTrue(money2.compareTo(money3) == 0);
    }

    @Test
    void should_sum_money() {
        Money money1 = Money.worth("100");
        Money money2 = Money.worth("100");

        assertEquals(money1.plus(money2), Money.worth("200"));
    }

}