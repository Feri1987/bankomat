/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.pratsOfATM;

import bankomat.enumerations.CurrencyEnumeration;
import bankomat.exeptions.CassetException;
import bankomat.interfaces.Cassetable;
import static bankomat.main.Main.LOGGER;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;

/**
 *
 * @author Denis
 */
public class Casset implements Cassetable {

    private CurrencyEnumeration curr;          //валюта
    private List<Integer> denominations;     //номиналы купюр
    private TreeMap<Integer, Integer> countOfdenominations;
    private int cash;

    public Casset() {
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

    public List<Integer> getDenominations() {
        return denominations;
    }

    public void setDenominations(List<Integer> denominations) {
        this.denominations = denominations;
    }

    public TreeMap<Integer, Integer> getCountOfdenominations() {
        return countOfdenominations;
    }

    public void setCountOfdenominations(TreeMap<Integer, Integer> countOfdenominations) {
        this.countOfdenominations = countOfdenominations;
    }

    @Override
    public List<Casset> autoInitCasset() {
        Casset casset = new Casset();
        casset.setCurr(CurrencyEnumeration.UAH);
        casset.countOfdenominations = new TreeMap<Integer, Integer>();
        casset.countOfdenominations.put(50, 100);
        casset.countOfdenominations.put(100, 10);
        casset.countOfdenominations.put(200, 10);
        casset.countOfdenominations.put(500, 10);

        Casset casset2 = new Casset();
        casset2.setCurr(CurrencyEnumeration.USD);
        casset2.countOfdenominations = new TreeMap<Integer, Integer>();
        casset2.countOfdenominations.put(10, 100);
        casset2.countOfdenominations.put(20, 100);
        casset2.countOfdenominations.put(50, 10);
        casset2.countOfdenominations.put(100, 10);

        List<Casset> list = new ArrayList<Casset>();
        list.add(casset);
        list.add(casset2);

        return list;
    }

    @Override
    public void initCasset() {
        System.out.print("1.UAH\n2.USD\n3.EUR\nВыберите номер валюты для загрузки кассеты: ");
        Scanner scanner = new Scanner(System.in);
        countOfdenominations = new TreeMap<Integer, Integer>();

        int a = 0;
        while (true) {
            if (scanner.hasNextInt()) {
                a = scanner.nextInt();
                break;
            } else {
                LOGGER.log(Level.WARNING, "Надо нажимать циферки!");
                System.out.print("\nПовторите ввод: ");
                scanner.next();
            }
        }

        switch (a) {
            case (1):
                curr = CurrencyEnumeration.UAH;
                denominations = Arrays.asList(50, 100, 200, 500);
                System.out.println("Выбрана гривна для загрузки\n");
                break;
            case (2):
                curr = CurrencyEnumeration.USD;
                denominations = Arrays.asList(10, 20, 50, 100);
                System.out.println("Выбран доллар для загрузки\n");
                break;
            case (3):
                curr = CurrencyEnumeration.EUR;
                denominations = Arrays.asList(10, 20, 50, 100);
                System.out.println("Выбрано евро для загрузки\n");
                break;
            default:
                try {
                    throw new CassetException("Выбранной валюты не существует\n");
                } catch (CassetException ex) {
                    ex.getMessage();
                }
                break;
        }
        cash = 0;
        while (true) {
            int temp = 0;
            System.out.println("Введите количество купюр для загрузки. \nДоступные для загрузки номиналы: ");
            for (Integer denomination : denominations) {
                System.out.print("Номиналом " + denomination + " : ");

                while (true) {
                    if (scanner.hasNextInt()) {
                        temp = scanner.nextInt();
                        break;
                    } else {
                        LOGGER.log(Level.WARNING, "Надо нажимать циферки!");
                        System.out.print("\nПовторите ввод: ");
                        scanner.next();
                    }
                }

                int count = 0;
                while (count == 0) {
                    if (temp > 0) {
                        count = temp;
                    } else {
                        LOGGER.log(Level.WARNING, "Ошибка ввода, повторите попытку: ");
                        while (true) {
                            if (scanner.hasNextInt()) {
                                temp = scanner.nextInt();
                                break;
                            } else {
                                LOGGER.log(Level.WARNING, "Надо нажимать циферки!");
                                System.out.print("\nПовторите ввод: ");
                                scanner.next();
                            }
                        }
                    }
                }

                if (countOfdenominations.get(denomination) != null) {
                    countOfdenominations.put(denomination, countOfdenominations.get(denomination) + temp);
                } else {
                    countOfdenominations.put(denomination, count);
                }
                cash += denomination * count;
            }

            StringBuilder text = new StringBuilder("\nКупюр в кассете:\n");
            for (Map.Entry<Integer, Integer> last : countOfdenominations.entrySet()) {

                System.out.println(last.getKey() + last.getValue());

                text.append("Номинал: ").
                        append(last.getKey()).
                        append(", купюр: ").
                        append(last.getValue()).
                        append("\n");
            }
            text.append("\nВ банкомат загружено = ").
                    append(cash).
                    append("\n").
                    append("\nДобавить еще купюры?\n1.Да\n2.Нет\n");
            System.out.println(text);

            while (true) {
                if (scanner.hasNextInt()) {
                    temp = scanner.nextInt();
                    break;
                } else {
                    LOGGER.log(Level.WARNING, "Надо нажимать циферки!");
                    System.out.print("\nПовторите ввод: ");
                    scanner.next();
                }
            }
            if (temp == 2) {
                break;
            }
        }
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

    @Override
    public List<Casset> getCassetList() {
        List<Casset> list = new ArrayList<Casset>();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            Casset casset = new Casset();
            casset.initCasset();
            list.add(casset);

            System.out.print("\nДобавить еще кассету?\n1.Да\n2.Нет\n ");
            int temp = 0;
            while (true) {
                if (scanner.hasNextInt()) {
                    temp = scanner.nextInt();
                    break;
                } else {
                    LOGGER.log(Level.WARNING, "Надо нажимать циферки!");
                    System.out.print("\nПовторите ввод: ");
                    scanner.next();
                }
            }
            if (temp == 2) {
                break;
            }
        }
        return list;
    }
}
