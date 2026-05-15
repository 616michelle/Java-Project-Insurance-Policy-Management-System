
import java.util.Scanner;

public class InsureRight {

    static Client[] clients = new Client[100]; //max no. of clients is 100
    static int clientCount = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to InsureRight Policy Management System");

        int choice = -1;

        while (choice != 6) { //keeps looping unless option 6 is picked
            printMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        registerClient();
                        break;
                    case 2:
                        addPolicyToClient();
                        break;
                    case 3:
                        removePolicyFromClient();
                        break;
                    case 4:
                        printClientDetails();
                        break;
                    case 5:
                        printAllClients();
                        break;
                    case 6:
                        System.out.println("Thank you for using InsureRight");
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Exception: java.lang.NumberFormatException");
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Prints the menu
    static void printMenu() {
        System.out.println("\n--- InsureRight Menu ---");
        System.out.println("1. Register a new client");
        System.out.println("2. Add a policy to a client");
        System.out.println("3. Remove a policy from a client");
        System.out.println("4. Print a client's details");
        System.out.println("5. Print all clients and their policies");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    // Finds a client by their ID
    static Client findClient(String clientId) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getClientId().equals(clientId)) {
                return clients[i];
            }
        }
        return null;
    }

    // Option 1 - Register a new client
    static void registerClient() {
        try {
            System.out.print("Enter client ID (format CL-XXXX): ");
            String clientId = scanner.nextLine().trim();

            if (!clientId.matches("CL-\\d{4}")) {
                System.out.println("Invalid client ID format. Must be CL-XXXX where X is a digit.");
                return;
            }

            if (findClient(clientId) != null) {
                System.out.println("A client with ID " + clientId + " already exists.");
                return;
            }

            System.out.print("Enter client name: ");
            String clientName = scanner.nextLine().trim();

            double income = readPositiveDouble("Enter monthly income: ");

            Client client = new Client(clientId, clientName, income);
            clients[clientCount] = client;
            clientCount++;
            System.out.println("Client registered successfully: " + client);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Returning to menu.");
        }
    }

    // Option 2 - Add a policy to a client
    static void addPolicyToClient() {
        try {
            System.out.print("Enter client ID: ");
            String clientId = scanner.nextLine().trim();

            Client client = findClient(clientId);
            if (client == null) {
                System.out.println("Client not found.");
                return;
            }

            System.out.println("Select policy type:");
            System.out.println("1. Health");
            System.out.println("2. Motor");
            System.out.println("3. Home");
            System.out.print("Enter choice: ");

            int policyType = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter policy ID (format PL-XXXX): ");
            String policyId = scanner.nextLine().trim();

            System.out.print("Enter provider name: ");
            String provider = scanner.nextLine().trim();

            double coverage = readPositiveDouble("Enter coverage amount: ");

            Policy policy = null;

            if (policyType == 1) {
                int age = readPositiveInt("Enter age: ");
                System.out.print("Pre-existing condition? (true/false): ");
                boolean preExisting = Boolean.parseBoolean(scanner.nextLine().trim());
                policy = new HealthPolicy(policyId, provider, coverage, age, preExisting);

            } else if (policyType == 2) {
                double vehicleValue = readPositiveDouble("Enter vehicle value: ");
                int yearsNoClaims = readPositiveInt("Enter years no claims: ");
                policy = new MotorPolicy(policyId, provider, coverage, vehicleValue, yearsNoClaims);

            } else if (policyType == 3) {
                double sqm = readPositiveDouble("Enter property size in sqm: ");
                System.out.print("Flood risk? (true/false): ");
                boolean floodRisk = Boolean.parseBoolean(scanner.nextLine().trim());
                policy = new HomePolicy(policyId, provider, coverage, sqm, floodRisk);

            } else {
                System.out.println("Invalid policy type.");
                return;
            }

            System.out.println("Adding policy to client " + clientId + "...");
            client.addPolicy(policy);
            System.out.println("Policy added successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Exception: java.util.InputMismatchException");
            System.out.println("Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Returning to menu.");
        }
    }

    // Option 3 - Remove a policy from a client
    static void removePolicyFromClient() {
        try {
            System.out.print("Enter client ID: ");
            String clientId = scanner.nextLine().trim();

            Client client = findClient(clientId);
            if (client == null) {
                System.out.println("Client not found.");
                return;
            }

            client.printSummary();

            System.out.print("Enter policy index to remove (0-based): ");
            int index = Integer.parseInt(scanner.nextLine().trim());

            client.removePolicy(index);
            System.out.println("Policy removed successfully.");

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Invalid policy index.");
        } catch (NumberFormatException e) {
            System.out.println("Exception: java.lang.NumberFormatException");
            System.out.println("Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Returning to menu.");
        }
    }

    // Option 4 - Print a single client's details
    static void printClientDetails() {
        int clientsProcessed = 0;
        try {
            System.out.print("Enter client ID: ");
            String clientId = scanner.nextLine().trim();

            Client client = findClient(clientId);
            if (client == null) {
                System.out.println("Client not found.");
                return;
            }

            client.printSummary();
            clientsProcessed++;

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            System.out.println("Total clients processed in this report: " + clientsProcessed);
        }
    }

    // Option 5 - Print all clients
    static void printAllClients() {
        int clientsProcessed = 0;
        try {
            if (clientCount == 0) {
                System.out.println("No clients registered.");
                return;
            }

            for (int i = 0; i < clientCount; i++) {
                clients[i].printSummary(); //prints each summary
                System.out.println();
                clientsProcessed++;
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            System.out.println("Total clients processed in this report: " + clientsProcessed);
        }
    }

    // Helper methodreads a positive double with error handling
    static double readPositiveDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value < 0) {
                    System.out.println("Exception: Coverage rule check failed: Value must be positive.");
                    System.out.println("Please try again.");
                } else if (value == 0) {
                    System.out.println("Exception: Value cannot be zero.");
                    System.out.println("Please try again.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Exception: java.util.InputMismatchException");
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Helper method - reads a positive int with error handling
    static int readPositiveInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value < 0) {
                    System.out.println("Exception: Value cannot be negative.");
                    System.out.println("Please try again.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Exception: java.lang.NumberFormatException");
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
