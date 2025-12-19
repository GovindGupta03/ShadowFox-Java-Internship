import java.util.*;
import java.util.regex.Pattern;

import javax.swing.plaf.basic.BasicBorders.MenuBarBorder;

// Contact POJO

class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Phone: " + phone +
                ", Email: " + email;
    }
}

// Main Application

public class ContactManagementSystem {

    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Contact> contacts = new ArrayList<>();

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1 -> addContact();
                case 2 -> listContacts();
                case 3 -> searchContact();
                case 4 -> editContact();
                case 5 -> deleteContact();
                case 0 -> {
                    System.out.println("Exiting... Bye.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // Menu

    private static void showMenu() {
        System.out.println("\n**** Contact Management System ****");
        System.out.println("1. Add Contact");
        System.out.println("2. List All Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Edit Contact");
        System.out.println("5. Delete Contact");
        System.out.println("0. Exit");
    }

    // CRUD Operations

    private static void addContact() {
        System.out.print("Name: ");
        String name = sc.nextLine();

        String phone;
        while (true) {
            System.out.print("Phone: ");
            phone = sc.nextLine();
            if (!phone.matches("\\d+")) {
                System.out.println("Phone must contain digits only.");
                continue;
            }
            if (isDuplicatePhone(phone)) {
                System.out.println("Contact with this phone already exists.");
                continue;
            }
            break;
        }

        String email;
        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine();
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                System.out.println("Invalid email format.");
                continue;
            }
            break;
        }

        contacts.add(new Contact(name, phone, email));
        System.out.println("Contact added successfully.");
    }

    private static void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        contacts.forEach(System.out::println);
    }

    private static void searchContact() {
        System.out.print("Enter name to search: ");
        String keyword = sc.nextLine().toLowerCase();
        boolean found = false;

        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(keyword)) {
                System.out.println(c);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching contact found.");
        }
    }

    private static void editContact() {
        System.out.print("Enter phone of contact to edit: ");
        String phone = sc.nextLine();

        for (Contact c : contacts) {
            if (c.getPhone().equals(phone)) {
                System.out.print("New Name: ");
                c.setName(sc.nextLine());

                String email;
                while (true) {
                    System.out.print("New Email: ");
                    email = sc.nextLine();
                    if (EMAIL_PATTERN.matcher(email).matches())
                        break;
                    System.out.println("Invalid email format.");
                }
                c.setEmail(email);

                System.out.println("Contact updated.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    private static void deleteContact() {
        System.out.print("Enter phone of contact to delete: ");
        String phone = sc.nextLine();

        Iterator<Contact> it = contacts.iterator();
        while (it.hasNext()) {
            if (it.next().getPhone().equals(phone)) {
                it.remove();
                System.out.println("Contact deleted.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    private static boolean isDuplicatePhone(String phone) {
        for (Contact c : contacts) {
            if (c.getPhone().equals(phone))
                return true;
        }
        return false;
    }

    private static int getIntInput(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
}
