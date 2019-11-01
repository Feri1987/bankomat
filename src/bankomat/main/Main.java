/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.main;

import bankomat.startPoint.BankomatStart;
import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author Denis
 */
public class Main {

    public static Logger LOGGER;

    static {
        try (FileInputStream ins = new FileInputStream("src/bankomat/logger.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public static void main(String[] args) {

        BankomatStart start = new BankomatStart();
        start.start();

    }

}
