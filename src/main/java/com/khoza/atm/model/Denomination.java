package com.khoza.atm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Denomination")
public class Denomination {

    @Id
    @Column
    private int denominationId;
    @Column
    private double value;
    @Column
    private String denominationCode;


    public int getDenominationId() {
        return denominationId;
    }

    public void setDenominationId(int denominationId) {
        this.denominationId = denominationId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDenominationCode() {
        return denominationCode;
    }

    public void setDenominationCode(String denominationCode) {
        this.denominationCode = denominationCode;
    }
}
