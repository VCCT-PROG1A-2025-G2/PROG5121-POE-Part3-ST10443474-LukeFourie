/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp;

/**
 *
 * @author luke
 */

public class Main {

    public static void main(String[] args) {
        // ----- Test: User Registration -----
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");

        System.out.println("Registration Status: " + login.registerUser());

        // ----- Test: Login -----
        String loginStatus = login.returnLoginStatus("kyl_1", "Ch&&sec@ke99!");
        System.out.println(loginStatus);

        // ----- Test: Creating and printing a message -----
        Message message = new Message("+27123456789", "Hello, this is a message from Kyle.", 1);

        if (message.sentMessage().equals("Message sent")) {
            System.out.println("Message successfully created:");
            System.out.println(message.printMessage());
        } else {
            System.out.println("Message failed to send (too long).");
        }

        // ----- Test: Store message -----
        message.storeMessage();
        System.out.println("Message stored in JSON file.");
    }
}
