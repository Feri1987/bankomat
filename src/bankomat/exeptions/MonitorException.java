/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.exeptions;

import java.util.Random;

/**
 *
 * @author Denis
 */
public class MonitorException extends Exception {

    public MonitorException() {
    }

    @Override
    public void printStackTrace() {
        StringBuffer buffer = new StringBuffer("Проблема в мониторе! Описание ниже:  \n");
        Random random = new Random();
        int rand = random.nextInt(super.getStackTrace().length) + 1;
        for (int i = 0; i < super.getStackTrace().length; i++) {

            for (int y = 0; y < rand; y++) {
                buffer.append("\t");
            }
            buffer.append(super.getStackTrace()[i]).append("\t").append(rand).append("\n");
        }
        System.out.println(buffer);

    }

}
