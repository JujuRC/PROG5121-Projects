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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again. ";
        }
        if (!sRecipient.startsWith("+")) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again. ";
        }
        return "Cell phone number successfully captured. ";
    }

    
     //Checks if the message text is more than 250 characters.
    public String validateMessageLength() {
        if (this.sMessageText != null && this.sMessageText.length() > 250) {
            int len = sMessageText.length() - 250;
            return "Message exceeds 250 characters by " + len +",please reduce the size. ";
        }
        return "Message ready to send.";
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
                return "Message successfully sent.";

            case 2:
                return "Press 0 to delete the message.";

            case 3:
                storeMessage();
                return "Message successfully stored.";

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
    
    // Escapes special characters so text is safe to place in JSON.
    private String escapeJson(String text) {
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            switch (c) {
                case '"':
                    result.append("\\\"");
                    break;
                case '\\':
                    result.append("\\\\");
                    break;
                case '\n':
                    result.append("\\n");
                    break;
                case '\r':
                    result.append("\\r");
                    break;
                case '\t':
                    result.append("\\t");
                    break;
                case '\b':
                    result.append("\\b");
                    break;
                case '\f':
                    result.append("\\f");
                    break;
                default:
                    if (c < 0x20) {
                        result.append(String.format("\\u%04x", (int) c));
                    } else {
                        result.append(c);
                    }
                    break;
            }
        }

        return result.toString();
    }

    // Stores messages in a valid JSON array.
    public void storeMessage() {
        String jsonPayload =
            "{\n" +
            "  \"messageID\": \"" + escapeJson(sMessageID) + "\",\n" +
            "  \"messageNumber\": " + iMessageN0 + ",\n" +
            "  \"recipient\": \"" + escapeJson(sRecipient) + "\",\n" +
            "  \"message\": \"" + escapeJson(sMessageText) + "\",\n" +
            "  \"messageHash\": \"" + escapeJson(sMessageHash) + "\"\n" +
            "}";

        Path path = Paths.get("stored_messages.json");

        try {
            String content = "[]";

            if (Files.exists(path)) {
                content = new String(
                    Files.readAllBytes(path),
                    StandardCharsets.UTF_8
                ).trim();

                if (content.isEmpty()) {
                    content = "[]";
                }
            }

            // Check that the existing file is a JSON array.
            if (!content.startsWith("[") || !content.endsWith("]")) {
                throw new IOException(
                    "The existing file is not a valid JSON array."
                );
            }

            // Remove the opening and closing array brackets.
            String existingMessages =
                content.substring(1, content.length() - 1).trim();

            String updatedContent;

            if (existingMessages.isEmpty()) {
                updatedContent = "[\n" + jsonPayload + "\n]";
            } else {
                updatedContent =
                    "[\n" + existingMessages + ",\n" +
                    jsonPayload + "\n]";
            }

            Files.write(
                path,
                updatedContent.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println(
                "Message saved to stored_messages.json"
            );

        } catch (IOException e) {
            System.err.println(
                "Error saving message: " + e.getMessage()
            );
        }
    }
    
    // Getters
    public String getMessageID() { return sMessageID; }
    public String getMessageHash() { return sMessageHash; }
}

