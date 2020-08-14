package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    final static Scanner scanner;
    static Double money;
    final static List<String[]> purchases;

    static {
        scanner = new Scanner(System.in);
        money = 0.00;
        purchases = new ArrayList<>();
    }

    public static void main(String[] args) {

        int option = -1;
        while (option != 0) {
            switch (option = menu()) {
                case 1:
                    addIncome();
                    break;
                case 2:
                    addPurchase();
                    break;
                case 3:
                    showListOfPurchases();
                    break;
                case 4:
                    balance();
                    break;
            }
        }

        System.out.println("Bye!");
    }

    private static int menu() {
        System.out.println();
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("0) Exit");
        int ret = Integer.parseInt(scanner.nextLine());
        System.out.println();

        return ret;
    }

    private static void addIncome() {
        System.out.println("Enter income:");
        money += Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added!");
    }

    private static void balance() {
        System.out.printf("Balance: $%.2f\n", money);
    }

    private static void addPurchase() {
        String[] details = new String[2];

        System.out.println("Enter purchase name:");
        details[0] = scanner.nextLine();

        System.out.println("Enter its price:");
        details[1] = scanner.nextLine();
        money -= Double.parseDouble(details[1]);

        purchases.add(details);
        System.out.println("Purchase was added!");
    }

    private static void showListOfPurchases() {
        if (purchases.size() == 0) {
            System.out.println("Purchase list is empty");
            return;
        }

        Double total = 0.00;

        for (String[] item : purchases) {
            System.out.printf("%s $%s\n", item[0], item[1]);
            total += Double.parseDouble(item[1]);
        }

        System.out.printf("Total sum: $%s\n", total);
    }
}
