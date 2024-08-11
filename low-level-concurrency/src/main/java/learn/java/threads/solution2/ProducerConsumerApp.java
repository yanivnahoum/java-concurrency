package learn.java.threads.solution2;


import org.junit.Assert;

public class ProducerConsumerApp {

    public static void main(String[] args) throws InterruptedException {
        MailBox mailBox = new MailBox();
        Producer producer = new Producer(mailBox);
        Consumer consumer = new Consumer(mailBox);
        Thread pt = new Thread(producer);
        Thread ct = new Thread(consumer);

        pt.start();
        ct.start();

        pt.join();
        ct.join();

        Assert.assertTrue(consumer.getMessageCount() > 0);
        Assert.assertTrue(consumer.getMessageCount() == producer.getMessageCount());
    }

}
