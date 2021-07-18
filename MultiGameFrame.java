import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.io.*;

public class MultiGameFrame extends GameFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    public static final int multiBoardSize = 10;
    private SceneController sceneController;
    private JToggleButton kanjiButton[][] = new JToggleButton[multiBoardSize][multiBoardSize];
    private JLabel kanji[][] = new JLabel[multiBoardSize][multiBoardSize];
    private JTextField furigana;
    private JLabel selectLabel;
    private JButton answerButton;
    private ImageIcon defalutPanel, enablePanel, player1Pane, player2Pane, player3Pane, player4Pane;
    private int[][] boardStatus = new int[multiBoardSize][multiBoardSize];
    private String selectKanji = "";
    private String position = "";
    public static final int BOARD = 0;
    public static final int SELECT = 7;
    public static final int CANSELECT = -1;
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public static final int PLAYER3 = 3;
    public static final int PLAYER4 = 4;

    // FIXME
    private String board[][] = new String[10][10];
    private int[][] boardPosition = new int[multiBoardSize][multiBoardSize];
    private int time = 180;
    private RichJLabel timeLabel;
    private RichJLabel scoreLabel;
    private Timer timer;

    public MultiGameFrame(SceneController sceneController) {

        super("漢字物語/multiGame", new Rectangle(0, 0, 1000, 800), 3);
        this.sceneController = sceneController;
        defalutPanel = new ImageIcon("images/panel2.png");
        enablePanel = new ImageIcon("images/panelenable1.png");
        player1Pane = new ImageIcon("images/player1.png");
        player2Pane = new ImageIcon("images/player2.png");
        player3Pane = new ImageIcon("images/player3.png");
        player4Pane = new ImageIcon("images/player4.png");

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                initMultiGame();
            }
        });

    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    // public void setBoardPosition(int[][] boardPosition) {
    // this.boardPosition = boardPosition;
    // }

    public void updateBoard(String[][] board) {
        this.board = board;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                kanji[i][j].setText(this.board[i][j]);
            }
        }
    }

    public void updateScore(int score) {
        scoreLabel.setText("スコア= " + String.valueOf(score));
    }

    public void updateBoardPosition(int[][] boardPosition) {
        this.boardPosition = boardPosition;

        for (int i = 0; i < multiBoardSize; i++) {
            for (int j = 0; j < multiBoardSize; j++) {
                if (boardPosition[i][j] == PLAYER1) {
                    kanjiButton[i][j].setIcon(player1Pane);
                } else if (boardPosition[i][j] == PLAYER2) {
                    kanjiButton[i][j].setIcon(player2Pane);
                } else if (boardPosition[i][j] == PLAYER3) {
                    kanjiButton[i][j].setIcon(player3Pane);
                } else if (boardPosition[i][j] == PLAYER4) {
                    kanjiButton[i][j].setIcon(player4Pane);
                }
            }
        }
    }

    // FIXME メソッドを置く位置
    public void setKanjiToLabel(int x, int y, String str) {
        kanji[x][y] = new JLabel();
        kanji[x][y].setForeground(new Color(0, 0, 255));
        this.kanji[x][y].setText(str);
    }

    public void setBoard(int x, int y, String str) {
        this.board[x][y] = str;
    }

    protected void initMultiGame() {

        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());
        wholePanel.setOpaque(false);

        JPanel timeKeeperPanel = new JPanel();
        timeKeeperPanel.setPreferredSize(new Dimension(1000, 55));
        timeKeeperPanel.setOpaque(false);

        Font fontMessage = null;
        try {
            fontMessage = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/AmeChanPopMaruTTFLight-Regular.ttf"))
                    .deriveFont(Font.CENTER_BASELINE, 25);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RichJLabel timerLabel = new RichJLabel("のこりじかん", 0);
        timerLabel.setLeftShadow(1, 1, Color.BLUE);
        timerLabel.setRightShadow(2, 3, Color.BLACK);
        timerLabel.setForeground(new Color(255, 255, 255));
        timerLabel.setPreferredSize(new Dimension(300, 50));
        timerLabel.setFont(fontMessage);
        timerLabel.setOpaque(false);

        timeLabel = new RichJLabel("", 0);
        timeLabel.setLeftShadow(1, 1, Color.BLUE);
        timeLabel.setRightShadow(2, 3, Color.BLACK);
        timeLabel.setForeground(new Color(255, 255, 255));
        timeLabel.setPreferredSize(new Dimension(200, 50));
        timeLabel.setFont(fontMessage);
        timeLabel.setOpaque(false);

        scoreLabel = new RichJLabel("  スコア=" + 0, 0);
        scoreLabel.setLeftShadow(1, 1, Color.BLUE);
        scoreLabel.setRightShadow(2, 3, Color.BLACK);
        scoreLabel.setForeground(new Color(255, 255, 255));
        scoreLabel.setPreferredSize(new Dimension(200, 50));
        scoreLabel.setFont(fontMessage);
        scoreLabel.setOpaque(false);

        PauseAction pauseAction = new PauseAction(this.sceneController);
        JButton pauseButton = new JButton(pauseAction);
        pauseButton.setIcon(new ImageIcon("images/pauseButton.png"));
        pauseButton.setContentAreaFilled(false);
        pauseButton.setBorderPainted(false);
        pauseButton.setPreferredSize(new Dimension(50, 50));

        timeKeeperPanel.add(timerLabel);
        timeKeeperPanel.add(timeLabel);
        timeKeeperPanel.add(scoreLabel);
        timeKeeperPanel.add(pauseButton);

        JLayeredPane layerePane = new JLayeredPane();
        layerePane.setPreferredSize(new Dimension(500, 500));

        JPanel boardPanel = new JPanel();
        boardPanel.setSize(new Dimension(500, 500));
        boardPanel.setLayout(new GridLayout(multiBoardSize, multiBoardSize));
        boardPanel.setOpaque(false);
        layerePane.add(boardPanel);
        layerePane.setLayer(boardPanel, multiBoardSize);

        for (int i = 0; i < multiBoardSize; i++) {
            for (int j = 0; j < multiBoardSize; j++) {

                kanjiButton[i][j] = new JToggleButton(defalutPanel);
                kanjiButton[i][j].setBorderPainted(false);
                kanjiButton[i][j].setOpaque(false);
                kanjiButton[i][j].setContentAreaFilled(false);
                kanjiButton[i][j].setPreferredSize(new Dimension(50, 50));
                kanjiButton[i][j].addActionListener(this);
                kanjiButton[i][j].setActionCommand(String.valueOf(i) + "," + String.valueOf(j));
                kanjiButton[i][j].setSelectedIcon(new ImageIcon("images/panelclick.png"));
                kanjiButton[i][j].setRolloverIcon(new ImageIcon("images/panelon.png"));

                boardPanel.add(kanjiButton[i][j]);

            }
        }

        JPanel kanjiPanel = new JPanel();
        // kanjiPanel.setSize(new Dimension(500, 500));
        kanjiPanel.setBounds(5, 5, 500, 500);
        kanjiPanel.setLayout(new GridLayout(multiBoardSize, multiBoardSize, 1, 1));
        kanjiPanel.setOpaque(false);
        layerePane.add(kanjiPanel);
        layerePane.setLayer(kanjiPanel, 11);
        Font font = new Font("メイリオ", Font.BOLD, 35);

        for (int i = 0; i < multiBoardSize; i++) {
            for (int j = 0; j < multiBoardSize; j++) {
                kanji[i][j] = new JLabel();
                kanji[i][j].setForeground(new Color(27, 28, 128));
                kanji[i][j].setFont(font);
                // kanji[i][j].setText("道");
                kanji[i][j].setText(this.board[i][j]);
                kanjiPanel.add(kanji[i][j]);
            }
        }

        JPanel playerPanel = new JPanel();
        playerPanel.setPreferredSize(new Dimension(900, 100));
        playerPanel.setLayout(new GridLayout(1, 4, 10, 10));
        playerPanel.setOpaque(false);

        JLabel player1Label = new JLabel("Player1");
        player1Label.setFont(fontMessage);
        player1Label.setForeground(new Color(181, 255, 20));
        player1Label.setPreferredSize(new Dimension(200, 100));
        player1Label.setOpaque(false);

        JLabel player2Label = new JLabel("Player2");
        player2Label.setFont(fontMessage);
        player2Label.setForeground(new Color(255, 20, 181));
        player2Label.setPreferredSize(new Dimension(200, 100));
        player2Label.setOpaque(false);

        JLabel player3Label = new JLabel("Player3");
        player3Label.setFont(fontMessage);
        player3Label.setForeground(new Color(20, 255, 212));
        player3Label.setPreferredSize(new Dimension(200, 100));
        player3Label.setOpaque(false);

        JLabel player4Label = new JLabel("Player4");
        player4Label.setFont(fontMessage);
        player4Label.setForeground(new Color(255, 212, 20));
        player4Label.setPreferredSize(new Dimension(200, 100));
        player4Label.setOpaque(false);

        playerPanel.add(player1Label);
        playerPanel.add(player2Label);
        playerPanel.add(player3Label);
        playerPanel.add(player4Label);

        ImagePanel answerPanel = new ImagePanel("images/answerboard.png");
        answerPanel.setPreferredSize(new Dimension(250, 200));

        selectLabel = new JLabel("");
        selectLabel.setPreferredSize(new Dimension(250, 50));
        selectLabel.setForeground(new Color(255, 255, 255));
        selectLabel.setFont(font);

        furigana = new JTextField();
        furigana.setPreferredSize(new Dimension(250, 50));
        furigana.setFont(font);
        furigana.setBackground(new Color(0, 135, 60));
        furigana.setForeground(new Color(255, 255, 255));
        furigana.setEditable(false);
        furigana.setCaretColor(Color.WHITE);
        furigana.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                furigana.getInputContext().setCompositionEnabled(false);
            }

            @Override
            public void focusGained(FocusEvent e) {
                furigana.getInputContext().setCompositionEnabled(true);
            }
        });

        furigana.addActionListener(this.sceneController);
        furigana.setActionCommand("answer");

        answerButton = new JButton(new ImageIcon("images/submitbutton.png"));
        answerButton.setPreferredSize(new Dimension(250, 50));
        answerButton.setOpaque(false);
        answerButton.setEnabled(false);
        answerButton.setContentAreaFilled(false);
        answerButton.setBorderPainted(false);
        answerButton.addActionListener(this.sceneController);
        answerButton.setActionCommand("answer");

        JButton resetButton = new JButton();
        resetButton.setPreferredSize(new Dimension(250, 50));
        resetButton.setMargin(new Insets(0, 200, 0, 0));
        resetButton.setOpaque(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setBorderPainted(false);
        resetButton.addActionListener(e -> {
            reset();
            print();
            answerButton.setEnabled(false);
            furigana.setEditable(false);
        });

        answerPanel.add(selectLabel);
        answerPanel.add(furigana);
        answerPanel.add(answerButton);
        answerPanel.add(resetButton);

        wholePanel.add(timeKeeperPanel);
        wholePanel.add(layerePane);
        wholePanel.add(answerPanel);
        wholePanel.add(playerPanel);
        commonBackgroundPanel.add(wholePanel);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String coordinate[] = command.split(",", 2);
        int x = Integer.parseInt(coordinate[0]);
        int y = Integer.parseInt(coordinate[1]);

        if (searchSelect() == 0) {
            reset();
            kanjiButton[x][y].setSelected(true);
            highlight(x, y);
            update();
            print();
            selectKanji += kanji[x][y].getText();
            setPosition(x, y);
            selectLabel.setText(selectKanji);
        } else if (boardStatus[x][y] == CANSELECT) {
            highlight(x, y);
            update();
            print();
            selectKanji += kanji[x][y].getText();
            setPosition(x, y);
            selectLabel.setText(selectKanji);
        } else if (boardStatus[x][y] != CANSELECT && boardStatus[x][y] != SELECT) {

            reset();
            kanjiButton[x][y].setSelected(true);
            selectKanji += kanji[x][y].getText();
            setPosition(x, y);
            selectLabel.setText(selectKanji);
            highlight(x, y);
            update();
            print();

        } else if (boardStatus[x][y] == SELECT) {
            reset();
            print();
        }

        if (search().equals("")) {
            answerButton.setEnabled(false);
            furigana.setEditable(false);
        } else {
            answerButton.setEnabled(true);
            furigana.setEditable(true);
        }

    }

    public void print() {// debug
        for (int i = 0; i < multiBoardSize; i++) {
            System.out.print("|");
            for (int j = 0; j < multiBoardSize; j++) {
                System.out.printf("%2d|", boardStatus[i][j]);
            }
            System.out.println();

        }
        System.out.println("--------------");
    }

    public void setPosition(int x, int y) {
        this.position += String.valueOf(x) + String.valueOf(y);
    }

    public String getPosition() {
        return position;
    }

    // public String selectedButton() {
    public String[] getAnswer() {

        System.out.println(getPosition() + "," + kanji + "," + furigana.getText());

        // TODO SceneControllerに配列で渡す,kanji
        String[] answerList = { getPosition(), furigana.getText() };
        return answerList;
    }

    public String search() {
        String kanji = "";
        for (int i = 0; i < multiBoardSize; i++) {
            for (int j = 0; j < multiBoardSize; j++) {
                if (kanjiButton[i][j].isSelected()) {
                    kanji += selectedKanji(i, j);
                }
            }
        }
        return kanji;
    }

    public int searchSelect() {
        int counter = 0;
        for (int i = 0; i < multiBoardSize; i++) {
            for (int j = 0; j < multiBoardSize; j++) {
                if (boardStatus[i][j] == CANSELECT) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public String selectedKanji(int i, int j) {
        return kanji[i][j].getText();
    }

    public void highlight(int x, int y) {
        if (x == multiBoardSize - 1 && y == 0) {

            if (kanjiButton[x - 1][y].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x - 1][y + 1] = BOARD;
                boardStatus[x - 2][y] = BOARD;
            } else if (kanjiButton[x][y + 1].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x - 1][y + 1] = BOARD;
                boardStatus[x][y + 2] = BOARD;
            } else {
                boardStatus[x][y + 1] = CANSELECT;
                boardStatus[x - 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            }
        } else if (x == 0 && y == 0) {
            if (kanjiButton[x + 1][y].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x + 1][y + 1] = BOARD;
                boardStatus[x + 2][y] = BOARD;
            } else if (kanjiButton[x][y + 1].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x + 1][y + 1] = BOARD;
                boardStatus[x][y + 2] = BOARD;
            } else {
                boardStatus[x][y + 1] = CANSELECT;
                boardStatus[x + 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            }
        } else if (x == multiBoardSize - 1 && y == multiBoardSize - 1) {
            if (kanjiButton[x - 1][y].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x - 1][y - 1] = BOARD;
                boardStatus[x - 2][y] = BOARD;
            } else if (kanjiButton[x][y - 1].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x - 1][y - 1] = BOARD;
                boardStatus[x][y - 2] = BOARD;
            } else {
                boardStatus[x][y - 1] = CANSELECT;
                boardStatus[x - 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            }
        } else if (x == 0 && y == multiBoardSize - 1) {
            if (kanjiButton[x + 1][y].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x + 1][y - 1] = BOARD;
                boardStatus[x + 2][y] = BOARD;
            } else if (kanjiButton[x][y - 1].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x + 1][y - 1] = BOARD;
                boardStatus[x][y - 2] = BOARD;
            } else {
                boardStatus[x][y - 1] = CANSELECT;
                boardStatus[x + 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            }
        } else if (x == multiBoardSize - 1) {
            if (kanjiButton[x - 1][y].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x - 1][y + 1] = BOARD;
                boardStatus[x - 1][y - 1] = BOARD;
                boardStatus[x - 2][y] = BOARD;

            } else if (kanjiButton[x][y - 1].isSelected()) {
                boardStatus[x - 1][y - 1] = BOARD;
                if (y > 1) {
                    boardStatus[x][y - 2] = BOARD;
                }
                boardStatus[x][y + 1] = CANSELECT;
                boardStatus[x][y] = SELECT;
            } else if (kanjiButton[x][y + 1].isSelected()) {
                boardStatus[x - 1][y + 1] = BOARD;
                if (y < multiBoardSize - 2)
                    boardStatus[x][y + 2] = BOARD;
                boardStatus[x][y - 1] = CANSELECT;
                boardStatus[x][y] = SELECT;
            } else {
                boardStatus[x][y - 1] = CANSELECT;
                boardStatus[x][y + 1] = CANSELECT;
                boardStatus[x - 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            }
        } else if (x == 0) {
            if (kanjiButton[x + 1][y].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x + 1][y + 1] = BOARD;
                boardStatus[x + 1][y - 1] = BOARD;
                boardStatus[x + 2][y] = BOARD;
            } else if (kanjiButton[x][y - 1].isSelected()) {
                boardStatus[x + 1][y - 1] = BOARD;
                if (y > 1)
                    boardStatus[x][y - 2] = BOARD;
                boardStatus[x][y + 1] = CANSELECT;
                boardStatus[x][y] = SELECT;
            } else if (kanjiButton[x][y + 1].isSelected()) {
                boardStatus[x + 1][y + 1] = BOARD;
                if (y < multiBoardSize - 2)
                    boardStatus[x][y + 2] = BOARD;
                boardStatus[x][y - 1] = CANSELECT;
                boardStatus[x][y] = SELECT;
            } else {
                boardStatus[x][y - 1] = CANSELECT;
                boardStatus[x][y + 1] = CANSELECT;
                boardStatus[x + 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            }
        } else if (y == multiBoardSize - 1) {

            if (kanjiButton[x][y - 1].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x][y - 2] = BOARD;
                boardStatus[x - 1][y - 1] = BOARD;
                boardStatus[x + 1][y - 1] = BOARD;
            } else if (kanjiButton[x + 1][y].isSelected()) {
                boardStatus[x + 1][y - 1] = BOARD;
                if (x < multiBoardSize - 2)
                    boardStatus[x + 2][y] = BOARD;
                boardStatus[x - 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            } else if (kanjiButton[x - 1][y].isSelected()) {
                boardStatus[x - 1][y - 1] = BOARD;
                if (x > 1)
                    boardStatus[x - 2][y] = BOARD;
                boardStatus[x + 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            } else {
                boardStatus[x][y - 1] = CANSELECT;
                boardStatus[x - 1][y] = CANSELECT;
                boardStatus[x + 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            }
        } else if (y == 0) {
            if (kanjiButton[x][y + 1].isSelected()) {
                boardStatus[x][y] = SELECT;
                boardStatus[x][y + 2] = BOARD;
                boardStatus[x - 1][y + 1] = BOARD;
                boardStatus[x + 1][y + 1] = BOARD;
            } else if (kanjiButton[x + 1][y].isSelected()) {
                boardStatus[x + 1][y + 1] = BOARD;
                if (x < multiBoardSize - 2)
                    boardStatus[x + 2][y] = BOARD;
                boardStatus[x - 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            } else if (kanjiButton[x - 1][y].isSelected()) {
                boardStatus[x - 1][y + 1] = BOARD;
                if (x > 1)
                    boardStatus[x - 2][y] = BOARD;
                boardStatus[x + 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            } else {
                boardStatus[x][y + 1] = CANSELECT;
                boardStatus[x - 1][y] = CANSELECT;
                boardStatus[x + 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
            }

        } else if (!kanjiButton[x][y - 1].isSelected() && !kanjiButton[x][y + 1].isSelected()
                && !kanjiButton[x - 1][y].isSelected() && !kanjiButton[x + 1][y].isSelected()) {

            boardStatus[x][y - 1] = CANSELECT;
            boardStatus[x][y + 1] = CANSELECT;
            boardStatus[x - 1][y] = CANSELECT;
            boardStatus[x + 1][y] = CANSELECT;
            boardStatus[x][y] = SELECT;

        } else if (kanjiButton[x][y - 1].isSelected()) {
            if (y == 1) {
                boardStatus[x][y + 1] = CANSELECT;
                boardStatus[x][y] = SELECT;
                boardStatus[x - 1][y - 1] = BOARD;
                boardStatus[x + 1][y - 1] = BOARD;
            } else {
                boardStatus[x][y + 1] = CANSELECT;
                boardStatus[x][y] = SELECT;
                boardStatus[x][y - 2] = BOARD;
                boardStatus[x - 1][y - 1] = BOARD;
                boardStatus[x + 1][y - 1] = BOARD;
            }
        } else if (kanjiButton[x][y + 1].isSelected()) {
            if (y == multiBoardSize - 2) {
                boardStatus[x][y - 1] = CANSELECT;
                boardStatus[x][y] = SELECT;
                boardStatus[x - 1][y + 1] = BOARD;
                boardStatus[x + 1][y + 1] = BOARD;
            } else {
                boardStatus[x][y - 1] = CANSELECT;
                boardStatus[x][y] = SELECT;
                boardStatus[x][y + 2] = BOARD;
                boardStatus[x - 1][y + 1] = BOARD;
                boardStatus[x + 1][y + 1] = BOARD;
            }
        } else if (kanjiButton[x - 1][y].isSelected()) {
            if (x == 1) {
                boardStatus[x + 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
                boardStatus[x - 1][y + 1] = BOARD;
                boardStatus[x - 1][y - 1] = BOARD;
            } else {
                boardStatus[x + 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
                boardStatus[x - 1][y + 1] = BOARD;
                boardStatus[x - 1][y - 1] = BOARD;
                boardStatus[x - 2][y] = BOARD;
            }
        } else if (kanjiButton[x + 1][y].isSelected()) {
            if (x == multiBoardSize - 2) {
                boardStatus[x - 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
                boardStatus[x + 1][y + 1] = BOARD;
                boardStatus[x + 1][y - 1] = BOARD;
            } else {
                boardStatus[x - 1][y] = CANSELECT;
                boardStatus[x][y] = SELECT;
                boardStatus[x + 1][y + 1] = BOARD;
                boardStatus[x + 1][y - 1] = BOARD;
                boardStatus[x + 2][y] = BOARD;
            }
        }

    }

    public void reset() {
        for (int i = 0; i < multiBoardSize; i++) {
            for (int j = 0; j < multiBoardSize; j++) {
                if (kanjiButton[i][j].isSelected()) {
                    kanjiButton[i][j].setSelected(false);
                }

                // FIXME 色が相殺
                if (boardPosition[i][j] == PLAYER1) {
                    boardStatus[i][j] = PLAYER1;
                } else if (boardPosition[i][j] == PLAYER2) {
                    boardStatus[i][j] = PLAYER2;
                } else if (boardPosition[i][j] == PLAYER3) {
                    boardStatus[i][j] = PLAYER3;
                } else if (boardPosition[i][j] == PLAYER4) {
                    boardStatus[i][j] = PLAYER4;
                } else {
                    boardStatus[i][j] = BOARD;
                }

                update();
            }
        }
        selectKanji = "";
        selectLabel.setText("");
        furigana.setText("");
        position = "";
        answerButton.setEnabled(false);
        furigana.setEditable(false);
    }

    public void update() {
        for (int i = 0; i < multiBoardSize; i++) {
            for (int j = 0; j < multiBoardSize; j++) {
                if (boardStatus[i][j] == PLAYER1) {
                    kanjiButton[i][j].setIcon(player1Pane);
                } else if (boardStatus[i][j] == PLAYER2) {
                    kanjiButton[i][j].setIcon(player2Pane);
                } else if (boardStatus[i][j] == PLAYER3) {
                    kanjiButton[i][j].setIcon(player3Pane);
                } else if (boardStatus[i][j] == PLAYER4) {
                    kanjiButton[i][j].setIcon(player4Pane);
                } else if (boardStatus[i][j] == BOARD) {
                    kanjiButton[i][j].setIcon(defalutPanel);
                } else if (boardStatus[i][j] == CANSELECT) {
                    kanjiButton[i][j].setIcon(enablePanel);
                } else if (kanjiButton[i][j].isSelected()) {
                    boardStatus[i][j] = SELECT;
                }
            }
        }
    }

    public void startTimer() {
        timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLabel.getText().equals("0:00")) {
                    timer.stop();

					//サーバに時間終了を送りwaitする
					sceneController.answerData.setStatus(AnswerData.REQUIRE_RESULT);
					sceneController.socketClient.sendData((Data)sceneController.answerData);
					try {
						synchronized(sceneController.socketClient) {
							sceneController.socketClient.wait();
						}
						KanjiData kanjiData = (KanjiData)sceneController.socketClient.data;
                    	sceneController.moveResult(3, kanjiData.getResultScore());
					}
					catch(InterruptedException ie) {
						System.out.println(ie);
					}
                } else {
                    if (time / 60 <= 0) {
                        timeLabel.setForeground(Color.RED);
                    }
                    if (time % 60 >= 10) {
                        timeLabel.setText(Integer.toString(time / 60) + ":" + Integer.toString(time % 60));
                    } else {
                        timeLabel.setText(Integer.toString(time / 60) + ":0" + Integer.toString(time % 60));
                    }

                }
                time--;
            }
        });
        timer.start();
    }

    public void timerStop() {
        timer.stop();
    }
}
