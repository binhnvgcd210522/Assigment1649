package Assigment1649;

import java.io.Console;

import static Assigment1649.Main.*;

public class MailSystem {
    public String systemName;
    public MailSystem connectedSystem;
    private Queue<Message> inboxQueue;
    private Queue<Message> outboxQueue;
    private Stack<Message> processingStack;

    public MailSystem(String name) {
        this.systemName = name;
        this.inboxQueue = new Queue<>();
        this.outboxQueue = new Queue<>();
        this.processingStack = new Stack<>();
        System.out.println(BLUE_Color + "System created successful!" + RESET_Color);
    }

    public void checkConnect() {
        if (connectedSystem != null) {
            System.out.println(systemName + " is connecting to: " + connectedSystem.systemName);
        } else {
            System.out.println(systemName + " is not connecting to other system.");
        }
    }

    public void connect(MailSystem mailSystem) {
        if (connectedSystem == null) {
            connectedSystem = mailSystem;
            System.out.println(BLUE_Color + "Connect to the " + mailSystem.systemName + " successful." + RESET_Color);
            mailSystem.connect(this);
        }
    }

    public void disConnect(MailSystem mailSystem) {
        this.connectedSystem = null;
        mailSystem.connectedSystem = null;
        System.out.println(BLUE_Color + "Disconnected." + BLUE_Color);

    }

    public void sendMessage(String content) {
        long startTime = System.nanoTime();
        Message newMessage = new Message();
        try {
            //check content not empty
            if (content.length() == 0) {
                throw new IllegalArgumentException("Error: Message cannot be empty");
            }
            if (content.length() > 10) {
                System.out.println("Message length exceeds 10 characters. Truncating...");
                // Truncate the message and send smaller messages
                int startIndex = 0;
                while (startIndex < content.length()) {
                    int endIndex = Math.min(startIndex + 10, content.length());
                    String truncatedMessage = content.substring(startIndex, endIndex);
                    outboxQueue.offer(newMessage.createMessage(truncatedMessage));
                    startIndex = endIndex;
                }
                System.out.println(BLUE_Color + "Message sent" + RESET_Color);
            } else {
                // Send the message
                outboxQueue.offer(newMessage.createMessage(content));
                System.out.println(BLUE_Color + "Message sent" + RESET_Color);
            }
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            System.out.println("Time spent: " + elapsedTime + " nanoseconds");
        } catch (IllegalArgumentException e) {
            System.out.println(RED_Color + e.getMessage() + RESET_Color);
        }
    }
    public void receiveMessage(MailSystem mailSystem) {
        long startTime = System.nanoTime();
        Message message = new Message();
        while (!mailSystem.outboxQueue.isEmpty()) {
            message = mailSystem.outboxQueue.poll();
            this.inboxQueue.offer(message);
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Time spent: " + elapsedTime + " nanoseconds");
    }

    public void checkInBox() {
        if (inboxQueue.isEmpty()) System.out.println(RED_Color + "Inbox is Empty." + RESET_Color);
        else {
            processingMessage();
        }
        while (processingStack.size() != 0) {
            System.out.println(processingStack.pop());
        }
    }

    private void processingMessage() {
        long startTime = System.nanoTime();
        int count = 0;
        while (!inboxQueue.isEmpty() && count < 5) {
            Message message = inboxQueue.poll();
            processingStack.push(message);
            count++;
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Time spent: " + elapsedTime + " nanoseconds");
    }

    public boolean outboxEmpty() {
        return outboxQueue.isEmpty();
    }

    public String toString() {
        if (this.connectedSystem == null)
            return "System name: " + systemName + ". Not connecting to other system";
        else
            return "System name: " + systemName + ". Is connecting to " + this.connectedSystem.systemName;
    }
}
