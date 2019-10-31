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
public abstract class CardChip extends CardMagnetic {

    private String chip;

    public abstract boolean checkChip(String chip);

    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getChip() {
        return chip;
    }
}
