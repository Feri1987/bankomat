/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.startPoint;

import bankomat.abstractClasses.Card;
import bankomat.cardsImpl.CardWithMagneticStringPrivatbank;
import bankomat.clientsPurse.AutoClientAccount;
import static bankomat.main.Main.LOGGER;
import bankomat.pratsOfATM.Bankomat;
import bankomat.pratsOfATM.CardHolder;
import bankomat.pratsOfATM.Casset;
import bankomat.pratsOfATM.Monitor;
import bankomat.pratsOfATM.Printer;
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

    public void start() {

        Casset casset = new Casset();
        AutoClientAccount autoClientAccount = new AutoClientAccount();
        List<Card> listAcc = autoClientAccount.autoInitCards();
        Monitor monitor = new Monitor();
        Printer printer = new Printer();

        CardWithMagneticStringPrivatbank card = new CardWithMagneticStringPrivatbank();
        CardHolder cardHolder = new CardHolder(card);
        Bankomat bankomat = new Bankomat(monitor, casset, cardHolder, printer);

        List<Casset> cassets = initBankomat(monitor, casset);
        card = (CardWithMagneticStringPrivatbank) waitMenu(autoClientAccount, card, cardHolder);

        while (true) {
            menu(cassets, card, bankomat, listAcc);
            System.out.println("\nЖелаете продолжить работу с банкоматом?\n1.Да\n2.Нет\nОжидается ввод: ");
            Scanner scanner = new Scanner(System.in);

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

    }

    public List<Casset> initBankomat(Monitor monitor, Casset casset) {
        Scanner scanner = new Scanner(System.in);
        List<Casset> list = new ArrayList<Casset>();
        StringBuilder init = new StringBuilder("Загрузка банкомата...\n");
        init.append("1. Автоматическая.\n").
                append("2. Ручная.\n").
                append("\nВыберите пункт меню: ");
        System.out.print(init);

        label:
        while (true) {
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
                    monitor.autoInit();
                    list = casset.autoInitCasset();
                    break label;
                case (2):
                    System.out.println("\nНастройка монитора...");
                    LOGGER.log(Level.INFO, "Максимальное разрешение экрана: 800х600");
                    while (true) {
                        System.out.print("Высота экрана: ");
                        int height = 0;
                        while (true) {
                            if (scanner.hasNextInt()) {
                                height = scanner.nextInt();
                                break;
                            } else {
                                LOGGER.log(Level.WARNING, "Надо нажимать циферки!");
                                System.out.print("\nПовторите ввод: ");
                                scanner.next();
                            }
                        }
                        System.out.print("Ширина экрана: ");
                        int size = 0;
                        while (true) {
                            if (scanner.hasNextInt()) {
                                size = scanner.nextInt();
                                break;
                            } else {
                                LOGGER.log(Level.WARNING, "Надо нажимать циферки!");
                                System.out.print("\nПовторите ввод: ");
                                scanner.next();
                            }
                        }
                        if (monitor.init(height, size)) {
                            break;
                        } else {
                            LOGGER.log(Level.WARNING, "Данные введены неверно, повторите попытку");
                        }
                    }
                    System.out.println("\nЗагрузка кассет...");
                    list = casset.getCassetList();
                    break label;
                default:
                    System.out.println("Такого меню нет, повторите ввод:");
                    break;
            };
        }
        return list;
    }

    public Card waitMenu(AutoClientAccount autoClientAccount, Card card, CardHolder cardHolder) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder start = new StringBuilder("\nВставьте, пожалуйста, карту в банкомат\n");
        start.append("1. Карта из кошелька.\n").
                append("2. Вставить другую карту.\n").
                append("\nВыберите пункт меню: ");
        System.out.print(start);

        label1:
        while (true) {
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

                    int from = 0;
                    while (true) {
                        if (scanner.hasNextInt()) {
                            from = scanner.nextInt();
                            break;
                        } else {
                            LOGGER.log(Level.WARNING, "Надо нажимать циферки!");
                            System.out.print("\nПовторите ввод: ");
                            scanner.next();
                        }
                    }

                    card = (CardWithMagneticStringPrivatbank) autoClientAccount.autoInitCards().get(from - 1);
                    break label1;
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
                    break label1;
                default:
                    System.out.println("Такого меню нет, повторите ввод:");
                    break;

            }
        };
        return card;
    }

    public void menu(List<Casset> cassets, Card card, Bankomat bankomat, List<Card> listAcc) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(new StringBuilder("\nМеню банкомата:\n").append("1. Баланс на экран.\n").
                append("2. Снять деньги с карты.\n").
                append("3. Сделать перевод на другую карту.\n").
                append("\nВыберите пункт меню: "));

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
                System.out.println(new StringBuilder("\nБаланс карты: ").append(bankomat.showBalance(card)));
                break;
            case (2):
                cashTaken(card, cassets, bankomat);
                break;
            case (3):
                transferMoney(listAcc, bankomat);
                break;
        };
    }

    public void transferMoney(List<Card> listAcc, Bankomat bankomat) {
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

        Scanner scanner = new Scanner(System.in);
        System.out.print("Выберите карту с которой хотите перевести средства: ");

        int from = 0;
        while (true) {
            if (scanner.hasNextInt()) {
                from = scanner.nextInt();
                break;
            } else {
                LOGGER.log(Level.WARNING, "Надо нажимать циферки!");
                System.out.print("\nПовторите ввод: ");
                scanner.next();
            }
        }

        System.out.print("Выберите карту на которую хотите перевести средства: ");

        int to = 0;
        while (true) {
            if (scanner.hasNextInt()) {
                to = scanner.nextInt();
                break;
            } else {
                LOGGER.log(Level.WARNING, "Надо нажимать циферки!");
                System.out.print("\nПовторите ввод: ");
                scanner.next();
            }
        }

        System.out.print(new StringBuilder("Для перевода доступно: ").
                append(listAcc.get(from - 1).getBalance()).
                append("\nУкажите сумму для перевода: "));

        double sum = 0;
        while (true) {
            if (scanner.hasNextDouble()) {
                sum = scanner.nextDouble();
                break;
            } else {
                LOGGER.log(Level.WARNING, "Надо нажимать циферки!");
                System.out.print("\nПовторите ввод: ");
                scanner.next();
            }
        }
        label2:
        while (true) {
            if (bankomat.calculateTransferMoney(listAcc.get(from - 1), listAcc.get(to - 1), sum)) {
                break label2;
            } else {
                transferMoney(listAcc, bankomat);
            }
        }
    }

    public void cashTaken(Card card, List<Casset> cassets, Bankomat bankomat) {
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
        Scanner scanner = new Scanner(System.in);
        int temp = scanner.nextInt();
        int sum = 0;
        while (sum == 0) {
            if (temp % minSum == 0) {
                sum = temp;
            } else {
                LOGGER.log(Level.WARNING, "Сумма указана некорректно, повторите ввод: ");

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

        if (sum <= card.getBalance()) {
            for (Casset casset : cassets) {
                TreeMap<Integer, Integer> tempCash = casset.getCountOfdenominations();
                if (sum < bankomatCash) {
                    if (casset.getCurr().equals(card.getCurrency())) {
                        needToTake = bankomat.calculation(sum, tempCash);
                    }
                } else {
                    casset.cassetEmptyInforming();
                }
            }
        } else {
            LOGGER.log(Level.WARNING, "На Вашей карте недостаточно средств");
        }
        int cash = 0;
        for (Map.Entry<Integer, Integer> minus : needToTake.entrySet()) {
            cash += minus.getKey() * minus.getValue();
        }
        card.setBalance(card.getBalance() - cash);

        System.out.print("\nРаспечатать чек??\n1.Да\n2.Нет\nОжидается ввод: ");

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

        if (temp == 1) {
            bankomat.getPrinter().print(card, sum);
        }
    }

}
