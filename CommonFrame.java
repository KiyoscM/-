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

public abstract class CommonFrame extends JFrame implements MouseListener {

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

    protected ImagePanel homeBackgroundPanel;// タイトル用背景画像
    protected ImagePanel commonBackgroundPanel;// 共通背景
    protected ImagePanel creditPanel;

    public CommonFrame(String title, Rectangle rectangle, int mode) {
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

        // ホーム背景
        homeBackgroundPanel = new ImagePanel("images/homesample.png");
        homeBackgroundPanel.setLayout(new BorderLayout());

        // 共通背景
        commonBackgroundPanel = new ImagePanel("images/background2.png");
        commonBackgroundPanel.setLayout(new BorderLayout());

        creditPanel = new ImagePanel("images/credit.png");
        if (mode == -1) {
            add(creditPanel);
        }
        if (mode == 1) {
            add(homeBackgroundPanel);
        }
        if (mode >= 2) {
            add(commonBackgroundPanel);
        }

    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

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