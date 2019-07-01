package com.khoza.atm.model;

import javax.persistence.*;

@Entity
@Table (name ="Currency" )
public class Currency {

    @Id
    @Column
    private String currencyCode;
    @Column
    private int decimalPlaces;
    @Column
    private int description;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }
}
