/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ice_task_2;
import java.util.Scanner;
/**
 *
 * @author justi
 */
public class ICE_TASK_2 {

    public static void main(String[] args) { //Declaration of variables 
        double Num1, Num2 , Sum , Multiple, Difference, Quotient, Average;
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your first number."); //prompting user for first number
        Num1 = scanner.nextDouble();
        
        System.out.print("Enter your second number.");//prompting user for second number
        Num2 = scanner.nextDouble();
        
        //Calculations
        Sum = add(Num1, Num2);
        Difference = subtract(Num1, Num2);
        Multiple = multiple(Num1, Num2);
        Quotient = divide(Num1, Num2);
        Average = average(Num1,Num2);
        if (Num2 != 0) {
            System.out.println("Quotient:" + Num1 + "Cannot divide by zero.");
        }
        
        //Printing out the calculations 
        System.out.println("Calculations using" + Num1 + "and" + Num2);
        System.out.println("Sum:" + Sum);
        System.out.println("Difference:" + Sum);
        System.out.println("Multiple:" + Sum);
        System.out.println("Quotient:" + Quotient);
        System.out.println("Average:" + Average);
       
       
        scanner.close();
    }
    //Declaration of methonds
    public static double add(double a, double b) {
        return a + b;
    }
    
    public static double subtract(double a, double b) {       
        return a - b;
    }
    
    public static double multiple(double a, double b) {
        return a * b;
    }
    
    public static double divide(double a, double b) {
        return a / b;
    }
    
    public static double average(double a, double b) {
        double total = add(a,b); // Reuses the sum method
        return (double) total / 2;
    }
     
}
