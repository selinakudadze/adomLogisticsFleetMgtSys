import java.util.Scanner;

import static Read_Write_To_Databases.AddToDatabases.runDataEntry;
import static sort_and_search.BinarySearch.searchByRegistration;
import static utils.MainClassReader.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String continueChoice;

        do {
            System.out.println("\nADOM LOGISTICS FLEET MANAGEMENT SYSTEM");
            System.out.println("-----------------------------");
            System.out.println("1. Display All drivers");
            System.out.println("2. Get driver info (D00-)");
            System.out.println("3. Get vehicle info");
            System.out.println("4. Display vehicles by mileage");
            System.out.println("5. Display vehicles by type");
            System.out.println("6. Display orders");
            System.out.println("7. Track order by Id");
            System.out.println("8. Assign orders");
            System.out.println("9. Update order status");
            System.out.println("10. Maintain vehicles");
            System.out.println("11. Show outliers");
            System.out.println("12. Data Entry");
            System.out.println("13. Exit");

            String choice = scanner.nextLine();
            //scanner.nextLine();

            switch (choice) {
                case "1":
                    getAllDriverInfo();
                    break;
                case "2":
                    getDriverInfo(scanner);
                    break;
                case "3":
                    searchVehicleByRegistration(scanner);
                    break;
                case "4":
                    organizeVehiclesByMileage();
                    break;
                case "5":
                    organizeVehiclesByType();
                    break;
                case "6":
                    displayAllOrders();
                    break;
                case "7":
                    trackOrderById(scanner);
                    break;
                case "8":
                    assignOrders(scanner);
                    break;
                case "9":
                    updateOrderStatus(scanner);
                    break;
                case "10":
                    maintainVehicles(scanner);
                    break;
                case "11":
                    displayOutliers(scanner);
                    break;
                case "12":
                    runDataEntry();
                case "13":
                    System.out.println("\nExiting program...\nSEE YOU SOON");
                    System.out.println("----------------------------------------");
                    System.out.println("ADOM LOGISTICS FLEET MANAGEMENT SYSTEM");

                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }

            while (true) {
                System.out.print("Would you like to perform another operation? (y/n): ");
                continueChoice = scanner.nextLine().trim().toLowerCase();

                if (continueChoice.equals("y")) {
                    break;
                } else if (continueChoice.equals("n")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                } else {
                    System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
                }
            }

        } while (continueChoice.equalsIgnoreCase("y"));

        System.out.println("\nSEE YOU SOON...");
        System.out.println("----------------------------------------");
        System.out.println("ADOM LOGISTICS FLEET MANAGEMENT SYSTEM");
        scanner.close();


    }
}