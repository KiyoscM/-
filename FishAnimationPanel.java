import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

public class FishAnimationPanel extends JComponent {

    private static final long serialVersionUID = 1L;
    private SceneController sceneController;
    private Image fish;
    protected Timer timer = null;
    protected ActionListener onTick = null;
    protected boolean started = false;
    protected int alphaLevel = 0;
    protected float shield = 0.70f;
    protected int barsCount = 14;
    protected float fps = 15.0f;
    protected int mode = 0;
    private int i = 0;
    private int status = 0;

    public FishAnimationPanel(SceneController sceneController, int mode) {
        this(sceneController, 14, mode);
    }

    public FishAnimationPanel(SceneController sceneController, int barsCount, int mode) {
        this(sceneController, barsCount, 0.70f, mode);
    }

    public FishAnimationPanel(SceneController sceneController, int barsCount, float shield, int mode) {
        this(sceneController, barsCount, shield, 15.0f, mode);
    }

    public FishAnimationPanel(SceneController sceneController, int barsCount, float shield, float fps, int mode) {
        this(sceneController, barsCount, shield, fps, 150, mode);
    }

    public FishAnimationPanel(SceneController sceneController, int barsCount, float shield, float fps, int rampDelay,
            int mode) {
        fish = Toolkit.getDefaultToolkit().createImage("images/fish1.gif");
        // fish = new ImageIcon("images/fish1.gif").getImage();
        this.sceneController = sceneController;
        this.shield = shield >= 0.0f ? shield : 0.0f;
        this.fps = fps > 0.0f ? fps : 15.0f;
        this.barsCount = barsCount > 0 ? barsCount : 14;
        this.mode = mode;
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
        sceneController.moveAnimation(mode);
    }

    public void interrupt() {
        timer.stop();
        setVisible(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(255, 255, 255, (int) (alphaLevel * shield)));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(fish, 0, 0, this);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}