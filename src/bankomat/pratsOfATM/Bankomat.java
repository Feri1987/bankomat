/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.pratsOfATM;

import bankomat.abstractClasses.Card;
import bankomat.clientsPurse.AutoClientAccount;
import bankomat.interfaces.BankomatOperationable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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

    @Override
    public void cashTaken(Card card, List<Casset> cassets) {
        StringBuilder menu = new StringBuilder("\nСумма для снятия должна быть кратной:\n");
        boolean take = false;
        int bankomatCash = 0;
        int minSum = 0;
        for (Casset casset : cassets) {
            if (casset.getCurr().equals(card.getCurrency())) {
                for (Map.Entry<Integer, Integer> denom : casset.countOfdenominations.entrySet()) {
                    minSum = casset.countOfdenominations.firstEntry().getKey();
                    bankomatCash += denom.getKey() * denom.getValue();
                    menu.append(denom.getKey()).
                            append(" - ");
                }
            }
        }
        menu.append("\nДоступная сумма для снятия: ").
                append(bankomatCash).
                append("\nВведите сумму для снятия: ");
        System.out.print(menu);
        Map<Integer, Integer> needToTake = new TreeMap<Integer, Integer>();
        Scanner scanner = new Scanner(System.in);
        int temp = scanner.nextInt();
        int sum = 0;
        while (sum == 0) {
            if (temp % minSum == 0) {
                sum = temp;
            } else {
                System.out.println("Сумма указана некорректно, повторите ввод: ");
                temp = scanner.nextInt();
            }
        }

        if (sum <= card.getBalance()) {
            for (Casset casset : cassets) {
                TreeMap<Integer, Integer> tempCash = casset.countOfdenominations;
                if (sum < bankomatCash) {
                    if (casset.getCurr().equals(card.getCurrency())) {
                        needToTake = calculation(sum, tempCash);
                    }
                } else {
                    casset.cassetEmptyInforming();
                }
            }
        } else {
            System.out.println("На Вашей карте недостаточно средств");
        }
        int cash = 0;
        for (Map.Entry<Integer, Integer> minus : needToTake.entrySet()) {
            cash += minus.getKey() * minus.getValue();
        }
        card.setBalance(card.getBalance() - cash);
    }

    private Map calculation(int sum, TreeMap<Integer, Integer> cassetInfo) {

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
            System.out.println("Банкомат выдал: " + e.getValue() + " купюр, номиналом: " + e.getKey());
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
    public void transferMoney() {
        AutoClientAccount client = new AutoClientAccount();
        List<Card> listAcc = client.autoInitCards();
        int temp = 1;
        for (Card cardFromList : listAcc) {
            System.out.print(temp + ". " + cardFromList.getNumber() + "   " + cardFromList.getBalance() + "\n");
            temp++;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Выберите карту с которой хотите перевести средства: ");
        int from = scanner.nextInt();
        System.out.print("Выберите карту на которую хотите перевести средства: ");
        int to = scanner.nextInt();
        System.out.print("Для перевода доступно: " + listAcc.get(from - 1).getBalance() + "\nУкажите сумму для перевода: ");
        double sum = scanner.nextDouble();
        calculateTransferMoney(listAcc.get(from - 1), listAcc.get(to - 1), sum);
    }

    @Override
    public boolean calculateTransferMoney(Card fromCard, Card toCard, double sum) {
        if (checkBalance(fromCard, sum) && !fromCard.getCurrency().equals(toCard.getCurrency())) {
            toCard.setBalance(toCard.getBalance() + sum);
            fromCard.setBalance(fromCard.getBalance() - sum);
            return true;
        } else {
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
