package com.khoza.atm.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "Accounts")
public class Account
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private double balance;

  @Column
  private String accountNumber;

  @Column
  @Enumerated(EnumType.STRING)
  private AccountType accountType = AccountType.SAVINGS_ACCOUNT;

  public Long getId()
  {
    return id;
  }

  @Transient
  private static Map<String, Double> listOfCurrencyConvertionsToZAR = new HashMap<>();

  static
  {
    listOfCurrencyConvertionsToZAR.put("USD", 13.50);
    listOfCurrencyConvertionsToZAR.put("EURO", 21.86);
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public double getBalance()
  {
    return balance;
  }

  public void setBalance(double balance)
  {
    this.balance = balance;
  }

  public String getAccountNumber()
  {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber)
  {
    this.accountNumber = accountNumber;
  }

  public AccountType getAccountType()
  {
    return accountType;
  }

  public void setAccountType(AccountType accountType)
  {
    this.accountType = accountType;
  }

  public double getRate(Currency toCurrency)
  {
    double rate = listOfCurrencyConvertionsToZAR.get(toCurrency.getCurrencyCode());
    if (rate > 0)
    {
      return rate;
    }
    return 1;
  }

  public double getBalance(Currency toCurrency)
  {
    double rate = listOfCurrencyConvertionsToZAR.get(toCurrency.getCurrencyCode());
    if (rate > 0)
    {
      return this.getBalance() * rate;
    }

    return this.getBalance();
  }

  public enum AccountType {
    CHEQUE_ACCOUNT("Cheque Account"),
    SAVINGS_ACCOUNT("Savings Account"),
    BOND_ACCOUNT("Bond Account"),
    CREDIT_CARD("Credit Card"),
    ;

    private final String name;

    AccountType(String name)
    {
      this.name = name;
    }
  }
}
