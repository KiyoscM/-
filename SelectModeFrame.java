import java.awt.*;
import javax.swing.*;

public class SelectModeFrame extends CommonFrame {
    private static final long serialVersionUID = 1L;

    private SceneController sceneController;
    private JButton singleModeButton;
    private JButton multiModeButton;
    private JButton oneToOneButton;

    public SelectModeFrame(SceneController sceneController) {
        super("漢字物語/selectMode", new Rectangle(100, 100, 600, 400), 3);

        this.sceneController = sceneController;

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                initSelectModeUI();
            }
        });
    }

    protected void initSelectModeUI() {
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        JPanel selectPanel = new JPanel();
        selectPanel.setPreferredSize(new Dimension(600, 190));

        singleModeButton = new JButton(new ImageIcon("images/buttonSingle.png"));
        singleModeButton.setPreferredSize(new Dimension(300, 50));
        singleModeButton.setOpaque(false);
        singleModeButton.setContentAreaFilled(false);
        singleModeButton.setBorderPainted(false);
        singleModeButton.addActionListener(this.sceneController);
        singleModeButton.setActionCommand("singleMode");

        multiModeButton = new JButton(new ImageIcon("images/buttonMulti.png"));
        multiModeButton.setPreferredSize(new Dimension(300, 50));
        multiModeButton.setOpaque(false);
        multiModeButton.setContentAreaFilled(false);
        multiModeButton.setBorderPainted(false);
        multiModeButton.addActionListener(this.sceneController);
        multiModeButton.setActionCommand("multiMode");

        oneToOneButton = new JButton(new ImageIcon("images/buttonVs2.png"));
        oneToOneButton.setPreferredSize(new Dimension(300, 50));
        oneToOneButton.setOpaque(false);
        oneToOneButton.setContentAreaFilled(false);
        oneToOneButton.setBorderPainted(false);
        oneToOneButton.addActionListener(this.sceneController);
        oneToOneButton.setActionCommand("vsMode");

        JPanel backPanel = new JPanel();
        backPanel.setPreferredSize(new Dimension(600, 60));
        backPanel.setOpaque(false);

        JButton backButton = new JButton(new ImageIcon("images/buttonHome.png"));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(300, 60));
        backButton.addActionListener(this.sceneController);
        backButton.setActionCommand("backhome3");

        backPanel.add(backButton);

        JPanel hintPanel = new JPanel();
        hintPanel.setPreferredSize(new Dimension(400, 60));
        hintPanel.setOpaque(false);
        hintPanel.setLayout(new BorderLayout());

        HintAction hintAction = new HintAction(this.sceneController);
        JButton hintButton = new JButton(hintAction);
        hintButton.setIcon(new ImageIcon("images/hintButton.png"));
        hintButton.setOpaque(false);
        hintButton.setContentAreaFilled(false);
        hintButton.setBorderPainted(false);
        hintButton.setPreferredSize(new Dimension(50, 50));

        hintPanel.add(hintButton, BorderLayout.EAST);

        selectPanel.add(singleModeButton);
        selectPanel.add(oneToOneButton);
        selectPanel.add(multiModeButton);
        selectPanel.setOpaque(false);

        wholePanel.add(selectPanel);
        wholePanel.add(backPanel);
        wholePanel.add(hintPanel);
        wholePanel.setOpaque(false);

        commonBackgroundPanel.add(wholePanel);
    }
}