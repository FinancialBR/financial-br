package br.com.lepsistemas.financial.domain.usecase;

import br.com.lepsistemas.financial.domain.model.Expense;
import br.com.lepsistemas.financial.domain.valueobject.Money;

import java.time.LocalDate;

public class Payment {

    public  void pay(Expense expense, LocalDate paymentDate, Money value) {
        expense.pay(paymentDate, value);
    }
}
