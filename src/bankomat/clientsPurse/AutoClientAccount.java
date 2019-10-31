/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.clientsPurse;

import bankomat.abstractClasses.Card;
import bankomat.cardsImpl.CardWithMagneticStringPrivatbank;
import bankomat.enumerations.CurrencyEnumeration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Denis
 */
public class AutoClientAccount {

    public List<Card> autoInitCards() {
        
        List<Card> list = new ArrayList<Card>();
        CurrencyEnumeration e1 = CurrencyEnumeration.UAH;
        CurrencyEnumeration e2 = CurrencyEnumeration.USD;
        CurrencyEnumeration e3 = CurrencyEnumeration.EUR;
        
        CardWithMagneticStringPrivatbank card1 = new CardWithMagneticStringPrivatbank();
        card1.setCurrency(e1);
        card1.setBalance(6000);
        card1.setCvv("111");
        card1.setDate(new Date(01/12/2021));
        card1.setMagneticString("Купцов Денис");
        card1.setNumber("1111222233334444");
        card1.setPinCode("1234");
        
        CardWithMagneticStringPrivatbank card2 = new CardWithMagneticStringPrivatbank();
        card2.setCurrency(e2);
        card2.setBalance(6000);
        card2.setCvv("111");
        card2.setDate(new Date(01/12/2021));
        card2.setMagneticString("Купцов Денис");
        card2.setNumber("4444333322221111");
        card2.setPinCode("1234");
        
        CardWithMagneticStringPrivatbank card3 = new CardWithMagneticStringPrivatbank();
        card3.setCurrency(e3);
        card3.setBalance(6000);
        card3.setCvv("111");
        card3.setDate(new Date(01/12/2021));
        card3.setMagneticString("Купцов Денис");
        card3.setNumber("4444333322225555");
        card3.setPinCode("1234");
        
        list.add(card1);
        list.add(card2);
        list.add(card3);

        return list;
    }
}
