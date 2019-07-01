
package com.khoza.atm.service;

import com.khoza.atm.model.Account;
import com.khoza.atm.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

@Service
public class BankingAutomatedTellerMachineService
{
  @Autowired
  private MessageService messageService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private BankingService bankingService;

  @Autowired
  private CustomerService customerService;

  public void process() throws Exception
  {
    System.out.println(messageService.getWeclomeMessage());
    listAllCustomers(accountService.getAllClients());
    Scanner input = new Scanner(System.in);

    System.out.println("Make your selection [Press 0 at any point to go back to main menu]:\n");
    long customerID = input.nextLong();

    if (customerID == 0)
    {
      process();
    }

    Customer selectedCustomer = customerService.getCustomerById(customerID);
    System.out.println(messageService.getDisplayAccountsPrompt());
    System.out.println(messageService.getDisplayCurrencyConvertedAccountValuesPrompt());
    System.out.println(messageService.getCashWithdrawalPrompt());
    System.out.println(messageService.getFindAccountsPerClientWithHighestBalance());
    System.out.println(messageService.getFindFinancialPositionPerClientWithHighestBalance());

    System.out.println("Make your selection [Press 0 at any point to go back to main menu]:\n");
    int action = input.nextInt();

    if (action == 0)
    {
      process();
    }
    switch (action)
    {
      case 1:
        listAccounts(selectedCustomer.getListOfAccounts());
        System.out.println("\nSelect 0 to return to main menu");
        input.nextInt();
        process();

        break;
      case 2:
        listAccountsCurrency(selectedCustomer.getListOfAccounts());
        System.out.println("\nSelect 0 to return to main menu");
        input.nextInt();
        process();
        break;
      case 3:
        withdrawCash(selectedCustomer.getListOfAccounts(), input);
        break;
      case 4:
        findAccountsPerClientWithHighestBalance();
        System.out.println("\nSelect 0 to return to main menu");
        input.nextInt();
        process();
        break;
      case 5:
        viewFinancialPositionsForAllClients();
        System.out.println("\nSelect 0 to return to main menu");
        input.nextInt();
        process();
        break;
      default:
        System.err.println("Wrong Selection Made. Returning to main menu");
        process();
        break;
    }

  }

  private void viewFinancialPositionsForAllClients()
  {
    System.out.println("Financial Positions for all clients:\n");
    bankingService.findAllAgregateFinancialPositions().forEach(System.out::println);
  }

  private void findAccountsPerClientWithHighestBalance()
  {
    for (Customer client : accountService.getAllClients())
    {
      Account account = client.getAccountWithHighestBalance();
      if (account != null)
      {
        System.out.println(client.getFormalName() + "\t " + account.getAccountNumber() + "\t" + account.getAccountType() + "\tR" + String.format("%.2f", account.getBalance()));
      }
      else
      {
        System.err.println("No qualifying accounts for " + client.getFormalName());
      }
    }
  }

  private void withdrawCash(List<Account> accounts, Scanner input) throws Exception
  {
    if (accounts != null && !accounts.isEmpty())
    {
      listAccounts(accounts);
      System.out.println("Choose the account you want to withdraw from [0 to go back to main menu]:\n");
      long selectedAccountId = input.nextLong();

      if (selectedAccountId == 0)
      {
        process();
      }
      Account selectedAccount = accountService.findById(selectedAccountId);

      if (selectedAccount != null)
      {
        if (selectedAccount.getBalance() <= 0)
        {
          System.err.println("Your selected account is overdrawn. Please selected a different account.");
          withdrawCash(accounts, input);
        }
        double balance = selectedAccount.getBalance();
        System.out.println("Please enter Amount to withdraw (Max: " + String.format("%.2f", balance) + "):");
        System.out.println("[Process 0 to go back to the main menu]\n");
        long amountInput = input.nextLong();

        if (amountInput == 0)
        {
          process();
        }

        if (amountInput > balance)
        {
          System.err.println("Insufficient Balance. Try a different amount or different account");
          withdrawCash(accounts, input);
        }
        List<String> response = bankingService.withdraw(selectedAccount, amountInput);
        if (response == null || response.isEmpty())
        {
          System.err.println("Failed to process withdrawal. Please try again");
          withdrawCash(accounts, input);
        }
        response.forEach(System.out::println);
        exit(0);
      }
      else
      {
        System.err.println("Bad Selection. Please try again!");
        withdrawCash(accounts, input);
      }

    }
    else
    {
      System.err.println("You do not have any qualifying accounts for withdrawal!\nLogging off");
      exit(0);
    }
  }

  private void listAccountsCurrency(List<Account> accounts)
  {
    if (accounts != null && !accounts.isEmpty())
    {
      Currency primatyCurrency = Currency.getInstance("ZAR");
      Currency toCurrency = Currency.getInstance("USD");
      System.out.println("List Of Accounts\n");

      System.out.println("#\t\t\tAccount Number\t\t\t" + primatyCurrency.getCurrencyCode() + "\t\t\t" + toCurrency.getCurrencyCode() + "\t\t\tRate\n");
      accounts.sort((acc1, acc2) -> (int) (acc2.getBalance() - acc1.getBalance()));
      accounts.forEach(account -> System.out.println("#: " + account.getId() + ".\tAcc# :" + account.getAccountNumber() + "\tBalance: R" + String.format("%.2f", account.getBalance()) + "\t" + toCurrency.getSymbol() + " " + String.format("%.2f", account.getBalance(toCurrency)) + "\t" + account.getRate(toCurrency)));
    }
    else
    {
      System.err.println("You do not have any qualifying accounts!\nLogging off");
      exit(0);
    }
  }

  private void listAccounts(List<Account> accounts)
  {
    if (accounts != null && !accounts.isEmpty())
    {
      System.out.println("List Of Accounts");
      accounts.sort((acc1, acc2) -> (int) (acc2.getBalance() - acc1.getBalance()));
      accounts.forEach(account -> System.out.println("#: " + account.getId() + ". Acc# :" + account.getAccountNumber() + "\tBalance: R" + String.format("%.2f", account.getBalance())));
    }
    else
    {
      System.err.println("You do not have any qualifying accounts!\nLogging off");
      exit(0);
    }
  }

  private void listAllCustomers(List<Customer> allClients)
  {
    System.out.println("Select Customer by ID: ");
    allClients.forEach(customer -> System.out.println("ID = " + customer.getId() + ". " + customer.getFormalName()));
  }
}
