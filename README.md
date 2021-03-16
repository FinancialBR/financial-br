# FinancialBR: Java API to work with brazilian financial systems

[![Build Status](https://travis-ci.com/FinancialBR/financial-br.svg?branch=master)](https://travis-ci.com/FinancialBR/financial-br) [![codecov](https://codecov.io/gh/FinancialBR/financial-br/branch/master/graph/badge.svg?token=W2ssUGSoY1)](https://codecov.io/gh/FinancialBR/financial-br)

FinancialBR is a set of core financial systems entities that allow you work with the most common brazilian operations.

### Adding FinancialBR to your project

FinancialBR's Maven groupId **WILL BE** `br.com.lepsistemas`, and its artifactId **WILL BE** `financial-br`. *

To add a dependency on FinancialBR using Maven, use the following:

```xml
<dependency>
  <groupId>br.com.lepsistemas</groupId>
  <artifactId>financial-br</artifactId>
  <version>RELEASE</version>
</dependency>
```

To add a dependency using Gradle:

```gradle
dependencies {
  implementation("br.com.lepsistemas:financial-br:RELEASE")
}
```

### Using FinancialBR in your project

As we learned from the Design Patterns and the Open-Closed Principle from SOLID, we should almost always chose composition over inheritance. Well, it's not different of how I suggest you to use this lib:

```java
import br.com.lepsistemas.financial.domain.model.Expense;

public class YourExpense {
    
    private Expense expense;
    private YourSupplier supplier;
    ...
    
    public YourExpense(Expense expense, YourSupplier supplier) {
        this.expense = expense;
        this.supplier = supplier;
    }
    
    public BigDecimal getGrossValue() {
        return this.expense.grossValue().value();
    }
    
    public LocalDate getDueDate() {
        return this.expense.dueDate();
    }
    
    ...
}
```

Of course that you can use class Money as well or any other Currency value object that you'd like to. BigDecimal was used to simplify and to show how easy it is to adapt FinancialBR objects to your own domain.

---

* Edit: This is still a Working In Progress API. Artifacts are not yet deployed into Maven Repository.