/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.abstractClasses;

/**
 *
 * @author Denis
 */
public abstract class CardMagnetic extends Card {

    private String magneticString;

    public String getMagneticString() {
        return magneticString;
    }

    public void setMagneticString(String magneticString) {
        this.magneticString = magneticString;
    }

    public abstract boolean checkMagneticString(String magneticString);

}
