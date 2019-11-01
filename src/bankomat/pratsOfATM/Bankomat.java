/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.pratsOfATM;

import bankomat.abstractClasses.Card;
import bankomat.interfaces.BankomatOperationable;
import static bankomat.main.Main.LOGGER;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

/**
 *
 * @author Denis
 */
public class Bankomat implements BankomatOperationable {

    private Monitor monitor;
    private Casset casset;
    private CardHolder cardHolder;
    private Printer printer;

    public Bankomat(Monitor monitor, Casset casset, CardHolder cardHolder, Printer printer) {
        this.monitor = monitor;
        this.casset = casset;
        this.cardHolder = cardHolder;
        this.printer = printer;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public Casset getCasset() {
        return casset;
    }

    public CardHolder getCardHolder() {
        return cardHolder;
    }

    public Printer getPrinter() {
        return printer;
    }

    

    public Map<Integer, Integer> calculation(int sum, TreeMap<Integer, Integer> cassetInfo) {

        TreeMap<Integer, Integer> needToTake = new TreeMap<Integer, Integer>();
        TreeMap<Integer, Integer> tempCash = (TreeMap<Integer, Integer>) cassetInfo.clone();
        Map.Entry<Integer, Integer> firstItem = tempCash.firstEntry();

        while (sum > 0) {
            int topKey = 0;
            int tempRatio = 10000;
            for (Map.Entry<Integer, Integer> denom : tempCash.entrySet()) {
                if (tempRatio > sum / denom.getKey() && sum / denom.getKey() >= 1) {
                    tempRatio = sum / denom.getKey();
                    topKey = denom.getKey();
                }
            }
            for (Map.Entry<Integer, Integer> denom : tempCash.entrySet()) {
                int ratio = sum / topKey;
                int rest = sum % topKey;
                int ratioFirstElem = rest / firstItem.getKey();
                int restFirstElem = rest % firstItem.getKey();

                if (denom.getKey() == topKey) {
                    if (ratio == denom.getValue() && rest == 0) {
                        needToTake.put(topKey, denom.getValue());
                        sum = rest;
                        tempCash.remove(topKey);
                        cassetInfo.remove(topKey);
                        break;
                    } else if (ratio == denom.getValue() && rest > 0 && restFirstElem == 0) {
                        needToTake.put(denom.getKey(), denom.getValue());
                        sum = rest;
                        tempCash.remove(topKey);
                        cassetInfo.remove(topKey);
                        break;
                    } else if (ratio > denom.getValue() && rest > 0 && restFirstElem == 0) {
                        needToTake.put(topKey, denom.getValue());
                        sum -= denom.getKey() * denom.getValue();
                        tempCash.remove(denom.getKey());
                        cassetInfo.remove(topKey);
                        break;
                    } else if (ratio > denom.getValue() && rest == 0) {
                        needToTake.put(topKey, denom.getValue());
                        sum -= denom.getKey() * denom.getValue();
                        tempCash.remove(topKey);
                        cassetInfo.remove(topKey);
                        break;
                    } else if (ratio < denom.getValue() && rest > 0 && restFirstElem == 0) {
                        needToTake.put(denom.getKey(), ratio);
                        sum -= denom.getKey() * ratio;
                        tempCash.remove(topKey);
                        cassetInfo.put(topKey, denom.getValue() - ratio);
                        break;
                    } else if (ratio < denom.getValue() && rest == 0) {
                        needToTake.put(topKey, ratio);
                        sum = rest;
                        tempCash.remove(topKey);
                        cassetInfo.put(topKey, denom.getValue() - ratio);
                        break;
                    } else if (rest == 10) {
                        tempCash.remove(topKey);
                        break;
                    } else if (ratio < denom.getValue() && rest > 10 && ratioFirstElem == 1) {
                        tempCash.remove(topKey);
                        break;
                    } else if (ratio < denom.getValue() && rest > 0 && restFirstElem > 0) {
                        needToTake.put(topKey, ratio);
                        sum = rest;
                        tempCash.remove(topKey);
                        cassetInfo.put(topKey, denom.getValue() - ratio);
                        break;
                    }
                }
            }
        }

        for (Map.Entry e : needToTake.entrySet()) {
            System.out.println(new StringBuilder("Банкомат выдал: ").
                                append(e.getValue()).
                                append(" купюр, номиналом: ").
                                append(e.getKey()));
        }
        StringBuilder text = new StringBuilder("\nВ банкомате осталось:\n");
        for (Map.Entry<Integer, Integer> last : cassetInfo.entrySet()) {
            text.append("Номинал: ").
                    append(last.getKey()).
                    append(", купюр: ").
                    append(last.getValue()).
                    append("\n");
        }
        System.out.println(text);
        return needToTake;
    }

    @Override
    public boolean calculateTransferMoney(Card fromCard, Card toCard, double sum) {
        if (checkBalance(fromCard, sum) && fromCard.getCurrency().equals(toCard.getCurrency())) {
            toCard.setBalance(toCard.getBalance() + sum);
            fromCard.setBalance(fromCard.getBalance() - sum);
            System.out.println(new StringBuilder().
                                append("\nБаланс карты ").
                                append(fromCard.getNumber()).
                                append(" после операции составляет: ").
                                append(fromCard.getBalance()).
                                append("    ").
                                append(fromCard.getCurrency().name()).
                                append("\n").
                                append("Баланс карты ").
                                append(toCard.getNumber()).
                                append(" после пополнения составляет: ").
                                append(toCard.getBalance()).
                                append("  ").
                                append(fromCard.getCurrency().name()).
                                append("\n"));
            return true;
        } else {
            LOGGER.log(Level.WARNING, "Карты разной валюты или не хватает средств");
            return false;
        }
    }

    @Override
    public double showBalance(Card card) {
        return card.getBalance();
    }

    @Override
    public void initBankomat(List<Casset> list, Monitor monitor) {
        Casset a1 = new Casset();
        a1.autoInitCasset();
    }

    @Override
    public boolean checkBalance(Card card, double sum) {
        if (card.getBalance() >= sum) {
            return true;
        } else {
            return false;
        }
    }
}
