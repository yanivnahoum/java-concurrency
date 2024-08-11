package learn.java.threads.exercise2;

public class MailBox {

    private String message = "";

    public synchronized String take(){
        // Remove message from mailbox and return it.
        // If mailbox is empty wait until you receive a new message
        return null;
    }

    public synchronized void put(String newMessage){
        // Put a new message in the mailbox.
        // If its not empty, wait until the consumer consumes the message
    }

}
