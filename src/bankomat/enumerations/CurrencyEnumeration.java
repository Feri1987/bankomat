/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.enumerations;

/**
 *
 * @author Denis
 */
public enum CurrencyEnumeration {
    UAH("grn", 980, new int[]{50, 100, 200, 500}),
    USD("usd", 840, new int[]{10, 20, 50, 100}),
    EUR("eur", 978, new int[]{10, 20, 50, 100});

    private String name;
    private int code;
    private int[] denominations;

    private CurrencyEnumeration(String name, int code, int... denominations) {
        this.name = name;
        this.code = code;
        this.denominations = denominations;
    }

    public int[] getDenominations() {
        return denominations;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

}
