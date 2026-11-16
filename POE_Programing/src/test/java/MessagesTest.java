/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import com.mycompany.poe_programing.Messages;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author justi
 */
public class MessagesTest {
    
private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
private final PrintStream originalOut = System.out;
private Messages m1, m2, m3, m4, m5;

@BeforeEach
public void setUp() throws Exception {
   Files.deleteIfExists(Paths.get("stored_messages.json"));
   resetStaticFields();
   m1 = new Messages(1, "+27834557896", "Did you get the cake?");
   m2 = new Messages(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
   m3 = new Messages(3, "+27834484567", "Yohoooo, I am at your gate.");
   m4 = new Messages(4, "0838884567", "It is dinner time !");
   m5 = new Messages(5, "+27838884567", "Ok, I am leaving without you.");
   m1.SentMessage(new Scanner("1\n")); // Flag: Sent
   m2.SentMessage(new Scanner("3\n")); // Flag: Stored
   m3.SentMessage(new Scanner("2\n")); // Flag: Disregard
   m4.SentMessage(new Scanner("1\n")); // Flag: Sent
   m5.SentMessage(new Scanner("3\n")); // Flag: Stored
   Messages.loadStoredMessageFromJson("stored_messages.json");
   System.setOut(new PrintStream(outContent));
    }

@AfterEach
public void tearDown() throws Exception {
   System.setOut(originalOut);
   Files.deleteIfExists(Paths.get("stored_messages.json"));
    }

private void resetStaticFields() throws Exception {
    Field sentArr = Messages.class.getDeclaredField("sSentM");
    sentArr.setAccessible(true);
    sentArr.set(null, new String[100]);
    Field disArr = Messages.class.getDeclaredField("sDisregardedM");
    disArr.setAccessible(true);
    disArr.set(null, new String[100]);
    Field sentCnt = Messages.class.getDeclaredField("sentCount");
    sentCnt.setAccessible(true);
    sentCnt.setInt(null, 0);
    Field disCnt = Messages.class.getDeclaredField("disregardedCount");
    disCnt.setAccessible(true);
    disCnt.setInt(null, 0);
    Field totalSent = Messages.class.getDeclaredField("totalMessagesSent");
    totalSent.setAccessible(true);
    totalSent.setInt(null, 0);
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

// --- TEST THE ARRAY POPULATION ---
    
@Test
public void testSentMessagesArrayPopulated() throws Exception {
     Field sentArrField = Messages.class.getDeclaredField("sSentM");
     sentArrField.setAccessible(true);
     String[] sSentM = (String[]) sentArrField.get(null);
     assertEquals("Did you get the cake?", sSentM[0]);
     assertEquals("It is dinner time !", sSentM[1]);
}

// --- TEST FOR LONGEST MESSAGE ---
    
@Test
public void testDisplayLongestMessage() {
     Messages.displayLongestMessage();
     String output = outContent.toString();
     assertTrue(output.contains("Where are you? You are late! I have asked you to be on time."));
}

// --- TEST FOR MESSAGE SEARCH BY ID ---
    
@Test
public void testSearchByMessageID() {
     String idToSearch = m4.getMessageID();
     Messages.searchByMessageID(m2.getMessageID());
     String output = outContent.toString();
     assertTrue(output.contains("Where are you? You are late! I have asked you to be on time."));
}

// --- TEST FOR RECIPIENT SEARCH ---
    
@Test
public void testSearchByRecipient() {
     Messages.searchByRecipient("+27838884567");
     String output = outContent.toString();
     assertTrue(output.contains("Where are you? You are late! I have asked you to be on time."));
     assertTrue(output.contains("Ok, I am leaving without you."));
}

// --- TEST FOR MESSAGE DELETION BY HASH ---
    
@Test
public void testDeleteByHash() {
     String hashToDelete = m2.getMessageHash();
     Messages.deleteByHash(hashToDelete);
     String output = outContent.toString();
     assertTrue(output.contains("Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted."));
}
}