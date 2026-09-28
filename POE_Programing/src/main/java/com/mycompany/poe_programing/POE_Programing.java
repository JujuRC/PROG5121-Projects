/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe_programing;

/**
 *
 * @author justi
 */

import java.util.Scanner; 
import java.util.regex.Pattern;
import java.util.regex.Matcher;
        
class Login {
    private String sStoredUsername;
    private String sStoredPassword;
    private String sStoredPhoneNo;
    private String sFirstName;
    private String sLastName;

    // Getters and setters
    public void setFirstName(String firstName) { this.sFirstName = firstName; }
    public void setLastName(String lastName) { this.sLastName = lastName; }
    public void setStoredUsername(String storedUsername) { this.sStoredUsername = storedUsername; }
    public void setStoredPassword(String storedPassword) { this.sStoredPassword = storedPassword; }
    public void setStoredPhoneNo(String storedPhoneNo) { this.sStoredPhoneNo = storedPhoneNo; }

    public String getFirstName() { return sFirstName; }
    public String getLastName() { return sLastName; }
    public String getStoredUsername() { return sStoredUsername; }
    public String getStoredPassword() { return sStoredPassword; }

    // Validates the username method
    public boolean bCheckUsername(String sUsername){
        if (sUsername == null) return false;
        return sUsername.contains("_") && sUsername.length() <= 5;
    }

    // Validates the password method
    public boolean bCheckPasswordComplexity(String sPassword) {
        if (sPassword == null || sPassword.length() < 8) {
            return false;
        }
        
        boolean bCapital = false;
        boolean bNumber = false;
        boolean bSpecial = false;
        
        for (char c: sPassword.toCharArray()){
            if (Character.isUpperCase(c)) {
                bCapital = true;
            } else if (Character.isDigit(c)) { 
                bNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                bSpecial = true;
            }
        }
        
        return bCapital && bNumber && bSpecial;
    }

    public boolean bCheckPhoneNo(String sPhoneNo) {
        if (sPhoneNo == null) {
            return false;
        }
        
        /* Reference:
        UIBakery.2015."Phone number regex Java".[Online]. Available at:https://uibakery.io/regex-library/phone-number-java[Accessed: 28 September 2026].
        This regex checks for an international code followed by up to 10 digits.
        */
        
        String regex = ("^\\+?[1-9][0-9]{7,14}$");
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sPhoneNo);
        return sPhoneNo.matches(regex);
    }

    // Register User method
    public String registerUser(String username, String password, String phoneNo) {
        if (!bCheckUsername(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!bCheckPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!bCheckPhoneNo(phoneNo)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // Save information using the correct field names
        this.sStoredUsername = username;
        this.sStoredPassword = password;
        this.sStoredPhoneNo = phoneNo;
        
        return "The two above conditions have been met, and the user has been registered successfully.";
    }

    // Login method
    public boolean bLoginUser(String enteredUsername, String enteredPassword) {
        if (sStoredUsername == null || sStoredPassword == null) {
            return false;
        }
        return enteredUsername.equals(sStoredUsername) && enteredPassword.equals(sStoredPassword);
    }

    // Return Login Status Method
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + sFirstName + ", " + sLastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
} 

public class POE_Programing {    

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