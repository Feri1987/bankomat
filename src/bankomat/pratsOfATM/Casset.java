/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.pratsOfATM;

import bankomat.enumerations.CurrencyEnumeration;
import bankomat.exeptions.CassetException;
import bankomat.interfaces.Cassetable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Denis
 */
public class Casset implements Cassetable {

    private CurrencyEnumeration curr;          //валюта
    private TreeMap<Integer, Integer> countOfdenominations;
    private int cash;

    public Casset() {
        countOfdenominations = new TreeMap<Integer, Integer>();
        cash = 0;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public CurrencyEnumeration getCurr() {
        return curr;
    }

    public void setCurr(CurrencyEnumeration curr) {
        this.curr = curr;
    }

    public TreeMap<Integer, Integer> getCountOfdenominations() {
        return countOfdenominations;
    }

    public void setCountOfdenominations(TreeMap<Integer, Integer> countOfdenominations) {
        this.countOfdenominations = countOfdenominations;
    }

    public List<Casset> autoInitCasset() {
        Casset casset = new Casset();
        casset.setCurr(CurrencyEnumeration.UAH);
        casset.countOfdenominations.put(50, 100);
        casset.countOfdenominations.put(100, 10);
        casset.countOfdenominations.put(200, 10);
        casset.countOfdenominations.put(500, 10);

        Casset casset2 = new Casset();
        casset2.setCurr(CurrencyEnumeration.USD);
        casset2.countOfdenominations.put(10, 100);
        casset2.countOfdenominations.put(20, 100);
        casset2.countOfdenominations.put(50, 10);
        casset2.countOfdenominations.put(100, 10);
        
        Casset casset3 = new Casset();
        casset3.setCurr(CurrencyEnumeration.EUR);
        casset3.countOfdenominations.put(10, 100);
        casset3.countOfdenominations.put(20, 100);
        casset3.countOfdenominations.put(50, 10);
        casset3.countOfdenominations.put(100, 10);

        List<Casset> list = new ArrayList<Casset>();
        list.add(casset);
        list.add(casset2);

        return list;
    }

    @Override
    public void cassetEmptyInforming() {
        try {
            throw new CassetException("Воспользуйтесь другим банкоматом либо укажите меньшую сумму для снятия");
        } catch (CassetException ex) {
            ex.getMessage();
        }
    }

    @Override
    public void giveCash(Map<Integer, Integer> cashDenominations) {
        for (Map.Entry<Integer, Integer> entry : countOfdenominations.entrySet()) {
            for (Map.Entry<Integer, Integer> entryCash : cashDenominations.entrySet()) {
                if (entry.getKey().equals(entryCash.getKey())) {
                    cash = entry.getValue() - entryCash.getValue();
                    countOfdenominations.put(entry.getKey(), cash);
                }
            }
        }
    }
}
