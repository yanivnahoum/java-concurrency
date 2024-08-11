package learn.java.threads.exercise2;

public class Producer implements Runnable {

    private static final String[] WORDS = {
            "Banana", "Apple", "Orange", "Watermelon", "Fig", "Pear", "Strawberry", "BYE"
    };

    private final MailBox mailBox;
    private int messageCount = 0;

    public Producer(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    @Override
    public void run() {
        // Send messages one by one (sleep for 1-2 sec between each send)
    }

    public int getMessageCount(){
        return messageCount;
    }
}
