package learn.java.threads.exercise2;

public class Consumer implements Runnable {

    private final MailBox mailBox;
    private int messageCount = 0;

    public Consumer(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    @Override
    public void run() {
        // Keep taking messages from the mailbox until you get the message BYE
    }

    public int getMessageCount(){
        return messageCount;
    }
}
