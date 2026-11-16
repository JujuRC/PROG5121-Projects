/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe_programing;

/**
 *
 * @author justi
 */


import java.util.Scanner;

public class POE_Programming{    
   
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login userAccount = new Login();

        System.out.print("Enter your first name: ");
        userAccount.setFirstName(scanner.nextLine());

        System.out.print("Enter your last name: ");
        userAccount.setLastName(scanner.nextLine());

        System.out.print("Enter your username: ");
        String regUsername = scanner.nextLine();

        System.out.print("Enter your password: ");
        String regPassword = scanner.nextLine();

        System.out.print("Enter your cell phone number using your international code: ");
        String regPhoneNo = scanner.nextLine();

        // Process Registration
        String registrationResult = userAccount.registerUser(regUsername, regPassword, regPhoneNo);
        System.out.println(registrationResult);
        
        // Only prompt login if registration was successful
        if (registrationResult.contains("successfully")) {
            System.out.println("--- LOGIN ---");

            System.out.print("Enter username to login: ");
            String loginUser = scanner.nextLine();

            System.out.print("Enter password to login: ");
            String loginPass = scanner.nextLine();

            boolean success = userAccount.bLoginUser(loginUser, loginPass);
            System.out.println(userAccount.returnLoginStatus(success));

            // Show menu only after successful login
            if (success) {
                int choice = 0;
                int messageNumber = 1;

                while (choice != 3) {
                    System.out.println("\n--- Welcome to QuikChat ---");
                    System.out.println("1. Send a Message");
                    System.out.println("2. Show Recently Sent Messages");
                    System.out.println("3. Quit");
                    System.out.println("4. Stored Messages");
                    System.out.print("Choose an option: ");

                    String input = scanner.nextLine();

                    try {
                        choice = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter 1, 2, 3 or 4.");
                        continue;
                    }

   switch (choice) {
    case 1:
        int numMessagesToSend;
        try {
            System.out.print("How many messages would you like to send? ");
            numMessagesToSend = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            break;
        }

        for (int i = 0; i < numMessagesToSend; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + numMessagesToSend + " ---");

            System.out.print("Enter recipient cell number: ");
            String recipient = scanner.nextLine();

            System.out.print("Enter your message: ");
            String messageText = scanner.nextLine();

            Messages message = new Messages(messageNumber, recipient, messageText);

            String cellCheck = message.checkRecipientCell();
            System.out.println(cellCheck);
            if (!cellCheck.contains("successfully")) {
                continue;
            }

            String lengthCheck = message.validateMessageLength();
            System.out.println(lengthCheck);
            if (!lengthCheck.equals("Message ready to send.")) {
                continue;
            }

           String result = message.SentMessage(scanner);
           System.out.println(result);
               if (result.equals("Message successfully sent.")) {
           System.out.println(message.printMessages());
            }
            messageNumber++;
        }
        break;

    case 2:
        System.out.println("Coming soon.");
        break;

    case 3:
        System.out.println("Total messages sent: " + Messages.returnTotalMessagess());
        System.out.println("Goodbye!");
    break;
    
    case 4:
        System.out.println("\n--- Stored Messages ---");
        Messages.loadStoredMessageFromJson("stored_messages.json");
        System.out.println("a. Display sender and recipient of all stored messages");
        System.out.print("Choose an option: ");
        String subChoice = scanner.nextLine().trim().toLowerCase();

        if (subChoice.equals("a")) {
            Messages.displaySendersAndRecipients(userAccount.getFirstName());
         } else if (subChoice.equals("b")) {    
            System.out.println("b. Display the longest stored message");
         } else if (subChoice.equals("c")) {
            System.out.print("Enter the message ID: ");
            String searchID = scanner.nextLine().trim();
            Messages.searchByMessageID(searchID);
         } else if (subChoice.equals("d")) {
            System.out.print("Enter the recipient's cell number: ");
            String searchRecipient = scanner.nextLine().trim();
            Messages.searchByRecipient(searchRecipient);
         } else if (subChoice.equals("e")) {
            System.out.print("Enter the message hash: ");
            String deleteHash = scanner.nextLine().trim();
            Messages.deleteByHash(deleteHash);
         } else if (subChoice.equals("f")) {
            Messages.displayStoredReport(userAccount.getFirstName());
        } else {
          System.out.println("Invalid option.");
            }
    break;
    default:
        System.out.println("Invalid option. Please choose 1, 2, or 3.");
}
                }}
        scanner.close();
    }
    }
}