package br.com.lepsistemas.financialbr.domain.valueobject;

import br.com.lepsistemas.financialbr.domain.exception.MoneyCannotWorthNegativeValueException;
import br.com.lepsistemas.financialbr.domain.exception.MoneyCannotWorthNullValueException;

import java.math.BigDecimal;
import java.util.Objects;

public class Money implements Comparable<Money> {

    private BigDecimal value;

    private Money(BigDecimal value) {
        if (value == null) {
            throw new MoneyCannotWorthNullValueException();
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new MoneyCannotWorthNegativeValueException();
        }
        this.value = value;
    }

    public static Money worth(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money worth(String amount) {
        return new Money(new BigDecimal(amount));
    }

    public BigDecimal value() {
        return this.value;
    }

    public Money plus(Money money) {
        return Money.worth(this.value.add(money.value));
    }

    public boolean isZero() {
        return BigDecimal.ZERO.equals(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value.equals(money.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(Money other) {
        return this.value.compareTo(other.value());
    }

    @Override
    public String toString() {
        return "Money{value=" + value + "}";
    }
}
