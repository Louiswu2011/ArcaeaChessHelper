package Chess;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CountdownTimer implements Runnable {
    private int target = 0;
    private TextField tf;
    private Label l;

    CountdownTimer(int target, TextField tf, Label l) {
        this.target = target;
        this.tf = tf;
        this.l = l;
    }

    @Override
    public void run() {
        this.runTimer();
    }

    private void runTimer() {
        try {
            while (target > 0) {
                target--;
                Thread.sleep(1000);
                this.tf.setText(Integer.toString(target));
                if (this.l.getText().equals("Cancelled")) {
                    throw new NumberFormatException();
                }
            }
            throw new NullPointerException();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
