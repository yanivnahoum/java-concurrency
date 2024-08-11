package learn.java.threads.solution2;

public class MailBox {

    private String message = "";

    public synchronized String take(){
        while (this.isEmpty()){
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

    public synchronized void put(String message){
        while (!this.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                // Continue
            }
        }
        this.message = message;
        this.notifyAll();
    }

    private boolean isEmpty(){
        return "".equals(message);
    }
}
