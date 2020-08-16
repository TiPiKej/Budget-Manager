package budget;

import java.io.*;
import java.util.*;

public class Main {
    final static Scanner scanner;
    static Double money;
    final static List<String[]> purchases;
    final static String locationOfSave = "purchases.txt";
    final static String separator = ";";

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
                case 5:
                    saveToFile();
                    break;
                case 6:
                    loadFile();
                    break;
                case 7:
                    analyze();
                    break;
            }

            System.out.println();
        }

        System.out.println("Bye!");
    }

    private static int menu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
        int ret = Integer.parseInt(scanner.nextLine());
        System.out.println();

        return ret;
    }

    private static int menuPurchase() {
        return menuPurchase(false, true);
    }

    private static int menuPurchase(boolean withAll, boolean withBack) {
        System.out.println("\nChoose the type of purchase");
        List<String> options = new ArrayList<>();
        options.add("Food");
        options.add("Clothes");
        options.add("Entertainment");
        options.add("Other");
        if (withAll) options.add("All");
        if (withBack) options.add("Back");

        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, options.get(i));
        }

        int ret = Integer.parseInt(scanner.nextLine());
        System.out.println();

        if (withAll) {
            if (!options.get(ret - 1).equals("Back")) System.out.println(options.get(ret - 1) + ":");
        }

        return ret;
    }

    private static int menuAnalyze() {
        System.out.println("How do you want to sort?");
        List<String> options = new ArrayList<>();
        options.add("Sort all purchases");
        options.add("Sort by type");
        options.add("Sort certain type");
        options.add("Back");

        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, options.get(i));
        }

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
        int option = -1;
        while ((option = menuPurchase()) != 5 ) {
            String[] details = new String[3];

            System.out.println("Enter purchase name:");
            details[0] = scanner.nextLine();

            System.out.println("Enter its price:");
            details[1] = String.valueOf(Double.parseDouble(scanner.nextLine()));
            money -= Double.parseDouble(details[1]);

            details[2] = Integer.toString(option);

            purchases.add(details);
            System.out.println("Purchase was added!");
        }
    }

    private static void showListOfPurchases() {
        Double total;
        int counter = 0;

        int option = -1;
        while ((option = menuPurchase(true, true)) != 6) {
            total = 0.00;
            for (String[] item : purchases) {
                if (option != Integer.parseInt(item[2]) && option != 5) continue;
                System.out.printf("%s $%s\n", item[0], parseDouble(item[1]));
                total += Double.parseDouble(item[1]);
                counter++;
            }

            if (counter == 0) {
                System.out.println("Purchase list is empty!");
            } else {
                System.out.printf("Total sum: $%.2f\n\n", total);
            }
        }
    }

    private static String parseDouble(String str) {
        int money = (int) (Double.parseDouble(str) * 100);
        String ret = str;

        if (money % 10 == 0) ret += "0";
        return ret;
    }

    private static String parseDouble(double value) {
        return parseDouble(String.valueOf(value));
    }

    private static void saveToFile() {
        File save = new File(locationOfSave);
        try (PrintWriter printWriter = new PrintWriter(save)) {
            printWriter.println(money);
            for (String[] purchase : purchases) {
                StringBuilder temp = new StringBuilder();
                for (int i = 0; i < purchase.length; i++) {
                    if (i != 0) temp.append(separator);
                    temp.append(purchase[i]);
                }

                printWriter.println(temp.toString());
            }
            System.out.println("Purchases were saved!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadFile() {
        File save = new File(locationOfSave);
        try {
            final Scanner scanner = new Scanner(save);

            while (scanner.hasNextLine()) {
                String[] tempLine = scanner.nextLine().split(separator);

                if (tempLine.length == 0) continue;
                if (tempLine.length == 1) money = Double.parseDouble(tempLine[0]);
                else purchases.add(tempLine);
            }

            System.out.println("Purchases were loaded!");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file!");
        }
    }

    private static void analyze() {
        int option = -1;

        while ((option = menuAnalyze()) != 4) {
            MultiMap copyPurchases = new MultiMap();
            int type = -1;
            switch (option) {
                case 1:
                    for (String[] purchase : purchases) {
                        copyPurchases.put(Double.parseDouble(purchase[1]), String.format("%s $%s", purchase[0], parseDouble(purchase[1])));
                    }
                    break;
                case 2:
                    System.out.println("Types:");
                    String[] total = {
                        totalOf(0),
                        totalOf(1),
                        totalOf(2),
                        totalOf(3),
                        totalOf(4)
                    };
                    copyPurchases.put(Double.parseDouble(total[1]), String.format("Food - $%s\n", total[1]));
                    copyPurchases.put(Double.parseDouble(total[2]), String.format("Clothes - $%s\n", total[2]));
                    copyPurchases.put(Double.parseDouble(total[3]), String.format("Entertainment - $%s\n", total[3]));
                    copyPurchases.put(Double.parseDouble(total[4]), String.format("Other - $%s\n", total[4]));

                    for (Double copyPurchase : copyPurchases.keySet()) {
                        for (String cp : copyPurchases.get(copyPurchase)) {
                            System.out.print(cp);
                        }
                    }

                    System.out.printf("Total sum: $%s\n\n", total[0]);
                    continue;
                case 3:
                    type = menuPurchase();

                    for (String[] purchase : purchases) {
                        if (Integer.parseInt(purchase[2]) == type) copyPurchases.put(Double.parseDouble(purchase[1]), String.format("%s $%s", purchase[0], parseDouble(purchase[1])));
                    }
                    break;
            }
            String typeName = "";

            switch (type) {
                case -1:
                    typeName = "All";
                    break;
                case 1:
                    typeName = "Food";
                    break;
                case 2:
                    typeName = "Clothes";
                    break;
                case 3:
                    typeName = "Entertainment";
                    break;
                case 4:
                    typeName = "Other";
                    break;
            }

            if (purchases.isEmpty()) {
                System.out.println("Purchase list is empty!\n");
                continue;
            } else {
                System.out.printf("%s:\n", typeName);
            }

            double total = 0.0;

            for (Double copyPurchase : copyPurchases.keySet()) {
                for (String cp : copyPurchases.get(copyPurchase)) {
                    System.out.println(cp);
                    total += copyPurchase;
                }
            }

            System.out.printf("Total: $%.2f\n\n", total);
        }
    }

    private static String totalOf (int option) {
        double total = 0.0;

        for (String[] purchase : purchases) {
            if (option == 0) {
                total += Double.parseDouble(purchase[1]);
                continue;
            }

            if (Integer.parseInt(purchase[2]) != option) continue;

            total += Double.parseDouble(purchase[1]);
        }

        return total == 0 ? "0" : parseDouble(String.format("%.2f", total));
    }
}
