/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import com.mycompany.poe_programing.Messages;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author justi
 */
public class MessagesTest {
    
   // --- TESTS FOR MESSAGE LENGTH VALIDATION --- 
@Test
public void testIncorrectMessageLength() {

    String longtext ="Hi Mike, will you join us for dinner?".repeat(50);
    int len = longtext.length();
    Messages message = new Messages(1, "08575975889", longtext);
    String result = message.validateMessageLength();
    assertEquals("Message exceeds 250 characters by " + len +",please reduce the size. ",result
    );
}

@Test 
public void testCorrectMessageLength() {
    String longtext = "Hi Keegan, recieve the payment?";
    Messages message = new Messages(2, "+27718693002", longtext);
    String result = message.validateMessageLength();
    assertEquals("Message ready to send. ",result); 
    
}

 // --- TESTS FOR RECIPIENT NUMBER VALIDATION ---

@Test 
public void testIncorrectNumber() {
    String Num = "08575975889 ";
    Messages message = new Messages(1,Num,"Hi Keegan, recieve the payment?");
    String result = message.checkRecipientCell();
    assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again. ",result);
}

@Test 
public void testCorrectNumber() {
    String Num = "+27718693002";
    Messages message = new Messages(2,Num,"Hi Mike, will you join us for dinner?");
    String result = message.checkRecipientCell();
    assertEquals("Cell phone number successfully captured. ",result);
}
    
  // --- TESTS FOR RECIPIENT NUMBER VALIDATION ---



    
}
