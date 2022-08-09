import javax.swing.*;
import java.awt.*;

public class testGui extends JFrame
{
    String val;
    int value = 0;
    JLabel l;
    JLabel la, lb, lc;
    public TaskObserver t, ta, tb, tc;

    public testGui(){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300,300);
        l = new JLabel();
        la = new JLabel();
        lb = new JLabel();
        lc = new JLabel();
        f.getContentPane().add(BorderLayout.NORTH, l);
        f.getContentPane().add(BorderLayout.EAST, la);
        f.getContentPane().add(BorderLayout.SOUTH, lb);
        f.getContentPane().add(BorderLayout.WEST, lc);
        f.setVisible(true);

        t = new TaskObserver() {
            @Override
            public void update(Object progress) {
                l.setText((String) progress);
            }
        };
        ta = new TaskObserver() {
            @Override
            public void update(Object progress) {
                la.setText((String) progress);
            }
        };

        tb = new TaskObserver() {
            @Override
            public void update(Object progress) {
                lb.setText((String) progress);
            }
        };

        tc = new TaskObserver() {
            @Override
            public void update(Object progress) {
                lc.setText((String) progress);
            }
        };

    }

    /*@Override
    public void update(Object progress) {
        l.setText((String) progress);
        la.setText((String) progress);
        lb.setText((String) progress);
        lc.setText((String) progress);
    }*/
}

