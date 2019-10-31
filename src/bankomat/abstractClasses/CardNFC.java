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
public abstract class CardNFC extends CardChip {

    private String nfc;

    public abstract boolean checkNFC(String nfc);

    public String getNFC() {
        return nfc;
    }

    public void setNfc(String nfc) {
        this.nfc = nfc;
    }

}
