/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp;

/**
 *
 * @author luke
 */

/**
 * Handles user registration and login validation for QuickChat.
 * Validates username, password, and phone number format.
 */
public class Login {

    private final String username;
    private final String password;
    private final String phoneNumber;
    private final String firstName;
    private final String lastName;

    public Login(String username, String password, String phoneNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Checks if the username contains an underscore and is 5 characters or
     * fewer.
     * @return 
     */
    public boolean checkUserName() {
        return this.username.contains("_") && this.username.length() <= 5;
    }

    /**
     * Validates password complexity: - At least 8 characters - Contains an
     * uppercase letter, number, and special character
     * @return 
     */
    public boolean checkPasswordComplexity() {
        return this.password.length() >= 8
                && this.password.matches(".*[A-Z].*")
                && this.password.matches(".*[0-9].*")
                && this.password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    /**
     * Validates the South African phone number format (+27 followed by 9
     * digits).
     * @return 
     */
    public boolean checkCellPhoneNumber() {
        return this.phoneNumber.matches("^\\+27\\d{9}$");
    }

    /**
     * Registers the user if all validation checks pass.
     * @return 
     */
    public String registerUser() {
        if (checkUserName() && checkPasswordComplexity() && checkCellPhoneNumber()) {
            return "User successfully registered.";
        } else {
            return "Registration failed due to input errors.";
        }
    }

    /**
     * Validates user login credentials.
     * @param enteredUsername
     * @param enteredPassword
     * @return 
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    /**
     * Returns the login status message.
     * @param enteredUsername
     * @param enteredPassword
     * @return 
     */
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + this.firstName + ", " + this.lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
