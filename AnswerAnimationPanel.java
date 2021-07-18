
import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

class AnswerAnimationPanel extends JComponent {
    private static final long serialVersionUID = 1L;
    private static final int GOOD = 1;
    private static final int BAD = 0;
    private SceneController sceneController;
    private Image[] answer;
    protected Timer timer = null;
    protected ActionListener onTick = null;
    protected boolean started = false;
    protected int alphaLevel = 0;
    protected float shield = 0.70f;
    protected int barsCount = 14;
    protected float fps = 15.0f;
    private int i = 0;
    private int status = 0;

    public AnswerAnimationPanel(SceneController sceneController, int status) {
        this(sceneController, 14, status);
    }

    public AnswerAnimationPanel(SceneController sceneController, int barsCount, int status) {
        this(sceneController, barsCount, 0.70f, status);
    }

    public AnswerAnimationPanel(SceneController sceneController, int barsCount, float shield, int status) {
        this(sceneController, barsCount, shield, 15.0f, status);
    }

    public AnswerAnimationPanel(SceneController sceneController, int barsCount, float shield, float fps, int status) {
        this(sceneController, barsCount, shield, fps, 180, status);
    }

    public AnswerAnimationPanel(SceneController sceneController, int barsCount, float shield, float fps, int rampDelay,
            int status) {
        answer = new Image[2];
        answer[GOOD] = new ImageIcon("images/good.png").getImage();
        answer[BAD] = new ImageIcon("images/bad.png").getImage();

        this.sceneController = sceneController;
        this.shield = shield >= 0.0f ? shield : 0.0f;
        this.fps = fps > 0.0f ? fps : 15.0f;
        this.barsCount = barsCount > 0 ? barsCount : 14;
        this.status = status;
        this.timer = new Timer(rampDelay, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (i < 4) {
                    i++;
                } else {
                    stop();
                    setVisible(false);
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
    }

    public void interrupt() {
        timer.stop();
        setVisible(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(255, 255, 255, (int) (alphaLevel * shield)));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(answer[status], 0, 0, this);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}