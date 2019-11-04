/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.pratsOfATM;

import bankomat.enumerations.CurrencyEnumeration;
import bankomat.exeptions.MonitorException;
import bankomat.interfaces.Scrinable;

/**
 *
 * @author Denis
 */
public class Monitor implements Scrinable {

    private int width;
    private int height;

    public Monitor() {
    }

    public Monitor(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getSize() {
        return width;
    }

    public void setSize(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean init(int width, int height) {
        
        if ((width > 0 && width <= 800) && (height > 0 && height <= 600)) {
            this.width = width;
            this.height = height;
            return true;
        } else {
            try {
                throw new MonitorException();
            } catch (MonitorException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
    public void autoInit() {
        setHeight(800);
        setSize(600);
        
        StringBuilder builder = new StringBuilder("Экран проинициализирован, параметры: ");
        builder.append(getHeight()).
                append("x").
                append(getSize());
        System.out.println(builder);
    }
    

    @Override
    public void view(int size, int hight, CurrencyEnumeration enumeration) {
        init(size, hight);
    }

}
