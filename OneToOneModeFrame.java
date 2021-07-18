import java.awt.*;
import javax.swing.*;

public class OneToOneModeFrame extends CommonFrame {

    private static final long serialVersionUID = 1L;
    private SceneController sceneController;

    public OneToOneModeFrame(SceneController sceneController) {
        super("漢字物語/multiMode", new Rectangle(100, 100, 600, 400), 6);

        this.sceneController = sceneController;

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                initOneToOneModeUI();
            }
        });
    }

    protected void initOneToOneModeUI() {

        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());
        wholePanel.setOpaque(false);

        JPanel explainPanel = new JPanel();
        explainPanel.setPreferredSize(new Dimension(600, 200));
        explainPanel.setOpaque(false);

        JLabel explainLabel = new JLabel("ここにルール説明を書く");

        explainPanel.add(explainLabel);

        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(600, 60));
        startPanel.setBackground(getBackgroundColor());
        startPanel.setOpaque(false);

        JButton startButton = new JButton(new ImageIcon("images/buttonStart2.png"));
        startButton.setPreferredSize(new Dimension(300, 50));
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.addActionListener(this.sceneController);
        startButton.setActionCommand("vs");

        JButton backButton = new CustomJButton();
        backButton.setToolTipText("戻る");
        backButton.setIcon(new ImageIcon("images/backButton2.png"));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(50, 50));
        backButton.addActionListener(this.sceneController);
        backButton.setActionCommand("backSelect3");

        HintAction hintAction = new HintAction(this.sceneController);
        JButton hintButton = new JButton(hintAction);
        hintButton.setIcon(new ImageIcon("images/hintButton.png"));
        hintButton.setOpaque(false);
        hintButton.setContentAreaFilled(false);
        hintButton.setBorderPainted(false);
        hintButton.setPreferredSize(new Dimension(50, 50));

        startPanel.add(backButton);
        startPanel.add(startButton);
        startPanel.add(hintButton);

        wholePanel.add(explainPanel);
        wholePanel.add(startPanel);

        commonBackgroundPanel.add(wholePanel, BorderLayout.CENTER);

    }
}