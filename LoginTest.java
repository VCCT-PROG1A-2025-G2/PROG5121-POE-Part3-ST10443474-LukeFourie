/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author luke
 */


public class LoginTest {

    @Test
    public void testCorrectUsername() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(login.checkUserName(), "Username should be valid if it contains '_' and <= 5 characters");
    }

    @Test
    public void testIncorrectUsername_NoUnderscore() {
        Login login = new Login("kyle1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertFalse(login.checkUserName(), "Username without underscore should be invalid");
    }

    @Test
    public void testIncorrectUsername_TooLong() {
        Login login = new Login("kyl_123", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertFalse(login.checkUserName(), "Username longer than 5 characters should be invalid");
    }

    @Test
    public void testCorrectPassword() {
        Login login = new Login("kyl_1", "Abc@1234", "+27838968976", "Kyle", "Smith");
        assertTrue(login.checkPasswordComplexity(), "Password meets complexity requirements");
    }

    @Test
    public void testIncorrectPassword_NoUppercase() {
        Login login = new Login("kyl_1", "abc@1234", "+27838968976", "Kyle", "Smith");
        assertFalse(login.checkPasswordComplexity(), "Password without uppercase should be invalid");
    }

    @Test
    public void testIncorrectPassword_NoNumber() {
        Login login = new Login("kyl_1", "Abc@defg", "+27838968976", "Kyle", "Smith");
        assertFalse(login.checkPasswordComplexity(), "Password without number should be invalid");
    }

    @Test
    public void testIncorrectPassword_NoSpecialChar() {
        Login login = new Login("kyl_1", "Abc12345", "+27838968976", "Kyle", "Smith");
        assertFalse(login.checkPasswordComplexity(), "Password without special character should be invalid");
    }

    @Test
    public void testCorrectCellNumber() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(login.checkCellPhoneNumber(), "Cell number format is valid");
    }

    @Test
    public void testIncorrectCellNumber_InvalidFormat() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "0831234567", "Kyle", "Smith");
        assertFalse(login.checkCellPhoneNumber(), "Number not starting with +27 should be invalid");
    }

    @Test
    public void testRegistrationSuccess() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals("User successfully registered.", login.registerUser());
    }

    @Test
    public void testRegistrationFailure() {
        Login login = new Login("kyle!", "simple", "083", "Kyle", "Smith");
        assertEquals("Registration failed due to input errors.", login.registerUser());
    }

    @Test
    public void testLoginSuccessful() {
        Login login = new Login("user_1", "Pass@123", "+27830000000", "User", "One");
        assertTrue(login.loginUser("user_1", "Pass@123"), "Login should succeed with correct credentials");
    }

    @Test
    public void testLoginFailed() {
        Login login = new Login("user_1", "Pass@123", "+27830000000", "User", "One");
        assertFalse(login.loginUser("wrong", "wrong"), "Login should fail with wrong credentials");
    }

    @Test
    public void testReturnLoginStatus_Success() {
        Login login = new Login("luc_1", "Cheese@1", "+27831112222", "Luke", "Skywalker");
        String status = login.returnLoginStatus("luc_1", "Cheese@1");
        assertEquals("Welcome Luke, Skywalker it is great to see you again.", status);
    }

    @Test
    public void testReturnLoginStatus_Failure() {
        Login login = new Login("luc_1", "Cheese@1", "+27831112222", "Luke", "Skywalker");
        String status = login.returnLoginStatus("wrong", "wrong");
        assertEquals("Username or password incorrect, please try again.", status);
    }
}
