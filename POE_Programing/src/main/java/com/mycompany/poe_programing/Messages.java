/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe_programing;

/**
 *
 * @author justi
 */

import java.io.Writer;
import java.io.Reader;
import com.google.gson.Gson;
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
    private static final int MAX_MESSAGES = 100;

    private static String[] sSentM = new String[MAX_MESSAGES];
    private static String[] sDisregardedM = new String[MAX_MESSAGES];
    private static String[] sMHash = new String[MAX_MESSAGES];
    private static String[] sMID = new String[MAX_MESSAGES];
    private static StoredMessage[] sStoredM = new StoredMessage[0];

    private static int sentCount = 0;
    private static int disregardedCount = 0;

    // Instance attributes
    private String sMessageID;
    private int iMessageNo; 
    private String sRecipient;
    private String sMessageText;
    private String sMessageHash;

    public Messages (int messNo, String recipient, String messText) {
       this.iMessageNo =messNo;
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

     //Checks if the message ID is ten characters long.
  public boolean checkMessageID() {
    return this.sMessageID != null
            && this.sMessageID.length() == 10;
}
  
  //Checks if the recieptents number is correct
    public String checkRecipientCell() {
    if (sRecipient != null && sRecipient.matches("^\\+[1-9][0-9]{7,14}$")) {
        return "Cell phone number successfully captured. ";
    }
    return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again. ";
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
        String rawHash = firstTwoID + ":" + iMessageNo + ":" + firstWord + lastWord;
        return rawHash.toUpperCase();
    }

    // Asks the user to send, disregard, or store the message.
    public String SentMessage(Scanner scanner) {
        System.out.println("\nChoose an option for the message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Enter choice (1-3): ");

        int choice;
            try {
              choice = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
            return "Invalid choice selected.";
                }

        switch (choice) {
         case 1:
            totalMessagesSent++;
            if (sentCount < MAX_MESSAGES) {
            sSentM[sentCount] = sMessageText;
            sMHash[sentCount] = sMessageHash;
            sMID[sentCount] = sMessageID;
            sentCount++;
            }
                return "Message successfully sent.";

         case 2:
            if (disregardedCount < MAX_MESSAGES) {
                sDisregardedM[disregardedCount] = sMessageText;
                disregardedCount++;
                }
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
 
    public static void displaySendersAndRecipients(String sender) {
    if (sStoredM.length == 0) {
        System.out.println("No stored messages.");
        return;
    }

    for (int i = 0; i < sStoredM.length; i++) {
        System.out.println("Message " + (i + 1)
                + " | Sender: " + sender
                + " | Recipient: " + sStoredM[i].recipient);
    }
}
    
    public static void displayLongestMessage() {
    if (sStoredM.length == 0) {
        System.out.println("No stored messages.");
        return;
    }

    StoredMessage longest = sStoredM[0];

    for (int i = 1; i < sStoredM.length; i++) {
        if (sStoredM[i].message.length() > longest.message.length()) {
            longest = sStoredM[i];
        }
    }

    System.out.println("Longest stored message (" + longest.message.length() + " characters):");
    System.out.println(longest.message);
}
   
    public static void displayStoredReport(String sender) {
    if (sStoredM.length == 0) {
        System.out.println("No stored messages.");
        return;
    }

    System.out.println("\n===== STORED MESSAGES REPORT =====");

    for (int i = 0; i < sStoredM.length; i++) {
        System.out.println("Message " + (i + 1) + " of " + sStoredM.length);
        System.out.println("Sender:         " + sender);
        System.out.println("Message ID:     " + sStoredM[i].messageID);
        System.out.println("Message Number: " + sStoredM[i].messageNumber);
        System.out.println("Message Hash:   " + sStoredM[i].messageHash);
        System.out.println("Recipient:      " + sStoredM[i].recipient);
        System.out.println("Message:        " + sStoredM[i].message);
        System.out.println("----------------------------------");
    }

    System.out.println("Total stored messages: " + sStoredM.length);
}
    
    public static void searchByRecipient(String recipient) {
    boolean found = false;

    for (int i = 0; i < sStoredM.length; i++) {
        if (sStoredM[i].recipient.equals(recipient)) {
            System.out.println("Message: " + sStoredM[i].message);
            found = true;
        }
    }

    if (!found) {
        System.out.println("No stored messages found for that recipient.");
    }
}
 
    public static void searchByMessageID(String id) {
    for (int i = 0; i < sStoredM.length; i++) {
        if (sStoredM[i].messageID.equals(id)) {
            System.out.println("Recipient: " + sStoredM[i].recipient);
            System.out.println("Message:   " + sStoredM[i].message);
            return;
        }
    }
    System.out.println("No stored message found with that ID.");
  }
    
    
    public static void loadStoredMessageFromJson(String filePath) {
    sStoredM = new StoredMessage[0];
    Path path = Paths.get(filePath);

    if (!Files.exists(path)) {
        return;
    }

    try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
        StoredMessage[] loaded = new Gson().fromJson(reader, StoredMessage[].class);

        if (loaded != null) {
            sStoredM = loaded;
        }
    } catch (IOException e) {
        System.out.println("Error reading the JSON file: " + e.getMessage());
    }
}
    public static void deleteByHash(String hash) {
    int index = -1;

    // Find the position of the message with this hash
    for (int i = 0; i < sStoredM.length; i++) {
        if (sStoredM[i].messageHash.equalsIgnoreCase(hash)) {
            index = i;
            break;
        }
    }

    if (index == -1) {
        System.out.println("No stored message found with that hash.");
        return;
    }

    // Build a smaller array that skips the deleted message
    StoredMessage[] updated = new StoredMessage[sStoredM.length - 1];
    int pos = 0;
    for (int i = 0; i < sStoredM.length; i++) {
        if (i != index) {
            updated[pos] = sStoredM[i];
            pos++;
        }
    }
    String deletedText = sStoredM[index].message;
    sStoredM = updated;

    // Rewrite the JSON file so the deletion is permanent
    try (Writer writer = Files.newBufferedWriter(Paths.get("stored_messages.json"), StandardCharsets.UTF_8)) {
        new Gson().toJson(sStoredM, writer);
        System.out.println("Message \"" + deletedText + "\" successfully deleted.");
    } catch (IOException e) {
        System.out.println("Error updating the JSON file: " + e.getMessage());
    }
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
            "  \"messageNumber\": " + iMessageNo + ",\n" +
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

