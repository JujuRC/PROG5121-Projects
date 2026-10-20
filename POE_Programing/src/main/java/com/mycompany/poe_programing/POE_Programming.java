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

    // --- MAIN APPLICATION CLASS ---
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
                    System.out.print("Choose an option: ");

                    String input = scanner.nextLine();

                    try {
                        choice = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter 1, 2, or 3.");
                        continue;
                    }

                    switch (choice) {
                        case 1:
                            System.out.print("How many messages would you like to send? ");
                             int numMessagesToSend = scanner.nextInt();
                             scanner.nextLine();

                          //Loop for number of messages
                             for (int i = 0; i < numMessagesToSend; i++) {
                             System.out.println("\n--- Message " + (i + 1) + " of " + numMessagesToSend + " ---");
                        
                            System.out.print("Enter recipient cell number: ");
                            String recipient = scanner.nextLine();

                            System.out.print("Enter your message: ");
                            String messageText = scanner.nextLine();

                            Messages message = new Messages(
                                messageNumber, recipient, messageText
                            );

                            if (messageText.length() > 250) {
                                System.out.println(
                                    "Message exceeds the maximum of 250 characters."
                                );
                                break;
                            }

                            System.out.println(
                                message.SentMessage(scanner)
                            );

                            messageNumber++;
                            break;
                             }
                        case 2:
                            System.out.println("Coming soon.");
                            break;

                        case 3:
                            System.out.println("Goodbye!");
                            break;

                        default:
                            System.out.println(
                                "Invalid option. Please choose 1, 2, or 3."
                            );
                    }
                }
            }
        }

        scanner.close();
    }
}