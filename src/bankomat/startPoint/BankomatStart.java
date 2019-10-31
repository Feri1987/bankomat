/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.startPoint;

import bankomat.abstractClasses.Card;
import bankomat.cardsImpl.CardWithMagneticStringPrivatbank;
import bankomat.clientsPurse.AutoClientAccount;
import bankomat.pratsOfATM.Bankomat;
import bankomat.pratsOfATM.CardHolder;
import bankomat.pratsOfATM.Casset;
import bankomat.pratsOfATM.Monitor;
import bankomat.pratsOfATM.Printer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Denis
 */
public class BankomatStart {

    public void start() {

        Casset casset = new Casset();
        AutoClientAccount autoClientAccount = new AutoClientAccount();
        Monitor monitor = new Monitor();
        Printer printer = new Printer();

        CardWithMagneticStringPrivatbank card = new CardWithMagneticStringPrivatbank();
        CardHolder cardHolder = new CardHolder(card);
        Bankomat bankomat = new Bankomat(monitor, casset, cardHolder, printer);

        List<Casset> cassets = initBankomat(monitor, casset);
        card = (CardWithMagneticStringPrivatbank) waitMenu(autoClientAccount, card, cardHolder);
        while (true) {

            menu(cassets, card, bankomat);
            System.out.println("\nЖелаете продолжить работу с банкоматом?\n1.Да\n2.Нет\nОжидается ввод: ");
            Scanner scanner = new Scanner(System.in);
            int i = scanner.nextInt();
            if (i == 2) {
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

        switch (scanner.nextInt()) {
            case (1):
                monitor.autoInit();
                list = casset.autoInitCasset();
                break;
            case (2):
                System.out.println("\nНастройка монитора...");
                while (true) {
                    System.out.print("Высота экрана: ");
                    int height = scanner.nextInt();
                    System.out.print("Ширина экрана: ");
                    int size = scanner.nextInt();
                    if (monitor.init(height, size)) {
                        break;
                    };
                }
                System.out.println("\nЗагрузка кассет...");
                list = casset.getCassetList();
                break;
        };
        return list;
    }

    public Card waitMenu(AutoClientAccount autoClientAccount, Card card, CardHolder cardHolder) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder start = new StringBuilder("\nВставьте, пожалуйста, карту в банкомат\n");
        start.append("1. Карта из кошелька.\n").
                append("2. Вставить другую карту.\n").
                append("\nВыберите пункт меню: ");
        System.out.print(start);

        switch (scanner.nextInt()) {
            case (1):
                int temp = 1;
                for (Card tempCard : autoClientAccount.autoInitCards()) {
                    System.out.print(temp + ". " + tempCard.getNumber() + "   " + tempCard.getBalance() + " " + tempCard.getCurrency().name() + "\n");
                    temp++;
                }
                System.out.print("\nВыберите карту: ");
                int from = scanner.nextInt();
                card = (CardWithMagneticStringPrivatbank) autoClientAccount.autoInitCards().get(from - 1);
                break;
            case (2):
                String number = scanner.nextLine();
                for (Card c : autoClientAccount.autoInitCards()) {
                    if (number == c.getNumber()) {
                        System.out.print("Введите пинкод: ");
                        String pin = scanner.nextLine();
                        if (cardHolder.getCardFromClient(card, pin)) {
                            card = (CardWithMagneticStringPrivatbank) c;
                        }
                    }
                }
                break;
        };
        return card;
    }

    public void menu(List<Casset> cassets, Card card, Bankomat bankomat) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder menu = new StringBuilder("Меню банкомата:\n");
        menu.append("1. Баланс на экран.\n").
                append("2. Снять деньги с карты.\n").
                append("3. Сделать перевод на другую карту.\n").
                append("\nВыберите пункт меню: ");
        System.out.print(menu);

        switch (scanner.nextInt()) {
            case (1):
                System.out.println("Баланс карты: " + bankomat.showBalance(card));
                break;
            case (2):
                bankomat.cashTaken(card, cassets);
                break;
            case (3):
                bankomat.transferMoney();
                break;
        };
    }

}
