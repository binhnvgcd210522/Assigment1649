package Assigment1649;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message{
    private String timeSent;
    private String content;
    //Get time when message content was sent and return message with time sent
    public Message createMessage(String content){
        Message messageCreated = new Message();
        // Get DateTime
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Format Date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String formattedDateTime = currentDateTime.format(formatter);
        messageCreated.timeSent = formattedDateTime;
        //get content
        messageCreated.content = content;
        return messageCreated;
    }
    //Generate message
    public String toString(){
        return "Message: \""+ content +"\". Date: "+ timeSent;
    }
}
