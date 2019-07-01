
package com.khoza.atm.model;

public enum HonoraryTitle
{
  MR("Mr."),
  MRS("Mrs."),
  MX("Mx."),
  MS("Ms."),
  MISS("Miss."),
  MASTER("Master"),
  MAID("Maid"),
  MADAM("Madam");

  private final String value;

  HonoraryTitle(String value)
  {
    this.value = value;
  }

  public String getValue()
  {
    return value;
  }
}
