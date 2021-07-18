import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SingleModeFrame extends CommonFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private SceneController sceneController;
    private JLabel selectLevelLabel;
    private JLabel explainLabel;
    private ImageIcon selectLevel[];
    private ImageIcon explain[];
    private int level;

    public SingleModeFrame(SceneController sceneController) {
        super("漢字物語/multiMode", new Rectangle(100, 100, 600, 400), 6);

        this.sceneController = sceneController;
        selectLevel = new ImageIcon[5];
        selectLevel[4] = new ImageIcon("images/level5.png");
        selectLevel[3] = new ImageIcon("images/level4.png");
        selectLevel[2] = new ImageIcon("images/level3.png");
        selectLevel[1] = new ImageIcon("images/level2.png");
        selectLevel[0] = new ImageIcon("images/level1.png");

        explain = new ImageIcon[5];
        explain[4] = new ImageIcon("images/explain5.png");
        explain[3] = new ImageIcon("images/explain4.png");
        explain[2] = new ImageIcon("images/explain3.png");
        explain[1] = new ImageIcon("images/explainP2.png");
        explain[0] = new ImageIcon("images/explain2.png");

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                initSingleModeUI();
            }
        });
    }

    protected void initSingleModeUI() {

        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());
        wholePanel.setOpaque(false);

        JPanel selectPanel = new JPanel();
        selectPanel.setPreferredSize(new Dimension(600, 300));
        selectPanel.setOpaque(false);

        JButton selectLeftButton = new JButton(new ImageIcon("images/select_button_left.png"));
        selectLeftButton.setOpaque(false);
        selectLeftButton.setContentAreaFilled(false);
        selectLeftButton.setBorderPainted(false);
        selectLeftButton.setBorder(null);
        selectLeftButton.setBounds(0, 0, 30, 30);
        selectLeftButton.addActionListener(this);
        selectLeftButton.setActionCommand("backmode");

        JButton selectRightButton = new JButton(new ImageIcon("images/select_button_right.png"));
        selectRightButton.setOpaque(false);
        selectRightButton.setContentAreaFilled(false);
        selectRightButton.setBorderPainted(false);
        selectRightButton.setBorder(null);
        selectRightButton.setBounds(0, 0, 30, 30);
        selectRightButton.addActionListener(this);
        selectRightButton.setActionCommand("nextmode");

        selectLevelLabel = new JLabel(selectLevel[4]);
        selectLevelLabel.setOpaque(false);
        selectLevelLabel.setBounds(0, 0, 300, 80);

        explainLabel = new JLabel(explain[4]);
        explainLabel.setOpaque(false);
        explainLabel.setBounds(0, 0, 300, 80);

        selectPanel.add(selectLeftButton);
        selectPanel.add(selectLevelLabel);
        selectPanel.add(selectRightButton);
        selectPanel.add(explainLabel);

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
        startButton.setActionCommand("single");

        JButton backButton = new CustomJButton();
        backButton.setToolTipText("戻る");
        backButton.setIcon(new ImageIcon("images/backButton2.png"));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(50, 50));
        backButton.addActionListener(this.sceneController);
        backButton.setActionCommand("backSelect2");

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

        wholePanel.add(selectPanel);
        wholePanel.add(startPanel);

        commonBackgroundPanel.add(wholePanel, BorderLayout.CENTER);

    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("backmode")) {
            String str = selectLevelLabel.getIcon().toString();
            String[] split = str.split("images/level", 2);
            int index = Character.getNumericValue(split[1].charAt(0));
            if (index == 5) {
                selectLevelLabel.setIcon(selectLevel[0]);
                explainLabel.setIcon(explain[0]);
                setLevel(index);
            } else {
                selectLevelLabel.setIcon(selectLevel[index]);
                explainLabel.setIcon(explain[index]);
                setLevel(index);
            }
        } else if (command.equals("nextmode")) {
            String str = selectLevelLabel.getIcon().toString();
            String[] split = str.split("images/level", 2);
            int index = Character.getNumericValue(split[1].charAt(0));
            if (index == 1) {
                selectLevelLabel.setIcon(selectLevel[4]);
                explainLabel.setIcon(explain[4]);
                setLevel(index);
            } else {
                selectLevelLabel.setIcon(selectLevel[index - 2]);
                explainLabel.setIcon(explain[index - 2]);
                setLevel(index);
            }
        }

    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}