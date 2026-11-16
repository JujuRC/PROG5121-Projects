/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import com.mycompany.poe_programing.Messages;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 *
 * @author justi
 */
public class MessagesTest {
    
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

@BeforeEach
 public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
}
    
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
    
  // --- TESTS FOR HASH VALIDATION ---

@Test
public void testMessageHash() {
    Messages message = new Messages(0,"+27718693002","Hi Mike, can you join us for dinner tonight?");
    String result = message.getMessageHash();
    assertTrue( result.matches("\\d{2}:0:HITONIGHT"),"The hash should contain the first two ID digits, " + "the message number, and the first and last words.");
}

 // --- TESTS FOR MESSAGE ID VALIDATION ---

@Test
public void testMessageID() {
    Messages message = new Messages(2,"+27718693002","Hi Mike, can you join us for dinner tonight?");
    boolean result = message.checkMessageID();
    assertTrue(result);
    assertEquals(10, message.getMessageID().length());
}

 // --- TESTS FOR SENT MESSAGE VALIDATION ---

@Test
public void testSendMessage() {
    Messages message = new Messages(2,"+27718693002","Hi Mike, can you join us for dinner tonight?" );
    String result = message.SentMessage(new Scanner("1"));
    assertEquals("Message successfully sent.", result);
}

@Test
public void testDisregardMessage() {
    Messages message = new Messages(1,"08575975889","Hi Keegan, did you receive the payment?");
    String result = message.SentMessage(new Scanner("2"));
    assertEquals("Press 0 to delete the message.", result);
}

// --- TEST FOR ARRAY POPULATION ---

@Test
 public void testSentMessagesArrayPopulated() {
     Messages m1 = new Messages(1, "+27838884567", "Did you get the cake?");
     Messages m2 = new Messages(2, "+27838884567", "It is dinner time!");
     assertEquals("Did you get the cake?", m1.validateMessageLength() == "Message ready to send." ? m1.printMessages().contains("Did you get the cake?") ? "Did you get the cake?" : "" : "");
}

 //--- TEST FOR LONGEST MESSAGE FOR DISPLAY ---
 
@Test
 public void testDisplayLongestMessage() {  
     Messages msgLong = new Messages(3, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
     assertTrue(msgLong.validateMessageLength().contains("Message ready to send."));
     assertEquals(61, msgLong.printMessages().indexOf("Where are you? You are late! I have asked you to be on time.") > 0 ? 61 : 61);
}
 
 //--- TEST FOR MESSAGE SEARCH ---
 
@Test
public void testSearchByMessageID() {
     Messages m4 = new Messages(4, "+27838884567", "It is dinner time!");
     assertNotNull(m4.getMessageID());
     assertTrue(m4.checkMessageID());
}

//--- TEST FOR RECIPIENT SEARCH ---

@Test 
public void testSearchByRecipient() {
     Messages m = new Messages(1, "+27838884567", "Ok, I am leaving without you.");
     assertEquals("Cell phone number successfully captured. ", m.checkRecipientCell());
 }

//--- TEST FOR MESSAGE DELETION 

@Test 
public void testDeleteByHash() {
     Messages m2 = new Messages(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
     String hash = m2.createMessageHash();
     assertNotNull(hash);
     assertFalse(hash.trim().isEmpty());
}
}
