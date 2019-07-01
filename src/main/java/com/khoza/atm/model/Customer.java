
package com.khoza.atm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customers")
public class Customer
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @Enumerated(EnumType.STRING)
  private HonoraryTitle titleOfHonour;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  private String identityNumber;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Account> listOfAccounts = new ArrayList<>();

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public String getIdentityNumber()
  {
    return identityNumber;
  }

  public void setIdentityNumber(String identityNumber)
  {
    this.identityNumber = identityNumber;
  }

  public List<Account> getListOfAccounts()
  {
    return listOfAccounts;
  }

  public void setListOfAccounts(List<Account> listOfAccounts)
  {
    this.listOfAccounts = listOfAccounts;
  }

  public HonoraryTitle getTitleOfHonour()
  {
    return titleOfHonour;
  }

  public void setTitleOfHonour(HonoraryTitle titleOfHonour)
  {
    this.titleOfHonour = titleOfHonour;
  }

  public String getFormalName() {
    return getTitleOfHonour().getValue() +" "+ getFirstName() + " "+ getLastName();
  }
  @Override
  public String toString()
  {
    return "Customer{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", identityNumber='" + identityNumber + '\'' +
        '}';
  }

  public Account getAccountWithHighestBalance()
  {
    listOfAccounts.sort((acc1, acc2) -> (int) (acc2.getBalance() - acc1.getBalance()));
    return listOfAccounts.stream().findFirst().orElse(null);
  }
}
