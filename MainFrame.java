import java.awt.*;
import javax.swing.*;

public class MainFrame extends CommonFrame {

    private static final long serialVersionUID = 1L;
    private SceneController sceneController;

    public MainFrame(SceneController sceneController) {
        super("漢字物語/home", new Rectangle(100, 100, 600, 430), 1);

        this.sceneController = sceneController;

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                initMainUI();
            }
        });
    }

    protected void initMainUI() {

        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());
        wholePanel.setOpaque(false);

        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(600, 51));
        startPanel.setBackground(getBackground());
        startPanel.setOpaque(false);

        JButton startButton = new JButton(new ImageIcon("images/buttonStart.png"));
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setPreferredSize(new Dimension(300, 51));
        startButton.addActionListener(this.sceneController);
        startButton.setActionCommand("start");

        startPanel.add(startButton);
        wholePanel.add(startPanel);
        homeBackgroundPanel.add(wholePanel, BorderLayout.SOUTH);

    }

}