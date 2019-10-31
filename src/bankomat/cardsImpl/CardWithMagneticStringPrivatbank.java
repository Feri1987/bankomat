/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.cardsImpl;

import bankomat.abstractClasses.CardMagnetic;
import bankomat.enumerations.CurrencyEnumeration;
import java.util.Date;

/**
 *
 * @author Denis
 */
public class CardWithMagneticStringPrivatbank extends CardMagnetic {

    @Override
    public boolean checkMagneticString(String magneticString) {
        if (magneticString == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean checkNumber(String number) {
        if (number == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean checkPinCode(String pinCode) {
        if (pinCode == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean checkCVV(String cvv) {
        if (cvv == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean checkDate(Date date) {
        if (date == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean checkBalance(double balance) {
        if (balance < 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean checkCurrency(CurrencyEnumeration currency) {
        if (currency.getCode() == 980 || currency.getCode() == 978 || currency.getCode() == 840) {
            return true;
        } else {
            return false;
        }
    }
}
