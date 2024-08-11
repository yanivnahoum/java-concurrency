package learn.java.threads.solution2;


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

        assert (consumer.getMessageCount() > 0);
        assert (consumer.getMessageCount() == producer.getMessageCount());
    }

}
