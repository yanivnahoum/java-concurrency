package learn.java.threads.solution2;

public class Consumer implements Runnable {

    private final MailBox mailBox;
    private int messageCount = 0;

    public Consumer(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    @Override
    public void run() {
        String message = null;
        while (!"BYE".equals(message)){
            message = mailBox.take();
            System.out.println("Consumer got message: " + message);
        }
        System.out.println("Consumer terminated due to BYE message");
    }

    public int getMessageCount(){
        return messageCount;
    }
}
