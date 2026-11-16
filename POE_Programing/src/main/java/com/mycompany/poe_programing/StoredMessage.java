/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe_programing;

/**
 *
 * @author justi
 */
public class StoredMessage {
  
    String messageID;
    int messageNumber;
    String recipient;
    String message;
    String messageHash;

    public StoredMessage (String id, int num, String recipient, String message, String hash) {
        this.messageID = id;
        this.messageNumber = num;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = hash;
    }
}
    
