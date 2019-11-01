/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.pratsOfATM;

import bankomat.abstractClasses.Card;
import bankomat.interfaces.Printable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Denis
 */
public class Printer implements Printable {
    
    

    @Override
    public void print(Card card, double sum) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E yyyy.MM.dd 'и время' hh:mm:ss a zzz");
        
        System.out.println(new StringBuilder("\nСнятие с карты: ").
                append(card.getNumber()).
                append("\nСумма снятия: ").
                append(sum).
                append("\nОстаток на карте: ").
                append(card.getBalance()).
                append("\nТекущая дата: ").
                append(dateFormat.format(date)).append("\n"));
    }

}
