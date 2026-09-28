/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package LoginTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author justi
 */

public class LoginTest {

    // --- TESTS FOR USERNAME VALIDATION ---
    @Test
    public void testUsernameCorrectlyFormatted() {
        Login login = new Login();
        boolean result = login.bCheckUsername("kyl_1");
        assertTrue("Username should be valid", result);
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login login = new Login();
        boolean result = login.bCheckUsername("kyle!!!!!!!");
        assertFalse("Username should be invalid due to length/format", result);
    }

    // --- TESTS FOR PASSWORD COMPLEXITY ---
    @Test
    public void testPasswordMeetsComplexity() {
        Login login = new Login();
        boolean result = login.bCheckPasswordComplexity("Ch&&sec@ke99!");
        assertTrue("Password should meet complexity requirements", result);
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Login login = new Login();
        boolean result = login.bCheckPasswordComplexity("password");
        assertFalse("Password should fail complexity check", result);
    }

    // --- TESTS FOR PHONE NUMBER VALIDATION ---
    @Test
    public void testPhoneNumCorrectlyFormatted() {
        Login login = new Login();
        boolean result = login.bCheckPhoneNo("+27838968976");
        assertTrue("Phone number should be valid with international code", result);
    }

    @Test
    public void testPhoneNumIncorrectlyFormatted() {
        Login login = new Login();
        boolean result = login.bCheckPhoneNo("08966553");
        assertFalse("Phone number should fail without international code/length", result);
    }

    // --- TESTS FOR LOGIN FUNCTIONALITY ---
    @Test
    public void testLoginSuccessful() {
        Login login = new Login();
        // Register first so we have stored credentials
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        boolean result = login.bLoginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue("Login should succeed with correct credentials", result);
    }

    @Test
    public void testLoginFailed() {
        Login login = new Login();
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        boolean result = login.bLoginUser("kyl_1", "WrongPassword!");
        assertFalse("Login should fail with incorrect credentials", result);
    }

    // --- TESTS FOR REGISTRATION MESSAGING ---
    @Test
    public void testRegisterUserSuccess() {
        Login login = new Login();
        String message = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("The two above conditions have been met, and the user has been registered successfully.", message);
    }
}