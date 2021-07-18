import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

public class TransitionAnimationPanel extends JComponent {

    private static final long serialVersionUID = 1L;
    private SceneController sceneController;
    private Image[] bubble;
    protected Timer timer = null;
    protected ActionListener onTick = null;
    protected boolean started = false;
    protected int alphaLevel = 0;
    protected float shield = 0.70f;
    protected int barsCount = 14;
    protected float fps = 15.0f;
    private int i = 0;
    private int status = 0;

    public TransitionAnimationPanel(SceneController sceneController) {
        this(sceneController, 14);
    }

    public TransitionAnimationPanel(SceneController sceneController, int barsCount) {
        this(sceneController, barsCount, 0.70f);
    }

    public TransitionAnimationPanel(SceneController sceneController, int barsCount, float shield) {
        this(sceneController, barsCount, shield, 15.0f);
    }

    public TransitionAnimationPanel(SceneController sceneController, int barsCount, float shield, float fps) {
        this(sceneController, barsCount, shield, fps, 180);
    }

    public TransitionAnimationPanel(SceneController sceneController, int barsCount, float shield, float fps,
            int rampDelay) {
        bubble = new Image[5];
        bubble[0] = new ImageIcon("images/bubble1.png").getImage();
        bubble[1] = new ImageIcon("images/bubble2.png").getImage();
        bubble[2] = new ImageIcon("images/bubble3.png").getImage();
        bubble[3] = new ImageIcon("images/bubble4.png").getImage();
        bubble[4] = new ImageIcon("images/bubble5.png").getImage();
        this.sceneController = sceneController;
        this.shield = shield >= 0.0f ? shield : 0.0f;
        this.fps = fps > 0.0f ? fps : 15.0f;
        this.barsCount = barsCount > 0 ? barsCount : 14;
        this.timer = new Timer(rampDelay, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (i < 4) {
                    i++;
                } else {
                    stop();
                }
                repaint();
            }
        });
        timer.start();
    }

    public void start() {
        setVisible(true);
    }

    public void stop() {
        timer.stop();
        sceneController.moveAnimation(1);
    }

    public void interrupt() {
        timer.stop();
        setVisible(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(255, 255, 255, (int) (alphaLevel * shield)));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(bubble[i], 0, 50 - i * 30, this);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}