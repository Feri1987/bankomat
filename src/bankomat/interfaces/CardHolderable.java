/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.interfaces;

import bankomat.abstractClasses.Card;

/**
 *
 * @author Denis
 */
public interface CardHolderable {

    Card returnCard(Card card);

    boolean getCardFromClient(Card card, String pin);

    void withdrawCard();

}
