/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author luke
 */


public class QuickChat {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat");

        // Registration
        String username = JOptionPane.showInputDialog("Enter a username:");
        String password = JOptionPane.showInputDialog("Enter a password:");
        String cell = JOptionPane.showInputDialog("Enter your cell phone number (+27...):");
        String firstName = JOptionPane.showInputDialog("Enter your first name:");
        String lastName = JOptionPane.showInputDialog("Enter your last name:");

        Login user = new Login(username, password, cell, firstName, lastName);
        JOptionPane.showMessageDialog(null, user.registerUser());

        // Login
        String loginUsername = JOptionPane.showInputDialog("Enter your username to login:");
        String loginPassword = JOptionPane.showInputDialog("Enter your password to login:");

        if (!user.loginUser(loginUsername, loginPassword)) {
            JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again.");
            return;
        }

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat, " + firstName);

        int numMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you want to send?"));

        // Initialize arrays
        List<Message> sentMessages = new ArrayList<>();
        List<Message> disregardedMessages = new ArrayList<>();
        List<Message> storedMessages = new ArrayList<>();
        List<String> messageHashes = new ArrayList<>();
        List<String> messageIDs = new ArrayList<>();

        int sentCount = 0;

        while (true) {
            String option = JOptionPane.showInputDialog(
                    "Choose an option:\n1) Send Messages\n2) Show Sent Messages\n3) Longest Sent Message\n4) Search by Message ID\n5) Search by Recipient\n6) Delete by Hash\n7) Show Report\n8) Quit");

            switch (option) {
                case "1" -> {
                    if (sentCount >= numMessages) {
                        JOptionPane.showMessageDialog(null, "You've reached your message limit.");
                        break;
                    }

                    String recipient = JOptionPane.showInputDialog("Enter recipient cell number (+27...):");
                    if (!recipient.matches("^\\+27\\d{9}$")) {
                        JOptionPane.showMessageDialog(null, "Recipient number incorrectly formatted.");
                        break;
                    }

                    String messageText = JOptionPane.showInputDialog("Enter message (max 250 chars):");
                    if (messageText.length() > 250) {
                        JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
                        break;
                    }

                    Message msg = new Message(recipient, messageText, sentCount + 1);
                    String[] sendOptions = {"Send", "Disregard", "Store for Later"};
                    int sendChoice = JOptionPane.showOptionDialog(
                            null, "What would you like to do with the message?", "Send Message",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, sendOptions, sendOptions[0]);

                    switch (sendChoice) {
                        case 0 -> {
                            JOptionPane.showMessageDialog(null, msg.printMessage());
                            sentMessages.add(msg);
                            messageHashes.add(msg.createMessageHash());
                            messageIDs.add(msg.messageID);
                            sentCount++;
                        }
                        case 1 ->
                            disregardedMessages.add(msg);
                        case 2 -> {
                            storedMessages.add(msg);
                            msg.storeMessage();
                            JOptionPane.showMessageDialog(null, "Message stored for later.");
                        }
                    }
                }

                case "2" -> {
                    StringBuilder sent = new StringBuilder("Sent Messages:\n");
                    for (Message m : sentMessages) {
                        sent.append(m.printMessage()).append("\n\n");
                    }
                    JOptionPane.showMessageDialog(null, sent.isEmpty() ? "No messages sent." : sent.toString());
                }

                case "3" -> {
                    Message longest = sentMessages.stream()
                            .max(Comparator.comparingInt(m -> m.message.length()))
                            .orElse(null);
                    JOptionPane.showMessageDialog(null,
                            longest != null ? longest.printMessage() : "No messages sent.");
                }

                case "4" -> {
                    String searchID = JOptionPane.showInputDialog("Enter message ID to search:");
                    Message found = sentMessages.stream()
                            .filter(m -> m.messageID.equals(searchID))
                            .findFirst().orElse(null);
                    JOptionPane.showMessageDialog(null,
                            found != null ? found.printMessage() : "Message ID not found.");
                }

                case "5" -> {
                    String searchRecipient = JOptionPane.showInputDialog("Enter recipient number to search:");
                    List<Message> results = sentMessages.stream()
                            .filter(m -> m.recipient.equals(searchRecipient))
                            .toList();
                    if (results.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No messages found for that recipient.");
                    } else {
                        StringBuilder report = new StringBuilder("Messages to " + searchRecipient + ":\n");
                        for (Message m : results) {
                            report.append(m.printMessage()).append("\n\n");
                        }
                        JOptionPane.showMessageDialog(null, report.toString());
                    }
                }

                case "6" -> {
                    String hashToDelete = JOptionPane.showInputDialog("Enter message hash to delete:");
                    boolean removed = sentMessages.removeIf(m -> m.createMessageHash().equals(hashToDelete));
                    JOptionPane.showMessageDialog(null, removed ? "Message deleted." : "Hash not found.");
                }

                case "7" -> {
                    StringBuilder report = new StringBuilder("Full Sent Message Report:\n");
                    for (Message m : sentMessages) {
                        report.append(m.printMessage()).append("\n\n");
                    }
                    report.append("Total sent: ").append(sentMessages.size());
                    JOptionPane.showMessageDialog(null, report.toString());
                }

                case "8" -> {
                    JOptionPane.showMessageDialog(null, "Total messages sent: " + sentCount + "\nGoodbye!");
                    System.exit(0);
                }

                default ->
                    JOptionPane.showMessageDialog(null, "Invalid option. Try again.");
            }
        }
    }
}
