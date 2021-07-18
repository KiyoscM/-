import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.*;

public class TransitionTabbedPane extends JTabbedPane implements ChangeListener, Runnable {
    private static final long serialVersionUID = 1L;
    protected int animation_length = 20;

    public TransitionTabbedPane() {
        super();
        this.addChangeListener(this);
    }

    public int getAnimationLength() {
        return this.animation_length;
    }

    public void setAnimationLength() {
        this.animation_length = animation_length;
    }

    public void stateChanged(ChangeEvent evt) {
        new Thread(this).start();
    }

    protected int step;
    protected BufferedImage buf = null;
    protected int previous_tab = -1;

    public void run() {
        step = 0;

        if (previous_tab != -1) {
            Component comp = this.getComponentAt(previous_tab);
            buf = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            comp.paint(buf.getGraphics());
        }
        for (int i = 0; i < animation_length; i++) {
            step = i;
            repaint();
            try {
                Thread.currentThread().sleep(100);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        step = -1;
        previous_tab = this.getSelectedIndex();
        repaint();
    }

    public void paintChildren(Graphics g) {
        super.paintChildren(g);

        if (step != -1) {
            Rectangle size = this.getComponentAt(0).getBounds();
            Graphics2D g2 = (Graphics2D) g;
            paintTransition(g2, step, size, buf);
        }
    }

    public void paintTransition(Graphics2D g2, int step, Rectangle size, Image prev) {

    }
}