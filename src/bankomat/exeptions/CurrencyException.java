/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.exeptions;

/**
 *
 * @author Denis
 */
public class CurrencyException extends Exception {

    private String str;

    public CurrencyException() {
        str = "Возникла проблемка с валютой! Описание ниже:  \n";
    }

    public String getStr() {
        return str;
    }

    @Override
    public void printStackTrace() {

        System.out.println(str);

        for (int i = 0; i < super.getStackTrace().length; i++) {
            System.out.println("\t" + super.getStackTrace()[i]);
        }

    }

}
