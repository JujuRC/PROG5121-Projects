/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.icetask3.ice_task_3;

/**
 *
 * @author justi
 */

import java.util.Scanner;

public class StudentMarks {

    public static void main(String[] args) {

        // Create a Scanner object to capture user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the student mark
        System.out.println("Enter Student Mark: ");
        int studentMark = scanner.nextInt();

        // Independent if statement to check for a distinction (75 or higher)
        if (studentMark >= 75) {
            System.out.println("Outstanding achievement!");
            System.out.println("You have officially qualified for a distinction.");
        }

        // If-else statement to determine whether the student passed or failed
        if (studentMark >= 50) {
            System.out.println("Result: Pass");
            System.out.println("Congratulations, you have met the requirements for this course!");
        } else {
            System.out.println("Result: Fail");
            System.out.println("Unfortunately, you did not achieve the minimum passing grade of 50.");
            System.out.println("Please contact your academic advisor regarding remediation options.");
        }

        // Close the scanner
        scanner.close();
    }
}