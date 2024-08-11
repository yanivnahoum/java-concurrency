package learn.java.threads.solution2;

public class MailBox {

    private String message = "";

    public synchronized String take(){
        while (this.message.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                // Continue
            }
        }
        String answer = this.message;
        this.message = "";
        this.notifyAll();
        return answer;
    }

    public synchronized void put(String newMessage){
        while (!this.message.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                // Continue
            }
        }
        this.message = newMessage;
        this.notifyAll();
    }
}
