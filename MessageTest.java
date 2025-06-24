/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapp;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author luke
 */


public class MessageTest {

    @Test
    public void testCorrectMessageID() {
        Message msg = new Message("+27987654321", "Hello", 1);
        assertTrue(msg.checkMessagesID());
    }

    @Test
    public void testCorrectRecipient() {
        Message msg = new Message("+27123456789", "Hello my friend", 1);
        assertEquals(1, msg.checkRecipientCell());
    }

    @Test
    public void testIncorrectRecipient() {
        Message msg = new Message("123456789", "Hello", 1);
        assertEquals(0, msg.checkRecipientCell());
    }

    @Test
    public void testMessageWithinLimit() {
        Message msg = new Message("+27987654321", "Short message", 1);
        assertEquals("Message sent", msg.sentMessage());
    }

    @Test
    public void testMessageTooLong() {
        String longMessage = "a".repeat(251);
        Message msg = new Message("+27987654321", longMessage, 1);
        assertEquals("Please enter a message of less than 250 characters.", msg.sentMessage());
    }

    @Test
    public void testCreateMessageHashFormat() {
        Message msg = new Message("+27987654321", "Hi and thanks", 1);
        String hash = msg.createMessageHash();
        assertTrue(hash.matches("^[0-9]{2}:1:[A-Z]+[A-Z]+$"));
    }

    @Test
    public void testPrintMessageFormat() {
        Message msg = new Message("+27987654321", "Hello!", 1);
        String output = msg.printMessage();
        assertTrue(output.contains("Recipient: +27987654321"));
        assertTrue(output.contains("Message: Hello!"));
        assertTrue(output.contains("Message ID:"));
        assertTrue(output.contains("Message Hash:"));
    }

    @Test
    public void testReturnTotalMessagesIncrements() {
        Message msg2 = new Message("+27123456789", "Hi there", 2);
        assertEquals(2, msg2.returnTotalMessages());
    }

    @Test
    public void testMessageArrayStorage() {
        List<Message> sentMessages = new ArrayList<>();
        Message msg = new Message("+27987654321", "Testing", 1);
        sentMessages.add(msg);
        assertEquals(1, sentMessages.size());
        assertEquals("Testing", sentMessages.get(0).message);
    }

    @Test
    public void testFindLongestMessage() {
        List<Message> sentMessages = new ArrayList<>();
        sentMessages.add(new Message("+27987654321", "Short", 1));
        sentMessages.add(new Message("+27111111111", "This is definitely a longer message", 2));

        Message longest = sentMessages.stream()
                .max((a, b) -> Integer.compare(a.message.length(), b.message.length()))
                .orElse(null);

        assertNotNull(longest);
        assertEquals("This is definitely a longer message", longest.message);
    }

    @Test
    public void testSearchByMessageID() {
        List<Message> sentMessages = new ArrayList<>();
        Message msg = new Message("+27987654321", "Hello there", 1);
        sentMessages.add(msg);

        String searchID = msg.messageID;
        Message found = sentMessages.stream()
                .filter(m -> m.messageID.equals(searchID))
                .findFirst()
                .orElse(null);

        assertNotNull(found);
        assertEquals("Hello there", found.message);
    }

    @Test
    public void testSearchByRecipient() {
        List<Message> sentMessages = new ArrayList<>();
        sentMessages.add(new Message("+27123456789", "Msg 1", 1));
        sentMessages.add(new Message("+27123456789", "Msg 2", 2));
        sentMessages.add(new Message("+27888888888", "Msg 3", 3));

        List<Message> result = sentMessages.stream()
                .filter(m -> m.recipient.equals("+27123456789"))
                .toList();

        assertEquals(2, result.size());
        assertEquals("Msg 1", result.get(0).message);
    }

    @Test
    public void testDeleteByHash() {
        List<Message> sentMessages = new ArrayList<>();
        Message msg = new Message("+27987654321", "Delete me", 1);
        sentMessages.add(msg);
        String hashToDelete = msg.createMessageHash();

        boolean removed = sentMessages.removeIf(m -> m.createMessageHash().equals(hashToDelete));
        assertTrue(removed);
        assertEquals(0, sentMessages.size());
    }

    @Test
    public void testFullReportStringContent() {
        List<Message> sentMessages = new ArrayList<>();
        sentMessages.add(new Message("+27987654321", "Hello", 1));
        sentMessages.add(new Message("+27987654322", "World", 2));

        StringBuilder report = new StringBuilder();
        for (Message m : sentMessages) {
            report.append(m.printMessage()).append("\n\n");
        }

        assertTrue(report.toString().contains("Hello"));
        assertTrue(report.toString().contains("World"));
        assertTrue(report.toString().contains("Message ID:"));
    }
}
