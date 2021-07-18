import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;

class ResultFrame extends GameFrame {
    private static final long serialVersionUID = 1L;
    private SceneController sceneController;

    public ResultFrame(SceneController sceneController, int mode, int[] resultScore) {
        super("結果", new Rectangle(0, 0, 1000, 800), 77);
        this.sceneController = sceneController;
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                initResultUI(mode, resultScore);
            }
        });

    }

    protected void initResultUI(int mode, int[] resultScore) {
        switch (mode) {
        case 1:
            initResultSingle(resultScore);
            break;
        case 2:
            initResultVs(resultScore);
            break;
        case 3:
            initResultMulti(resultScore);
            break;
        }
    }

    protected void initResultSingle(int[] resultScore) {
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());
        wholePanel.setOpaque(false);

        Font fontMessage = null;
        try {
            fontMessage = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/AmeChanPopMaruTTFLight-Regular.ttf"))
                    .deriveFont(Font.CENTER_BASELINE, 25);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPanel titPanel = new JPanel();
        titPanel.setPreferredSize(new Dimension(500, 50));
        titPanel.setOpaque(false);

        RichJLabel titleLabel = new RichJLabel("結果発表", 5);
        titleLabel.setLeftShadow(1, 1, Color.WHITE);
        titleLabel.setRightShadow(2, 3, Color.BLACK);
        titleLabel.setPreferredSize(new Dimension(250, 50));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setFont(fontMessage);
        titleLabel.setOpaque(false);

        titPanel.add(titleLabel);

        wholePanel.add(titPanel);

        commonBackgroundPanel.add(wholePanel);
    }

    protected void initResultVs(int[] resultScore) {
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());
        wholePanel.setOpaque(false);

        Font fontMessage = null;
        try {
            fontMessage = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/AmeChanPopMaruTTFLight-Regular.ttf"))
                    .deriveFont(Font.CENTER_BASELINE, 25);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPanel titPanel = new JPanel();
        titPanel.setPreferredSize(new Dimension(1000, 50));
        titPanel.setOpaque(false);

        RichJLabel titleLabel = new RichJLabel("結果発表", 5);
        titleLabel.setLeftShadow(1, 1, Color.WHITE);
        titleLabel.setRightShadow(2, 3, Color.BLACK);
        titleLabel.setPreferredSize(new Dimension(250, 50));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setFont(fontMessage);
        titleLabel.setOpaque(false);

        titPanel.add(titleLabel);

        wholePanel.add(titPanel);

        commonBackgroundPanel.add(wholePanel);
    }

    protected void initResultMulti(int[] resultScore) {
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());
        wholePanel.setOpaque(false);

        Font fontMessage = null;
        try {
            fontMessage = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/AmeChanPopMaruTTFLight-Regular.ttf"))
                    .deriveFont(Font.CENTER_BASELINE, 30);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(1000, 100));
        titlePanel.setOpaque(false);

        RichJLabel titleLabel = new RichJLabel("RESULTS MULTI", 5);
        titleLabel.setLeftShadow(1, 1, Color.WHITE);
        titleLabel.setRightShadow(2, 3, Color.BLACK);
        titleLabel.setPreferredSize(new Dimension(250, 50));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setFont(fontMessage);
        titleLabel.setOpaque(false);

        JPanel rankPane = new JPanel();
        rankPane.setPreferredSize(new Dimension(200, 400));
        rankPane.setOpaque(false);
        rankPane.setLayout(new GridLayout(4, 1));

        RichJLabel firstLabel = new RichJLabel("1st", 0);
        firstLabel.setPreferredSize(new Dimension(200, 100));
        firstLabel.setForeground(Color.RED);
        firstLabel.setFont(fontMessage);
        firstLabel.setLeftShadow(1, 1, Color.WHITE);
        firstLabel.setRightShadow(2, 3, Color.BLACK);

        RichJLabel secondLabel = new RichJLabel("2nd", 0);
        secondLabel.setPreferredSize(new Dimension(200, 100));
        secondLabel.setForeground(Color.BLUE);
        secondLabel.setFont(fontMessage);
        secondLabel.setLeftShadow(1, 1, Color.WHITE);
        secondLabel.setRightShadow(2, 3, Color.BLACK);

        RichJLabel thirdLabel = new RichJLabel("3rd", 0);
        thirdLabel.setPreferredSize(new Dimension(200, 100));
        thirdLabel.setForeground(Color.ORANGE);
        thirdLabel.setFont(fontMessage);
        thirdLabel.setLeftShadow(1, 1, Color.WHITE);
        thirdLabel.setRightShadow(2, 3, Color.BLACK);

        RichJLabel fourthLabel = new RichJLabel("4th", 0);
        fourthLabel.setPreferredSize(new Dimension(200, 100));
        fourthLabel.setForeground(Color.GREEN);
        fourthLabel.setFont(fontMessage);
        fourthLabel.setLeftShadow(1, 1, Color.WHITE);
        fourthLabel.setRightShadow(2, 3, Color.BLACK);

        JPanel scorePane = new JPanel();
        scorePane.setPreferredSize(new Dimension(500, 400));
        scorePane.setLayout(new GridLayout(4, 1));
        scorePane.setOpaque(false);

        //RichJLabel no1Player = new RichJLabel("0000", 0);
        RichJLabel no1Player = new RichJLabel(String.valueOf(resultScore[0]), 0);
        no1Player.setPreferredSize(new Dimension(500, 100));
        no1Player.setFont(fontMessage);
        no1Player.setForeground(Color.RED);
        no1Player.setLeftShadow(1, 1, Color.WHITE);
        no1Player.setRightShadow(2, 3, Color.BLACK);

        //RichJLabel no2Player = new RichJLabel("0000", 0);
        RichJLabel no2Player = new RichJLabel(String.valueOf(resultScore[1]), 0);
        no2Player.setPreferredSize(new Dimension(500, 100));
        no2Player.setFont(fontMessage);
        no2Player.setForeground(Color.BLUE);
        no2Player.setLeftShadow(1, 1, Color.WHITE);
        no2Player.setRightShadow(2, 3, Color.BLACK);

        //RichJLabel no3Player = new RichJLabel("0000", 0);
        RichJLabel no3Player = new RichJLabel(String.valueOf(resultScore[2]), 0);
        no3Player.setPreferredSize(new Dimension(500, 100));
        no3Player.setFont(fontMessage);
        no3Player.setForeground(Color.ORANGE);
        no3Player.setLeftShadow(1, 1, Color.WHITE);
        no3Player.setRightShadow(2, 3, Color.BLACK);

        //RichJLabel no4Player = new RichJLabel("0000", 0);
        RichJLabel no4Player = new RichJLabel(String.valueOf(resultScore[3]), 0);
        no4Player.setPreferredSize(new Dimension(500, 100));
        no4Player.setFont(fontMessage);
        no4Player.setForeground(Color.GREEN);
        no4Player.setLeftShadow(1, 1, Color.WHITE);
        no4Player.setRightShadow(2, 3, Color.BLACK);

        JPanel backPane = new JPanel();
        backPane.setPreferredSize(new Dimension(1000, 100));
        backPane.setOpaque(false);

        VectorButton backHomeButton = new VectorButton();
        backHomeButton.setPreferredSize(new Dimension(300, 50));
        backHomeButton.setText("  　 ホームへ   　");
        backHomeButton.addActionListener(this.sceneController);
        backHomeButton.setActionCommand("backhome_result");
        backHomeButton.setContentAreaFilled(false);
        backHomeButton.setOpaque(false);
        backHomeButton.setBorderPainted(false);

        VectorButton backSelectButton = new VectorButton();
        backSelectButton.setPreferredSize(new Dimension(300, 50));
        backSelectButton.setText("モードセレクトへ");
        backSelectButton.addActionListener(this.sceneController);
        backSelectButton.setActionCommand("backselect_result");
        backSelectButton.setContentAreaFilled(false);
        backSelectButton.setOpaque(false);
        backSelectButton.setBorderPainted(false);

        titlePanel.add(titleLabel);

        rankPane.add(firstLabel);
        rankPane.add(secondLabel);
        rankPane.add(thirdLabel);
        rankPane.add(fourthLabel);

        scorePane.add(no1Player);
        scorePane.add(no2Player);
        scorePane.add(no3Player);
        scorePane.add(no4Player);

        backPane.add(backHomeButton);
        backPane.add(backSelectButton);

        wholePanel.add(titlePanel);
        wholePanel.add(rankPane);
        wholePanel.add(scorePane);
        wholePanel.add(backPane);

        commonBackgroundPanel.add(wholePanel, BorderLayout.CENTER);
    }
}
