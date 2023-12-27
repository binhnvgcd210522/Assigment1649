package Assigment1649;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static final String RESET_Color = "\u001B[0m";
    public static final String RED_Color = "\u001B[31m";
    public static final String BLUE_Color = "\u001B[34m";
    public static final String YELLOW_Color = "\u001B[33m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //An array to store created system
        MailSystem[] mailSystems = new MailSystem[10];
        int choice = 0;
        int count = 0;
        while (choice != 4) {
            showMainMenu();
            // read input
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        if (count == 0) {
                            System.out.println(RED_Color +"There are no system to show."+ RESET_Color);
                        } else {
                            System.out.println("-----Showing All System-----");
                            for (int i = 0; i < count; i++) {
                                System.out.println(mailSystems[i]);
                            }
                        }
                        break;
                    case 2:
                        if (count < 10) {
                            createSystem(mailSystems, count, scanner);
                            count++;
                        } else {
                            System.out.println(RED_Color + "Too many systems. Stop create." + RESET_Color);
                        }
                        break;
                    case 3:
                        if (count == 0) {
                            System.out.println(RED_Color + "There are no system to work with." + RESET_Color);
                            break;
                        }
                        System.out.print("Enter system name: ");
                        String name = scanner.nextLine();
                        boolean found = false;
                        //Find system
                        for (int i = 0; i < count; i++) {
                            if (mailSystems[i].systemName.equals(name)) {
                                found = true;
                                int subChoice = 0;
                                while (subChoice != 6) {
                                    showSubMenu(mailSystems[i]);
                                    if (scanner.hasNextInt()) {
                                        subChoice = scanner.nextInt();
                                        scanner.nextLine();
                                        switch (subChoice) {
                                            case 1:
                                                sendMessage(mailSystems[i], scanner);
                                                break;
                                            case 2:
                                                connectSystem(mailSystems, mailSystems[i], count);
                                                break;
                                            case 3:
                                                receiveMessage(mailSystems[i]);
                                                break;
                                            case 4:
                                                disconnectSystem(mailSystems[i]);
                                                break;
                                            case 5:
                                                mailSystems[i].checkInBox();
                                                break;
                                            case 6:
                                                System.out.println("Going Back...");
                                                break;
                                            default:
                                                System.out.println(RED_Color + "Invalid choice. Please enter a valid option." + RESET_Color);
                                                break;
                                        }
                                    } else {
                                        // Handle the case where the input is not an integer
                                        System.out.println(RED_Color + "Invalid input. Please enter a valid integer option." + RESET_Color);
                                        scanner.nextLine(); // Consume the invalid input
                                    }
                                }
                                break;
                            }
                        }
                        if (!found) System.out.println(RED_Color + "System not found." + RESET_Color);
                        break;
                    case 4:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println(RED_Color + "Invalid choice. Please enter a valid option." + RESET_Color);
                        break;
                }
            } else {
                // Handle the case where the input is not an integer
                System.out.println(RED_Color + "Invalid input. Please enter a valid integer option." + RESET_Color);
                scanner.nextLine(); // Consume the invalid input
            }
        }
        scanner.close();
    }
    static void createSystem(MailSystem[] mailSystems, int count, Scanner scanner){
        System.out.print("Input system name: ");
        String string = scanner.nextLine();
        //add to system storage
        MailSystem newSystem = new MailSystem(string);
        mailSystems[count] = newSystem;
    }
    static void sendMessage(MailSystem mailSystem, Scanner scanner) {
        System.out.print("Input message: ");
        String message = scanner.nextLine();
        mailSystem.sendMessage(message);
    }

    static void connectSystem(MailSystem[] mailSystems, MailSystem mailSystem, int count) {
        Scanner scanner = new Scanner(System.in);
        if (mailSystem.connectedSystem == null) {
            System.out.print("Enter system name you want to connect: ");
            String system = scanner.nextLine();
            for (int j = 0; j < count; j++) {
                if (mailSystems[j].systemName.equals(system)) {
                    if(mailSystems[j].connectedSystem != null){
                        System.out.println(RED_Color + "System "+ system +" is connecting to other." + RESET_Color);
                        return;
                    }
                    mailSystem.connect(mailSystems[j]);
                    return;
                }
            }
            System.out.println(RED_Color +"System "+ system+ " not found." + RESET_Color);
        } else mailSystem.checkConnect();
    }

    static void receiveMessage(MailSystem mailSystem) {

        //check if system is not connect
        if (mailSystem.connectedSystem == null) {
            mailSystem.checkConnect();
            return;
        }
        //get message and clear outbox queue
        if (mailSystem.connectedSystem.outboxEmpty()) {
            System.out.println(RED_Color + "Required system outbox queue is null." + RESET_Color);
        } else {
            mailSystem.receiveMessage(mailSystem.connectedSystem);
            System.out.println(BLUE_Color + "Success." + RESET_Color);
        }
    }

    static void disconnectSystem(MailSystem mailSystem) {
        if (mailSystem.connectedSystem != null) {
            mailSystem.disConnect(mailSystem.connectedSystem);
        } else System.out.println(RED_Color +"System is not connected yet."+ RESET_Color);
    }

    static void showSubMenu(MailSystem mailSystem) {
        System.out.println(BLUE_Color + "----- System "+ mailSystem.systemName + " ------" + RESET_Color);
        System.out.println("1) Send message");
        System.out.println("2) Connect system");
        System.out.println("3) Receive message from connected system");
        System.out.println("4) Disconnect system");
        System.out.println("5) Check inbox");
        System.out.println("6) Back");
        System.out.print(YELLOW_Color +"Input your choice: " + RESET_Color);
    }
    static void showMainMenu(){
        System.out.println(BLUE_Color +"------ Menu ------" + RESET_Color);
        System.out.println("1) Show All System");
        System.out.println("2) Create new system");
        System.out.println("3) Choose system to work with");
        System.out.println("4) Exit program");
        System.out.print(YELLOW_Color +"Input your choice: " +RESET_Color);
    }
}
