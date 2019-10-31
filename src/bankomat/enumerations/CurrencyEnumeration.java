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
    UAH("grn", 980),
    USD("usd", 840),
    EUR("eur", 978);

    private String name;
    private int code;

    CurrencyEnumeration(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

}
