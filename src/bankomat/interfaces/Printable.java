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
public interface Printable {

    void print(Card card, double sum);

}
