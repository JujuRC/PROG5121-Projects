/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe_programing;

/**
 *
 * @author justi
 */

import java.util.Scanner;

public class Programming_POE{    

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
            System.out.println("--- LOGIN SECTION ---");
            System.out.print("Enter username to login: ");
            String loginUser = scanner.nextLine();

            System.out.print("Enter password to login: ");
            String loginPass = scanner.nextLine();

            boolean success = userAccount.bLoginUser(loginUser, loginPass);
            System.out.println(userAccount.returnLoginStatus(success));
        }
        
        System.out.println("NutterButter");
        scanner.close();
    }
}