package learn.java.threads.exercise2;

public class ProducerConsumerApp {

    public static void main(String[] args) throws InterruptedException {
        // Create a mailbox
        MailBox mailBox = new MailBox();
        Producer producer = new Producer(mailBox);
        Consumer consumer = new Consumer(mailBox);

        // Create Producer/Consumer threads and start them

        // Wait for the producer/consumer to finish and print num of messages sent / received

    }

}
