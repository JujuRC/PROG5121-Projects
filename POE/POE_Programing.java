/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
/**
 *
 * @author justi
 */

import java.util.Scanner; 


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

//Prompting user for their information to validate it
public static void main(String[] args) { 
      try (Scanner scanner = new Scanner(System.in)) {
          System.out.print("Enter your username: ");
          String sUsername = scanner.nextLine();
          
          if (bCheckUsername(sUsername)) {
              System.out.println("Username successfully captured");
          } else {
              System.out.println("Username is not correctly formatted: please ensure that your username contains an underscore and is nomore than five characters in length.");
          }
          
          System.out.println("Enter your password: ");
          String sPassword = scanner.nextLine();
          
          if (bCheckPassword(sPassword)) {
              System.out.println("Password succcessfully captured.");
          } else {
              System.out.println("Password is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number , and a special character.");
          } }
}
}

   
 