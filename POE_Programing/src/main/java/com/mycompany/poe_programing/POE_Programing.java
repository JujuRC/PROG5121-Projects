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
        

public class POE_Programing {

    //Validates the username according to the rules
  public static boolean bCheckUsername(String sUsername){
      if (sUsername == null) {
          return false;
      }
      
      boolean bUnderscore = sUsername.contains("_");
      boolean bValidLength = sUsername.length() <= 5;
      return bUnderscore && bValidLength;
  }
  
  //Validates the password according to the rules
public static boolean bCheckPassword(String sPassword) {
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

public static boolean bCheckPhoneNo (String sPhoneNo) {
    if (sPhoneNo == null) {
        return false;
    }
    
    /* Reference:
    UIBakery.2015."Phone number regex Java".[Online]. Available at:https://uibakery.io/regex-library/phone-number-java[Accessed: 28 September 2026].
    This regex checks for an internation code followed by up to 10 digits.
    */
    
    String regex = ("^\\+?[1-9][0-9]{7,14}$");
    
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(sPhoneNo);
    return sPhoneNo.matches(regex);

}
    

public static void main(String[] args) { 
    //Initiating scanner
      Scanner scanner = new Scanner(System.in);
          
       //Prompting user for username to capture and validate 
          System.out.print("Enter your username: ");
          String sUsername = scanner.nextLine();
          
          if (bCheckUsername(sUsername)) {
              System.out.println("Username successfully captured");
          } else {
              System.out.println("Username is not correctly formatted: please ensure that your username contains an underscore and is nomore than five characters in length.");
          }
          
       //Prompting user for Password to capture and validate 
          System.out.println("Enter your password: ");
          String sPassword = scanner.nextLine();
          
          if (bCheckPassword(sPassword)) {
              System.out.println("Password succcessfully captured.");
          } else {
              System.out.println("Password is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number , and a special character.");
          } 
          
       //Prompting user for cell phone number to capture and validate  
          System.out.println("Enter cell phone number with international code.");
          String sPhoneNo = scanner.nextLine();
          
          if (bCheckPhoneNo(sPhoneNo)) {
              System.out.println("Cell phone number successfully added.");
          } else { 
              System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
          }
          //System.out.println("NutterButter");
          scanner.close();
}
}
