/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.interfaces;

import bankomat.abstractClasses.Card;
import bankomat.pratsOfATM.Casset;
import bankomat.pratsOfATM.Monitor;
import java.util.List;

/**
 *
 * @author Denis
 */
public interface BankomatOperationable {

    void cashTaken(Card card, List<Casset> casset);

    void transferMoney();

    boolean calculateTransferMoney(Card card, Card card2, double sum);

    double showBalance(Card card);

    void initBankomat(List<Casset> list, Monitor monitor);

    boolean checkBalance(Card card, double sum);
    
}
