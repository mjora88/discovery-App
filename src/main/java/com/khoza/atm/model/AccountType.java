package com.khoza.atm.model;

import javax.persistence.*;

@Entity
@Table (name ="Account_type" )
    public class AccountType {

    @Id
    @Column
    private String accountTypeCode;
    @Column
    private String desc;
    @Column
    private Boolean transactional;

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getTransactional() {
        return transactional;
    }

    public void setTransactional(Boolean transactional) {
        this.transactional = transactional;
    }
}
