/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.startPoint;

import bankomat.abstractClasses.Card;
import bankomat.cardsImpl.CardWithMagneticStringPrivatbank;
import bankomat.clientsPurse.AutoClientAccount;
import bankomat.enumerations.CurrencyEnumeration;
import bankomat.exeptions.CassetException;
import static bankomat.main.Main.LOGGER;
import bankomat.pratsOfATM.Bankomat;
import bankomat.pratsOfATM.CardHolder;
import bankomat.pratsOfATM.Casset;
import bankomat.pratsOfATM.Monitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;

/**
 *
 * @author Denis
 */
public class BankomatStart {

    private CurrencyEnumeration curr;

    public void start() {

        Casset casset = new Casset();
        Scanner scanner = new Scanner(System.in);
        AutoClientAccount autoClientAccount = new AutoClientAccount();
        List<Card> listAcc = autoClientAccount.autoInitCards();

        CardWithMagneticStringPrivatbank card = new CardWithMagneticStringPrivatbank();
        CardHolder cardHolder = new CardHolder(card);
        Bankomat bankomat = new Bankomat(cardHolder);
        List<Casset> cassets = initBankomat(bankomat.getMonitor(), casset, scanner);

        card = (CardWithMagneticStringPrivatbank) waitMenu(cardHolder, scanner);

        while (true) {
            menu(cassets, card, bankomat, listAcc, scanner);
            System.out.println("\nЖелаете продолжить работу с банкоматом?\n1.Да\n2.Нет\nОжидается ввод: ");

            int temp = checkMenuInsert(2, scanner);

            if (temp == 2) {
                break;
            }
        }
    }

    public List<Casset> initBankomat(Monitor monitor, Casset casset, Scanner scanner) {
        List<Casset> list = new ArrayList<Casset>();
        StringBuilder init = new StringBuilder("Загрузка банкомата...\n");
        init.append("1. Автоматическая.\n").
                append("2. Ручная.\n").
                append("\nВыберите пункт меню: ");
        System.out.print(init);

        int a = checkMenuInsert(2, scanner);
        switch (a) {
            case (1):
                monitor.autoInit();
                list = casset.autoInitCasset();
                break;
            case (2):
                System.out.println("\nНастройка монитора...");
                LOGGER.log(Level.INFO, "Максимальное разрешение экрана: 800х600");
                while (true) {
                    System.out.print("Высота экрана: ");
                    int height = checkInsert(scanner);
                    System.out.print("Ширина экрана: ");
                    int size = checkInsert(scanner);
                    if (monitor.init(height, size)) {
                        break;
                    } else {
                        LOGGER.log(Level.WARNING, "Данные введены неверно, повторите попытку");
                    }
                }
                System.out.println("\nЗагрузка кассет...");
                list = getCassetList(scanner);
                break;
        };
        return list;
    }

