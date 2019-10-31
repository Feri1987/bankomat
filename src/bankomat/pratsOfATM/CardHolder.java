/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.pratsOfATM;

import bankomat.abstractClasses.Card;
import bankomat.interfaces.CardHolderable;

/**
 *
 * @author Denis
 */
public class CardHolder implements CardHolderable {

    private Card card;
    private boolean holdCard;

    public CardHolder(Card card) {
        this.card = card;
        holdCard = false;
    }

    @Override
    public Card returnCard(Card card) {
        StringBuilder builder = new StringBuilder("Карта возвращена клиенту");
        return card;
    }

    @Override
    public boolean getCardFromClient(Card card, String pin) {

        if (card.checkDate(card.getDate()) && card.checkPinCode(card.getPinCode())&& card.getPinCode() == pin) {
            holdCard = true;
            StringBuilder builder = new StringBuilder("Карта прошла проверку");
        } else {
            withdrawCard();
        }
        return holdCard;
    }

    @Override
    public void withdrawCard() {
        holdCard = false;
        StringBuilder builder = new StringBuilder("Карта изъята банкоматом");

    }

}
