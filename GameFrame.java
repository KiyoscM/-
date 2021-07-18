import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

/**
 * 全画面の共通設定
 *
 * @author takao
 */

public abstract class GameFrame extends JFrame implements MouseListener {

    private static final long serialVersionUID = 1L;
    /**
     * 必要に応じて表示する用
     *
     * private JPanel panel;
     *
     * private JLabel label; private String text; private ImageIcon icon;
     *
     */

    private Color backgroundColor;

    protected ImagePanel commonBackgroundPanel;// 共通背景

    public GameFrame(String title, Rectangle rectangle, int mode) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                initCommmonUI(title, rectangle, mode);
            }
        });
    }

    protected void initCommmonUI(String title, Rectangle rectangle, int mode) {
        // 基本
        setTitle(title);
        setBounds((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(),
                (int) rectangle.getHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // 共通背景
        commonBackgroundPanel = new ImagePanel("images/game_background1.jpg");
        commonBackgroundPanel.setLayout(new BorderLayout());

        add(commonBackgroundPanel);
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    /*
     * public void errorMessage(Message message) { switch (message.getStatus()) {
     *
     * } }
     */

    public static Clip createClip(File path) {

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)) {

            AudioFormat af = ais.getFormat();

            DataLine.Info dataLine = new DataLine.Info(Clip.class, af);

            Clip c = (Clip) AudioSystem.getLine(dataLine);

            c.open(ais);

            return c;

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (UnsupportedAudioFileException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (LineUnavailableException e) {

            e.printStackTrace();

        }

        return null;

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {

    }
}