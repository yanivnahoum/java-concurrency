package learn.java.threads.solution2;

import learn.java.threads.Utils;

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
        for (String message : WORDS) {
            System.out.println("Producer is sending message : " + message);
            mailBox.put(message);
            this.messageCount++;
            Utils.sleep(2000);
        }
        System.out.println("Producer finished sending all messages");
    }

    public int getMessageCount(){
        return messageCount;
    }
}
