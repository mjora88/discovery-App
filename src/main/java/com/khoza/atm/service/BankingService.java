package com.khoza.atm.service;

import com.khoza.atm.model.Account;
import com.khoza.atm.model.Customer;
import com.khoza.atm.model.FinancialPositionReport;
import com.khoza.atm.repository.AccountRepository;
import com.khoza.atm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankingService
{
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private AccountRepository accountRepository;

  private static List<Integer> nodesInDescendingOrder = new ArrayList<>();

  static
  {
    nodesInDescendingOrder.add(200);
    nodesInDescendingOrder.add(100);
    nodesInDescendingOrder.add(50);
    nodesInDescendingOrder.add(20);
    nodesInDescendingOrder.add(10);
  }

  @Transactional
  public List<String> withdraw(Account selectedAccount, long amountLeftToWithdraw)
  {
    List<String> listOfNotesToReturn = new ArrayList<>();
    double totalWithdrawn = 0;

    if (amountLeftToWithdraw <= selectedAccount.getBalance())
    {
      for (Integer note : nodesInDescendingOrder)
      {
        int numberOfNodes = 0;
        while (amountLeftToWithdraw >= note)
        {
          totalWithdrawn += note;
          amountLeftToWithdraw -= note;
          numberOfNodes++;
        }
        if (numberOfNodes > 0)
        {
          listOfNotesToReturn.add(numberOfNodes + " X R" + note);
        }
      }
      selectedAccount.setBalance(selectedAccount.getBalance() - totalWithdrawn);
      accountRepository.save(selectedAccount);

      listOfNotesToReturn.add("Total Amount Withdrawn:\t R" + String.format("%.2f", totalWithdrawn));
      listOfNotesToReturn.add("Remaining Balance:\t R" + String.format("%.2f", selectedAccount.getBalance()));
    }

    return listOfNotesToReturn;
  }

  public List<FinancialPositionReport> findAllAgregateFinancialPositions()
  {
    List<FinancialPositionReport> financialPositionReports = new ArrayList<>();

    for (Customer customer : customerRepository.findAll())
    {
      FinancialPositionReport report = new FinancialPositionReport();
      report.setCustmerId(customer.getId());
      report.setFormalName(customer.getFormalName());

      if (!customer.getListOfAccounts().isEmpty())
      {
        report.setLoanBalance(customer.getListOfAccounts()
            .stream()
            .filter(account -> account.getAccountType() == Account.AccountType.BOND_ACCOUNT || account.getAccountType() == Account.AccountType.CREDIT_CARD)
            .mapToDouble(Account::getBalance).sum()
        );
        report.setTransactionalBalance(customer.getListOfAccounts()
            .stream()
            .filter(account -> account.getAccountType() == Account.AccountType.SAVINGS_ACCOUNT || account.getAccountType() == Account.AccountType.CHEQUE_ACCOUNT)
            .mapToDouble(Account::getBalance).sum()
        );

        report.setNetPosition(customer.getListOfAccounts()
            .stream()
            .mapToDouble(Account::getBalance).sum()
        );

        financialPositionReports.add(report);
      }
    }

    return financialPositionReports;
  }

}
