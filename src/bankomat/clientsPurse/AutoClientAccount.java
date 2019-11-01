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
        
        CardWithMagneticStringPrivatbank card1 = new CardWithMagneticStringPrivatbank();
        card1.setCurrency(CurrencyEnumeration.UAH);
        card1.setBalance(6000);
        card1.setCvv("111");
        card1.setDate(new Date(01/12/2021));
        card1.setMagneticString("Купцов Денис");
        card1.setNumber("1111222233334444");
        card1.setPinCode("1234");
        
        CardWithMagneticStringPrivatbank card2 = new CardWithMagneticStringPrivatbank();
        card2.setCurrency(CurrencyEnumeration.USD);
        card2.setBalance(6000);
        card2.setCvv("111");
        card2.setDate(new Date(01/12/2021));
        card2.setMagneticString("Купцов Денис");
        card2.setNumber("4444333322221111");
        card2.setPinCode("1234");
        
        CardWithMagneticStringPrivatbank card3 = new CardWithMagneticStringPrivatbank();
        card3.setCurrency(CurrencyEnumeration.EUR);
        card3.setBalance(6000);
        card3.setCvv("111");
        card3.setDate(new Date(01/12/2021));
        card3.setMagneticString("Купцов Денис");
        card3.setNumber("4444333322225555");
        card3.setPinCode("1234");
        
        CardWithMagneticStringPrivatbank card4 = new CardWithMagneticStringPrivatbank();
        card4.setCurrency(CurrencyEnumeration.UAH);
        card4.setBalance(6000);
        card4.setCvv("111");
        card4.setDate(new Date(01/12/2021));
        card4.setMagneticString("Купцов Денис");
        card4.setNumber("2222333311115555");
        card4.setPinCode("1234");
        
        CardWithMagneticStringPrivatbank card5 = new CardWithMagneticStringPrivatbank();
        card5.setCurrency(CurrencyEnumeration.USD);
        card5.setBalance(6000);
        card5.setCvv("111");
        card5.setDate(new Date(01/12/2021));
        card5.setMagneticString("Купцов Денис");
        card5.setNumber("1111333322221111");
        card5.setPinCode("1234");
        
        CardWithMagneticStringPrivatbank card6 = new CardWithMagneticStringPrivatbank();
        card6.setCurrency(CurrencyEnumeration.EUR);
        card6.setBalance(6000);
        card6.setCvv("111");
        card6.setDate(new Date(01/12/2021));
        card6.setMagneticString("Купцов Денис");
        card6.setNumber("4444333322225555");
        card6.setPinCode("1234");
        
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        list.add(card5);
        list.add(card6);

        return list;
    }
}
