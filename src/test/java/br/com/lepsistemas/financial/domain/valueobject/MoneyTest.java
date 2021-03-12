package br.com.lepsistemas.financial.domain.valueobject;

import br.com.lepsistemas.financial.domain.exception.MoneyCannotWorthNegativeValueException;
import br.com.lepsistemas.financial.domain.exception.MoneyCannotWorthNullValueException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    @Test
    void should_throw_exception_when_value_is_null() {
        assertThrows(MoneyCannotWorthNullValueException.class, () -> Money.worth((BigDecimal) null), "Money cannot worth null value.");
    }

    @Test
    void should_throw_exception_if_value_is_negative() {
        assertThrows(MoneyCannotWorthNegativeValueException.class, () -> Money.worth(new BigDecimal(-0.01)), "Money cannot worth negative value.");
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

}