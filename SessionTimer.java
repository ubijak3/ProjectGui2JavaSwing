import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SessionTimer extends Thread{
    static int time = 60000;

    public static void resetTime(){
        time = 60000;
    }

    @Override
    public void run() {
        try {
            while(time > 0) {
                Thread.sleep(1000);
                time -= 1000;
            }
            JOptionPane.showMessageDialog(null,"You have been logged out", null, JOptionPane.WARNING_MESSAGE);
            System.exit(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
