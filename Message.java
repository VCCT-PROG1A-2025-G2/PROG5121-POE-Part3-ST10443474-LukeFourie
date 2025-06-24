/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 *
 * @author luke
 */


public class Message {

    public final String recipient;
    public final String message;
    public final int messageNum;
    public final String messageID;
    public final String messageHash;

    public Message(String recipient, String message, int messageNum) {
        this.recipient = recipient;
        this.message = message;
        this.messageNum = messageNum;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        int id = 1000000000 + rand.nextInt(900000000);
        return String.valueOf(id);
    }

    public boolean checkMessagesID() {
        return this.messageID != null && this.messageID.length() == 10;
    }

    public int checkRecipientCell() {
        return recipient.matches("^\\+27\\d{9}$") ? 1 : 0;
    }

    public final String createMessageHash() {
        String[] words = message.split(" ");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "";
        String lastWord = words.length > 1 ? words[words.length - 1].toUpperCase() : firstWord;
        String idPart = messageID.substring(0, 2);
        return String.format("%s:%d:%s%s", idPart, messageNum, firstWord, lastWord);
    }

    public String sentMessage() {
        return message.length() <= 250
                ? "Message sent"
                : "Please enter a message of less than 250 characters.";
    }

    public String printMessage() {
        return String.format("Message ID: %s\nMessage Hash: %s\nRecipient: %s\nMessage: %s",
                messageID, messageHash, recipient, message);
    }

    public void storeMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("messages.json", true)) {
            gson.toJson(this, writer);
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public int returnTotalMessages() {
        return messageNum;
    }

    public static void saveMessagesToJson(List<Message> messages, String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(messages, writer);
            System.out.println("Messages saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving messages: " + e.getMessage());
        }
    }
}
