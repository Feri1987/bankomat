/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.abstractClasses;

import bankomat.enumerations.CurrencyEnumeration;
import java.util.Date;

/**
 *
 * @author Denis
 */
public abstract class Card {

    private String number;                  //номер карты
    private String pinCode;                 //Пин код карты
    private Date date;                      //Срок действия карты
    private String cvv;                     //СВВ
    private double balance;                 //Баланс
    private CurrencyEnumeration currency;   //Валюта

    abstract public boolean checkNumber(String number);

    abstract public boolean checkPinCode(String pinCode);

    abstract public boolean checkDate(Date date);

    abstract public boolean checkCVV(String cvv);

    abstract public boolean checkBalance(double balance);

    abstract public boolean checkCurrency(CurrencyEnumeration currency);

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (checkNumber(number)) {
            this.number = number;
        }
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        if (checkPinCode(pinCode)) {
            this.pinCode = pinCode;
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (checkDate(date)) {
            this.date = date;
        }
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        if (checkCVV(cvv)) {
            this.cvv = cvv;
        }
    }

    public double getBalance() {
        return balance;
    }

    public synchronized void setBalance(double balance) {
        if (checkBalance(balance) == true) {
            this.balance = balance;
        }
    }

    public CurrencyEnumeration getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnumeration currency) {
        if (checkCurrency(currency)) {
            this.currency = currency;
        }
    }

}
