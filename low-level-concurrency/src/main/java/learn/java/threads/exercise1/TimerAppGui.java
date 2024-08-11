package learn.java.threads.exercise1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TimerAppGui implements ActionListener {

    private JFrame window = new JFrame("Timer");
    private Timer timer = null;
    private JLabel timerLabel;

    public TimerAppGui(int timerInSeconds){
        this.timer = new Timer(this, timerInSeconds);
        this.timerLabel = new JLabel("" + timerInSeconds);
    }

    public void actionPerformed(ActionEvent e) {
        if ("START_STOP".equals(e.getActionCommand())){
            if (timer.isRunning()){
                timer.stop();
            } else {
                timer.start();
            }
        } else {
            timer.reset();
        }
    }

    public void start() {

        JButton startButton = new JButton("Start/Stop");
        JButton resetButton = new JButton("Reset");
        window.add(timerLabel);
        window.add(startButton,0);
        window.add(resetButton,1);

        window.setSize(300, 300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(3, 1));

        startButton.addActionListener(this);
        startButton.setActionCommand("START_STOP");
        resetButton.addActionListener(this);
        resetButton.setActionCommand("RESET");

        window.setVisible(true);
    }

    public void setNewTimerValue(int value) {
        this.timerLabel.setText(""+value);
    }

}