    public Card waitMenu(CardHolder cardHolder, Scanner scanner) {
        StringBuilder start = new StringBuilder("\nВставьте, пожалуйста, карту в банкомат\n");
        start.append("1. Карта из кошелька.\n").
                append("2. Вставить другую карту.\n").
                append("\nВыберите пункт меню: ");
        System.out.print(start);
        int a = checkMenuInsert(2, scanner);
        AutoClientAccount autoClientAccount = new AutoClientAccount();
        Card card = new CardWithMagneticStringPrivatbank();

        switch (a) {
            case (1):
                int temp = 1;
                for (Card tempCard : autoClientAccount.autoInitCards()) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(temp).
                            append(". ").
                            append(tempCard.getNumber()).
                            append("   ").
                            append(tempCard.getBalance()).
                            append(" ").
                            append(tempCard.getCurrency().name());
                    System.out.println(builder);
                    temp++;
                }
                System.out.print("\nВыберите карту: ");

                int from = checkMenuInsert(autoClientAccount.autoInitCards().size(), scanner);

                card = (CardWithMagneticStringPrivatbank) autoClientAccount.autoInitCards().get(from - 1);
                break;
            case (2):
                out:
                while (true) {
                    System.out.print("\nВведите номер карты: ");
                    String number = scanner.next();
                    for (Card c : autoClientAccount.autoInitCards()) {
                        if (number.equals(c.getNumber())) {
                            System.out.print("\nВведите пинкод: ");
                            String pin = scanner.next();
                            if (cardHolder.getCardFromClient(c, pin)) {
                                card = (CardWithMagneticStringPrivatbank) c;
                                LOGGER.log(Level.INFO, "Карта успешно прошла проверку");
                                break out;
                            } else {
                                LOGGER.log(Level.WARNING, "Неверные данные, повторите ввод");
                            }
                        }
                    }
                }
                break;
        };
        return card;
    }

    public void menu(List<Casset> cassets, Card card, Bankomat bankomat, List<Card> listAcc, Scanner scanner) {
        System.out.print(new StringBuilder("\nМеню банкомата:\n").append("1. Баланс на экран.\n").
                append("2. Снять деньги с карты.\n").
                append("3. Сделать перевод на другую карту.\n").
                append("\nВыберите пункт меню: "));

        int a = checkMenuInsert(3, scanner);

        switch (a) {
            case (1):
                System.out.println(new StringBuilder("\nБаланс карты: ").append(bankomat.showBalance(card)));
                break;
            case (2):
                cashTaken(card, cassets, bankomat, scanner);
                break;
            case (3):
                transferMoney(listAcc, bankomat, scanner);
                break;
        };
    }

    public void transferMoney(List<Card> listAcc, Bankomat bankomat, Scanner scanner) {
        int temp = 1;
        for (Card cardFromList : listAcc) {
            System.out.print(new StringBuilder().
                    append(temp).
                    append(". ").
                    append(cardFromList.getNumber()).
                    append("   ").
                    append(cardFromList.getBalance()).
                    append("  ").
                    append(cardFromList.getCurrency().name()).
                    append("\n"));
            temp++;
        }
        System.out.print("Выберите карту с которой хотите перевести средства: ");

        int from = checkMenuInsert(listAcc.size(), scanner);

        System.out.print("Выберите карту на которую хотите перевести средства: ");

        int to = checkMenuInsert(listAcc.size(), scanner);

        System.out.print(new StringBuilder("Для перевода доступно: ").
                append(listAcc.get(from - 1).getBalance()).
                append("\nУкажите сумму для перевода: "));

        double sum = 0;
        while (true) {
            if (scanner.hasNextDouble()) {
                sum = scanner.nextDouble();
                if (sum > 0) {
                    break;
                } else {
                    LOGGER.log(Level.WARNING, "Введите число больше нуля");
                    System.out.print("\nПовторите ввод: ");
                }
                break;
            } else {
                LOGGER.log(Level.WARNING, "Надо вводить число!");
                System.out.print("\nПовторите ввод: ");
                scanner.next();
            }
        }
        label2:
        while (true) {
            if (bankomat.calculateTransferMoney(listAcc.get(from - 1), listAcc.get(to - 1), sum)) {
                break label2;
            } else {
                transferMoney(listAcc, bankomat, scanner);
            }
        }
    }

    public void cashTaken(Card card, List<Casset> cassets, Bankomat bankomat, Scanner scanner) {
        StringBuilder menu = new StringBuilder("\nСумма для снятия должна быть кратной:\n");
        boolean take = false;
        int bankomatCash = 0;
        int minSum = 0;
        for (Casset casset : cassets) {
            if (casset.getCurr().equals(card.getCurrency())) {
                for (Map.Entry<Integer, Integer> denom : casset.getCountOfdenominations().entrySet()) {
                    minSum = casset.getCountOfdenominations().firstEntry().getKey();
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
        int temp = checkInsert(scanner);
        int sum = 0;
        synchronized (card) {
            while (sum == 0) {
                if (temp % minSum == 0) {
                    if (bankomat.checkBalance(card, temp)) {
                        for (Casset casset : cassets) {
                            TreeMap<Integer, Integer> tempCash = casset.getCountOfdenominations();
                            if (temp < bankomatCash) {
                                if (casset.getCurr().equals(card.getCurrency())) {
                                    sum = temp;
                                    needToTake = bankomat.calculation(sum, tempCash);
                                }
                            } else {
                                casset.cassetEmptyInforming();
                            }
                        }
                        int cash = 0;
                        for (Map.Entry<Integer, Integer> minus : needToTake.entrySet()) {
                            cash += minus.getKey() * minus.getValue();
                        }
                        card.setBalance(card.getBalance() - cash);
                    } else {
                        LOGGER.log(Level.WARNING, "На Вашей карте недостаточно средств");
                        temp = checkInsert(scanner);
                    }
                } else {
                    LOGGER.log(Level.WARNING, "Сумма указана некорректно, повторите ввод: ");
                    temp = checkInsert(scanner);
                }
            }
        }
        System.out.print("\nРаспечатать чек??\n1.Да\n2.Нет\nОжидается ввод: ");

        temp = checkMenuInsert(2, scanner);

        if (temp == 1) {
            bankomat.getPrinter().print(card, sum);
        }
    }

    public void initCasset(Casset casset, Scanner scanner) {
        System.out.print("1.UAH\n2.USD\n3.EUR\nВыберите номер валюты для загрузки кассеты: ");
        int a = checkMenuInsert(3, scanner);

        switch (a) {
            case (1):
                curr = CurrencyEnumeration.UAH;
                System.out.println("Выбрана гривна для загрузки\n");
                break;
            case (2):
                curr = CurrencyEnumeration.USD;
                System.out.println("Выбран доллар для загрузки\n");
                break;
            case (3):
                curr = CurrencyEnumeration.EUR;
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
        int cash = 0;
        while (true) {
            int temp = 0;
            System.out.println("Введите количество купюр для загрузки. \nДоступные для загрузки номиналы: ");
            for (Integer denomination : curr.getDenominations()) {
                System.out.print("Номиналом " + denomination + " : ");

                temp = checkInsert(scanner);

                if (casset.getCountOfdenominations().get(denomination) != null) {
                    casset.getCountOfdenominations().put(denomination, casset.getCountOfdenominations().get(denomination) + temp);
                } else {
                    casset.getCountOfdenominations().put(denomination, temp);
                }
                cash += denomination * temp;
            }

            StringBuilder text = new StringBuilder("\nКупюр в кассете:\n");
            for (Map.Entry<Integer, Integer> last : casset.getCountOfdenominations().entrySet()) {

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
                    append("\nДобавить еще купюры?\n1.Да\n2.Нет");
            System.out.println(text);

            temp = checkMenuInsert(2, scanner);
            if (temp == 2) {
                break;
            }
        }
    }

    public List<Casset> getCassetList(Scanner scanner) {
        List<Casset> list = new ArrayList<Casset>();
        while (true) {
            Casset casset = new Casset();
            initCasset(casset, scanner);
            list.add(casset);

            System.out.print("\nДобавить еще кассету?\n1.Да\n2.Нет\n ");
            int temp = checkMenuInsert(2, scanner);
            if (temp == 2) {
                break;
            }
        }
        return list;
    }

    public int checkMenuInsert(int i, Scanner scanner) {
        int temp = 0;
        while (true) {
            temp = checkInsert(scanner);
            if (temp <= i) {
                return temp;
            } else {
                LOGGER.log(Level.WARNING, "нет такого пункта меню!");
                System.out.print("\nПовторите ввод: ");
            }
        }
    }

    public int checkInsert(Scanner scanner) {
        int temp = 0;
        while (true) {
            if (scanner.hasNextInt()) {
                temp = scanner.nextInt();
                if (temp >= 1) {
                    return temp;
                } else {
                    LOGGER.log(Level.WARNING, "Введите число больше нуля");
                    System.out.print("\nПовторите ввод: ");
                }
            } else {
                LOGGER.log(Level.WARNING, "Надо вводить число!");
                System.out.print("\nПовторите ввод: ");
                scanner.next();
            }
        }
    }
}
