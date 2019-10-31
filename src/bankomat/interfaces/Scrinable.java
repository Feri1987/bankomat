/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.interfaces;

import bankomat.enumerations.CurrencyEnumeration;

/**
 *
 * @author Denis
 */
public interface Scrinable {

    void view(int size, int hight, CurrencyEnumeration enumeration);

    void setSize(int size);

    void setHeight(int height);

}
