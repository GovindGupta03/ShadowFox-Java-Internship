import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculatorApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);

    public static void main(String[] args) {

        while (true) {
            printMenu();

            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid choice. Numbers only.");
                sc.next(); // clear buffer
                continue;
            }

            try {
                switch (choice) {
                    case 1 -> basicOperations();
                    case 2 -> scientificOperations();
                    case 3 -> temperatureConversion();
                    case 4 -> currencyConversion();
                    case 5 -> {
                        System.out.println("✅ Exiting calculator. Good choice.");
                        return;
                    }
                    default -> System.out.println("❌ Invalid option.");
                }
            } catch (ArithmeticException e) {
                System.out.println("❌ Math error: " + e.getMessage());
            }

            System.out.print("\nContinue? (y/n): ");
            if (!sc.next().equalsIgnoreCase("y")) {
                System.out.println("👋 Done.");
                break;
            }
        }
    }

    // ---------------- MENU ----------------
    private static void printMenu() {
        System.out.println("\n====== CALCULATOR ======");
        System.out.println("1. Basic Arithmetic");
        System.out.println("2. Scientific Operations");
        System.out.println("3. Temperature Conversion");
        System.out.println("4. Currency Conversion");
        System.out.println("5. Exit");
        System.out.print("Choose option: ");
    }

    // ---------------- BASIC OPS ----------------
    private static void basicOperations() {
        BigDecimal a = readBigDecimal("Enter first number: ");
        BigDecimal b = readBigDecimal("Enter second number: ");

        System.out.print("Operation (+ - * /): ");
        char op = sc.next().charAt(0);

        BigDecimal result;

        switch (op) {
            case '+' -> result = a.add(b);
            case '-' -> result = a.subtract(b);
            case '*' -> result = a.multiply(b);
            case '/' -> {
                if (b.compareTo(BigDecimal.ZERO) == 0)
                    throw new ArithmeticException("Division by zero");
                result = a.divide(b, MC);
            }
            default -> {
                System.out.println("❌ Invalid operator");
                return;
            }
        }

        System.out.println("Result: " + result);
    }

    // ---------------- SCIENTIFIC OPS ----------------
    private static void scientificOperations() {
        System.out.println("\n1. Square Root");
        System.out.println("2. Power");
        System.out.print("Choose: ");

        int ch = sc.nextInt();

        switch (ch) {
            case 1 -> {
                BigDecimal num = readBigDecimal("Enter number: ");
                double res = Math.sqrt(num.doubleValue());
                System.out.println("Result: " + res);
            }
            case 2 -> {
                BigDecimal base = readBigDecimal("Enter base: ");
                System.out.print("Enter exponent (int): ");
                int exp = sc.nextInt();
                System.out.println("Result: " + base.pow(exp, MC));
            }
            default -> System.out.println("❌ Invalid option");
        }
    }

    // ---------------- TEMP CONVERSION ----------------
    private static void temperatureConversion() {
        System.out.println("\n1. Celsius → Fahrenheit");
        System.out.println("2. Fahrenheit → Celsius");
        System.out.print("Choose: ");

        int ch = sc.nextInt();
        BigDecimal temp = readBigDecimal("Enter temperature: ");

        BigDecimal result;

        if (ch == 1) {
            result = temp.multiply(BigDecimal.valueOf(9))
                    .divide(BigDecimal.valueOf(5), MC)
                    .add(BigDecimal.valueOf(32));
        } else if (ch == 2) {
            result = temp.subtract(BigDecimal.valueOf(32))
                    .multiply(BigDecimal.valueOf(5))
                    .divide(BigDecimal.valueOf(9), MC);
        } else {
            System.out.println("❌ Invalid option");
            return;
        }

        System.out.println("Converted value: " + result);
    }

    // --------- CURRENCY ---------
    private static void currencyConversion() {
        BigDecimal USD_TO_INR = BigDecimal.valueOf(83.0);

        System.out.println("\n1. USD → INR");
        System.out.println("2. INR → USD");
        System.out.print("Choose: ");

        int ch = sc.nextInt();
        BigDecimal amount = readBigDecimal("Enter amount: ");

        BigDecimal result;

        if (ch == 1) {
            result = amount.multiply(USD_TO_INR);
        } else if (ch == 2) {
            result = amount.divide(USD_TO_INR, MC);
        } else {
            System.out.println("❌ Invalid option");
            return;
        }

        System.out.println("Converted amount: " + result);
    }

    // ---------------- SAFE INPUT ----------------
    private static BigDecimal readBigDecimal(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return sc.nextBigDecimal();
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid number. Try again.");
                sc.next();
            }
        }
    }
}
