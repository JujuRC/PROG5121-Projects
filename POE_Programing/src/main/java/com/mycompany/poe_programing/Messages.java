/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe_programing;

/**
 *
 * @author justi
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Messages {

    // Fields tracking total messages across instances
    private static int totalMessagesSent = 0;

    // Instance attributes
    private String sMessageID;
    private int iMessageN0; 
    private String sRecipient;
    private String sMessageText;
    private String sMessageHash;

    public Messages (int messNo, String recipient, String messText) {
       this.iMessageN0 =messNo;
        this.sRecipient = recipient;
        this.sMessageText = messText;
        
        // Generate 10-digit unique ID
        this.sMessageID = generateMessageID();
        
        // Create hash upon initialization
        this.sMessageHash = createMessageHash();
    }

    //Ceates a random message ID 
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

     //Checks if the message ID is shorter ten characters long.
  public boolean checkMessageID() {
    return this.sMessageID != null
            && this.sMessageID.length() == 10;
}
  
  //Checks if the recieptents number is 10 numbers long 
    public String checkRecipientCell() {
        if (sRecipient == null || sRecipient.length() > 10) {
            return "Invalid cell number: must not exceed 10 characters.";
        }
        if (!sRecipient.startsWith("+")) {
            return "Invalid cell number: must contain an international code.";
        }
        return "Cell number is valid.";
    }

    
     //Checks if the message text is more than 250 characters.
    public String validateMessageLength() {
        if (this.sMessageText != null && this.sMessageText.length() > 250) {
            return "Please enter a message of less than 250 characters.";
        }
        return "Message sent";
    }

 //Creates message hash 
    public String createMessageHash() {
        if (sMessageID == null || sMessageID.length() < 2 || sMessageText == null || sMessageText.trim().isEmpty()) {
            return " ";
        }

        //First two numbers of message ID
        String firstTwoID = sMessageID.substring(0, 2);

        //Get first and last words
        String[] words = sMessageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        //Combined and make uppercase
        String rawHash = firstTwoID + ":" + iMessageN0 + ":" + firstWord + lastWord;
        return rawHash.toUpperCase();
    }

    // Asks the user to send, disregard, or store the message.
    public String SentMessage(Scanner scanner) {
        System.out.println("\nChoose an option for the message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Enter choice (1-3): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                totalMessagesSent++;
                return "Message successfully sent";

            case 2:
                return "Press 0 to delete the message";

            case 3:
                storeMessage();
                return "Message successfully stored";

            default:
                return "Invalid choice selected.";
        }
    }

    // Displays message details in the required order
    public String printMessages() {
        return "----------------------------------------\n" +
               "Message ID:   " + sMessageID + "\n" +
               "Message Hash: " + sMessageHash+ "\n" +
               "Recipient:    " + sRecipient+ "\n" +
               "Message:      " + sMessageText + "\n" +
               "----------------------------------------";
    }

    // Returns total messages sent.
     
    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    //Custom method to store the message in JSON format to a local file.
     
    public void storeMessage() {
        String jsonPayload = String.format(
            "{\n" +
            "  \"messageID\": \"%s\",\n" +
            "  \"messageNumber\": %d,\n" +
            "  \"recipient\": \"%s\",\n" +
            "  \"message\": \"%s\",\n" +
            "  \"messageHash\": \"%s\"\n" +
            "}",
            sMessageID, iMessageN0, sRecipient, sMessageText.replace("\"", "\\\""), sMessageHash
        );

        try (FileWriter writer = new FileWriter("stored_messages.json", true)) {
            writer.write(jsonPayload + ",\n");
            System.out.println(">> Message saved to stored_messages.json");
        } catch (IOException e) {
            System.err.println("Error saving message to JSON file: " + e.getMessage());
        }
    }

    // Getters
    public String getMessageID() { return sMessageID; }
    public String getMessageHash() { return sMessageHash; }
}

