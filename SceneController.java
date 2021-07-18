import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.Clip;

/**
 * 画面遷移管理
 *
 * @author takao
 */

public class SceneController implements ActionListener {

    SocketClient socketClient;
    // KanjiData kanjiData;
    UserData userData;
    AnswerData answerData;
    // String[][] board;
    String[] board;

    private MainFrame mainFrame;
    private LoginFrame loginFrame;
    private RegisterFrame registerFrame;
    private SelectModeFrame selectModeFrame;
    private SingleModeFrame singleModeFrame;
    private MultiModeFrame multiModeFrame;
    private OneToOneModeFrame oneToOneModeFrame;
    private MultiGameFrame multiGameFrame;
    private SingleGameFrame singleGameFrame;
    private VsGameFrame vsGameFrame;
    private GameFrame beforeFrame;
    private GameFrame afterFrame;

    // sound
    Clip main;
    Clip correct;
    Clip incorrect;
    Clip animation;
    Clip battle;

    public SceneController(SocketClient socketClient, UserData userData) {
        this.socketClient = socketClient;
        this.userData = userData;
    }

    public static void main(String[] args) {

        SocketClient socketClient = new SocketClient();
        UserData userData = new UserData();
        socketClient.connectServer();

        SceneController sceneController = new SceneController(socketClient, userData);
        MainFrame mainFrame = new MainFrame(sceneController);
        sceneController.mainFrame = mainFrame;
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                mainFrame.setVisible(true);
            }
        });

        sceneController.main = CommonFrame.createClip(new File("sounds/lovelyflower.wav"));
        sceneController.main.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("start")) {

            loginFrame = new LoginFrame(this);
            TransitionAnimationPanel glassPane = new TransitionAnimationPanel(this);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            glassPane.setBounds(100, 100, (dimension.width) / 2 - 50, (dimension.height) / 2 - 50);
            mainFrame.setGlassPane(glassPane);
            glassPane.start();

            animation = CommonFrame.createClip(new File("sounds/bubble.wav"));
            animation.start();

        } else if (command.equals("login")) {

            System.out.println("login");
            selectModeFrame = new SelectModeFrame(this);
            if (loginFrame.getData()[0].equals("") && loginFrame.getData()[1].equals("")) {
                loginFrame.showError("null");
            } else if (loginFrame.getData()[0].equals("")) {
                loginFrame.showError("nullName");
            } else if (loginFrame.getData()[1].equals("")) {
                loginFrame.showError("nullPassword");
            }
            this.userData.setName(loginFrame.getData()[0]);
            this.userData.setPassword(loginFrame.getData()[1]);
            this.userData.setType(Data.USER);
            this.userData.setStatus(UserData.LOGIN);

            socketClient.sendData(userData);

            try {
                synchronized (socketClient) {
                    socketClient.wait();
                }

                if (socketClient.data.getStatus() == UserData.EXIST) {
                    FishAnimationPanel glassPane = new FishAnimationPanel(this, 2);
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    glassPane.setBounds(100, 100, (dimension.width) / 2 - 50, (dimension.height) / 2 - 50);
                    loginFrame.setGlassPane(glassPane);
                    glassPane.start();

                    animation = CommonFrame.createClip(new File("sounds/bubble.wav"));
                    animation.start();
                } else if (socketClient.data.getStatus() == UserData.EMPTY) {
                    System.out.println("userが存在しません");
                    loginFrame.showError("false");
                }
            } catch (InterruptedException ie) {
                System.err.println(ie);
            }

        } else if (command.equals("register")) {

            registerFrame = new RegisterFrame(this);
            FishAnimationPanel glassPane = new FishAnimationPanel(this, 3);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            glassPane.setBounds(100, 100, (dimension.width) / 2 - 50, (dimension.height) / 2 - 50);
            loginFrame.setGlassPane(glassPane);
            glassPane.start();

            animation = CommonFrame.createClip(new File("sounds/bubble.wav"));
            animation.start();

        } else if (command.equals("singleMode")) {

            singleModeFrame = new SingleModeFrame(this);
            FishAnimationPanel glassPane = new FishAnimationPanel(this, 4);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            glassPane.setBounds(100, 100, (dimension.width) / 2 - 50, (dimension.height) / 2 - 50);
            selectModeFrame.setGlassPane(glassPane);
            glassPane.start();

            animation = CommonFrame.createClip(new File("sounds/bubble.wav"));
            animation.start();

        } else if (command.equals("multiMode")) {

            multiModeFrame = new MultiModeFrame(this);
            FishAnimationPanel glassPane = new FishAnimationPanel(this, 5);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            glassPane.setBounds(100, 100, (dimension.width) / 2 - 50, (dimension.height) / 2 - 50);
            selectModeFrame.setGlassPane(glassPane);
            glassPane.start();

            animation = CommonFrame.createClip(new File("sounds/bubble.wav"));
            animation.start();

        } else if (command.equals("vsMode")) {

            oneToOneModeFrame = new OneToOneModeFrame(this);
            FishAnimationPanel glassPane = new FishAnimationPanel(this, 6);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            glassPane.setBounds(100, 100, (dimension.width) / 2 - 50, (dimension.height) / 2 - 50);
            selectModeFrame.setGlassPane(glassPane);
            glassPane.start();

            animation = CommonFrame.createClip(new File("sounds/bubble.wav"));
            animation.start();
        } else if (command.equals("create")) {

            if (registerFrame.getData()[0].equals("") && registerFrame.getData()[1].equals("")) {
                registerFrame.showError("null");
            } else if (registerFrame.getData()[0].equals("")) {
                registerFrame.showError("nullName");
            } else if (registerFrame.getData()[1].equals("")) {
                registerFrame.showError("nullPassword");
            } else if (!registerFrame.checkPass()[0].equals(registerFrame.checkPass()[1])) {
                registerFrame.showError("check");
            } else {
                selectModeFrame = new SelectModeFrame(this);

                this.userData.setName(registerFrame.getData()[0]);
                this.userData.setPassword(registerFrame.getData()[1]);
                this.userData.setType(Data.USER);
                this.userData.setStatus(UserData.CREATE);

                socketClient.sendData(userData);

                // FIXME
                System.out.println("Userのステータス：" + userData.getStatus());
                try {
                    synchronized (socketClient) {
                        socketClient.wait();
                    }

                    if (socketClient.data.getStatus() == UserData.ALREADY_EXIST) {
                        System.out.println("すでにそのユーザー名は使われています");
                        registerFrame.showError("already");
                    } else if (socketClient.data.getStatus() == UserData.CREATE_SUCCESS) {
                        System.out.println("ユーザ作成に成功しました");
                        FishAnimationPanel glassPane = new FishAnimationPanel(this, 7);
                        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                        glassPane.setBounds(100, 100, (dimension.width) / 2 - 50, (dimension.height) / 2 - 50);
                        registerFrame.setGlassPane(glassPane);
                        glassPane.start();

                        animation = CommonFrame.createClip(new File("sounds/bubble.wav"));
                        animation.start();
                        // showError("falseUser");
                    }
                } catch (InterruptedException ie) {
                    System.err.println(ie);
                }

            }

        } else if (command.equals("backhome1")) {

            mainFrame = new MainFrame(this);
            moveScene(loginFrame, mainFrame);

        } else if (command.equals("backhome2")) {

            mainFrame = new MainFrame(this);
            moveScene(registerFrame, mainFrame);

        } else if (command.equals("backhome3")) {

            mainFrame = new MainFrame(this);
            moveScene(selectModeFrame, mainFrame);

        } else if (command.equals("backSelect1")) {

            selectModeFrame = new SelectModeFrame(this);
            moveScene(multiModeFrame, selectModeFrame);

        } else if (command.equals("backSelect2")) {

            selectModeFrame = new SelectModeFrame(this);
            moveScene(singleModeFrame, selectModeFrame);

        } else if (command.equals("backSelect3")) {

            selectModeFrame = new SelectModeFrame(this);
            moveScene(oneToOneModeFrame, selectModeFrame);

        } else if (command.equals("backlogin")) {

            loginFrame = new LoginFrame(this);
            moveScene(registerFrame, loginFrame);

        } else if (command.equals("multi")) {

            multiGameFrame = new MultiGameFrame(this);

            // 対戦時にupdateに必要
            socketClient.multiGameFrame = multiGameFrame;
            socketClient.sceneController = this;
            socketClient.multiModeFrame = multiModeFrame;

            // 送信用のanswerData
            answerData = new AnswerData(Data.MULTI_MODE_ANSWER, "0", "");
            answerData.setStatus(AnswerData.INIT);
            System.out.println("multiゲームスタートリクエスト");
            socketClient.sendData((Data) answerData);

            try {
                synchronized (socketClient) {
                    socketClient.wait();
                }

                KanjiData kanjiData = (KanjiData) socketClient.data;

                if (kanjiData.getStatus() == KanjiData.MULTI_STARTER) {
                    multiGameFrame.setBoard(kanjiData.getBoard());
                    moveGame(multiModeFrame, multiGameFrame);
                    multiGameFrame.startTimer();

                    // 音楽スタート
                    battle = GameFrame.createClip(new File("sounds/battle.wav"));
                    main.stop();
                    main.flush();
                    main.setFramePosition(0);
                    battle.loop(Clip.LOOP_CONTINUOUSLY);
                } else if (kanjiData.getStatus() == KanjiData.MULTI_WAIT) {
                    // 待機画面
                    InfiniteProgressPanel glassPane = new InfiniteProgressPanel("他のユーザを探しています…");
                    socketClient.glassPane = glassPane;
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    glassPane.setBounds(100, 100, (dimension.width) / 2 - 50, (dimension.height) / 2 - 50);
                    multiModeFrame.setGlassPane(glassPane);
                    glassPane.start();

                    synchronized (socketClient) {
                        socketClient.wait();
                    }
                    moveGame(multiModeFrame, multiGameFrame);
                    multiGameFrame.startTimer();
                }

            } catch (InterruptedException ie) {
                System.out.println(ie);
            }

            /**
             * if(MultiData.member.length>=2) moveScene(checkFrame,multiModeFrame);
             */

        } else if (command.equals("single")) {

            singleGameFrame = new SingleGameFrame(this);
            // 送信用のanswerData
            answerData = new AnswerData(Data.SINGLE_MODE_ANSWER, "0", "");
            answerData.setStatus(AnswerData.INIT);
            System.out.println("singleゲームスタートリクエスト");
            socketClient.sendData((Data) answerData);

            try {
                synchronized (socketClient) {
                    socketClient.wait();
                }

                KanjiData kanjiData = (KanjiData) socketClient.data;
                singleGameFrame.setBoard(kanjiData.getBoard());
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
            moveGame(singleModeFrame, singleGameFrame);
            singleGameFrame.startTimer();

            battle = GameFrame.createClip(new File("sounds/battle.wav"));
            main.stop();
            main.flush();
            main.setFramePosition(0);
            battle.loop(Clip.LOOP_CONTINUOUSLY);

        } else if (command.equals("vs")) {

            vsGameFrame = new VsGameFrame(this);
            moveGame(oneToOneModeFrame, vsGameFrame);
            vsGameFrame.startTimer();

            battle = GameFrame.createClip(new File("sounds/battle.wav"));
            main.stop();
            main.flush();
            main.setFramePosition(0);
            battle.loop(Clip.LOOP_CONTINUOUSLY);

        } else if (command.equals("answer")) {
            // System.out.println(multiGameFrame.selectedButton());
            String[] answer = multiGameFrame.getAnswer();
            answerData = new AnswerData(Data.MULTI_MODE_ANSWER, answer[0], answer[1]);
            answerData.setStatus(AnswerData.NOT_INIT);
            socketClient.sendData((Data) answerData);

            try {
                synchronized (socketClient) {
                    socketClient.wait();
                }
                KanjiData kanjiData = (KanjiData) socketClient.data;
                AnswerAnimationPanel glassPane = new AnswerAnimationPanel(this, kanjiData.getStatus());
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                glassPane.setBounds(100, 100, (dimension.width) / 2 - 50, (dimension.height) / 2 - 50);
                multiGameFrame.setGlassPane(glassPane);
                glassPane.start();

                if (kanjiData.getStatus() == 1) {
                    correct = GameFrame.createClip(new File("sounds/correct1.wav"));
                    correct.start();
                } else if (kanjiData.getStatus() == 0) {
                    incorrect = GameFrame.createClip(new File("sounds/incorrect1.wav"));
                    incorrect.start();
                }

                multiGameFrame.updateBoard(kanjiData.getBoard());
                multiGameFrame.updateScore(kanjiData.getScore());
                multiGameFrame.updateBoardPosition(kanjiData.getBoardPosition());

            } catch (InterruptedException ie) {
                System.out.println(ie);
            }

            multiGameFrame.reset();

        } else if (command.equals("answer1")) {
            // System.out.println(multiGameFrame.selectedButton());
            String[] answer = singleGameFrame.getAnswer();
            answerData = new AnswerData(Data.SINGLE_MODE_ANSWER, answer[0], answer[1]);
            answerData.setStatus(AnswerData.NOT_INIT);
            socketClient.sendData((Data) answerData);

            try {
                synchronized (socketClient) {
                    socketClient.wait();
                }
                KanjiData kanjiData = (KanjiData) socketClient.data;
                AnswerAnimationPanel glassPane = new AnswerAnimationPanel(this, kanjiData.getStatus());
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                glassPane.setBounds(100, 100, (dimension.width) / 2 - 50, (dimension.height) / 2 - 50);
                singleGameFrame.setGlassPane(glassPane);
                glassPane.start();

                if (kanjiData.getStatus() == KanjiData.GOOD) {
                    correct = GameFrame.createClip(new File("sounds/correct1.wav"));
                    correct.start();
                } else if (kanjiData.getStatus() == KanjiData.BAD) {
                    incorrect = GameFrame.createClip(new File("sounds/incorrect1.wav"));
                    incorrect.start();
                }
                singleGameFrame.updateScore(kanjiData.getScore());
                singleGameFrame.updateBoard(kanjiData.getBoard());
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }

            singleGameFrame.reset();

        } else if (command.equals("back_home")) {

            Component comp = (Component) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);

            Color bg = new Color(123, 63, 0);
            UIManager.put("OptionPane.background", bg);
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.getLookAndFeelDefaults().put("Panel.background", bg);

            VectorButton yesButton = new VectorButton();
            yesButton.setText("   はい   ");
            yesButton.setBorderPainted(false);
            yesButton.addActionListener(e1 -> {
                win.dispose();
                mainFrame = new MainFrame(this);
                checkMainFrame();
                /*
                 * if (multiGameFrame != null) { backScene(multiGameFrame, mainFrame);
                 * multiGameFrame.timerStop(); } else if (vsGameFrame != null) {
                 * backScene(vsGameFrame, mainFrame); vsGameFrame.timerStop(); } else if
                 * (singleGameFrame != null) { backScene(singleGameFrame, mainFrame);
                 * singleGameFrame.timerStop(); }
                 */
            });

            VectorButton noButton = new VectorButton();
            noButton.setText(" いいえ ");
            noButton.setBorderPainted(false);
            noButton.addActionListener(e2 -> {
                win.dispose();
            });
            VectorButton[] buttons = { yesButton, noButton };

            JOptionPane.showOptionDialog(win, "本当に戻りますか？（途中でやめることとなります）", "注意！", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, buttons, buttons[0]);

        } else if (command.equals("back_select")) {

            Component comp = (Component) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);

            Color bg = new Color(123, 63, 0);
            UIManager.put("OptionPane.background", bg);
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.getLookAndFeelDefaults().put("Panel.background", bg);

            VectorButton yesButton = new VectorButton();
            yesButton.setText("   はい   ");
            yesButton.setBorderPainted(false);
            yesButton.addActionListener(e3 -> {
                win.dispose();
                selectModeFrame = new SelectModeFrame(this);
                checkSelectFrame();
                /*
                 * if (multiGameFrame != null) { backScene(multiGameFrame, selectModeFrame);
                 * multiGameFrame.timerStop(); } else if (vsGameFrame != null) {
                 * backScene(vsGameFrame, selectModeFrame); vsGameFrame.timerStop(); } else if
                 * (singleGameFrame != null) { backScene(singleGameFrame, selectModeFrame);
                 * singleGameFrame.timerStop(); }
                 */
            });

            VectorButton noButton = new VectorButton();
            noButton.setText(" いいえ ");
            noButton.setBorderPainted(false);
            noButton.addActionListener(e4 -> {
                win.dispose();
            });
            VectorButton[] buttons = { yesButton, noButton };

            JOptionPane.showOptionDialog(win, "本当に戻りますか？（途中でやめることとなります）", "注意！", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, buttons, buttons[0]);

        } else if (command.equals("how_to_play")) {
            Component comp = (Component) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);
            win.dispose();
            HowToFrame howToFrame = new HowToFrame("遊び方", new Rectangle(100, 100, 500, 300));
            howToFrame.setVisible(true);
        } else if (command.equals("credit")) {

        } else if (command.equals("backhome_result")) {
            mainFrame = new MainFrame(this);
            backScene(afterFrame, mainFrame);
        } else if (command.equals("backselect_result")) {
            selectModeFrame = new SelectModeFrame(this);
            backScene(afterFrame, selectModeFrame);
        } else {
            System.out.println(command);
            multiGameFrame.reset();

        }
    }

    public void moveScene(CommonFrame beforeFrame, CommonFrame afterFrame) {
        Rectangle rectangle = beforeFrame.getBounds();
        afterFrame.setBounds((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(),
                (int) rectangle.getHeight());

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                afterFrame.setVisible(true);
                beforeFrame.dispose();
            }
        });
    }

    public void moveGame(CommonFrame beforeFrame, GameFrame afterFrame) {
        Rectangle rectangle = beforeFrame.getBounds();
        afterFrame.setBounds((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(),
                (int) rectangle.getHeight());

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                afterFrame.setVisible(true);
                beforeFrame.dispose();
            }
        });
    }

    public void backScene(GameFrame beforeFrame, CommonFrame afterFrame) {
        Rectangle rectangle = beforeFrame.getBounds();
        afterFrame.setBounds((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(),
                (int) rectangle.getHeight());

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                afterFrame.setVisible(true);
                beforeFrame.dispose();
            }
        });

        battle.stop();
        battle.flush();
        battle.setFramePosition(0);
        main = CommonFrame.createClip(new File("sounds/lovelyflower.wav"));
        main.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void checkSelectFrame() {
        try {
            backScene(multiGameFrame, selectModeFrame);
            multiGameFrame.timerStop();

        } catch (Exception e) {
            System.out.println("multiGameFrame is null");
        }
        try {
            backScene(vsGameFrame, selectModeFrame);
            vsGameFrame.timerStop();
        } catch (Exception e) {
            System.out.println("vsGameFrame is null");
        }
        try {
            backScene(singleGameFrame, selectModeFrame);
            singleGameFrame.timerStop();
        } catch (Exception e) {
            System.out.println("singleGameFrame is null");
        }

    }

    public void checkMainFrame() {
        try {
            backScene(multiGameFrame, mainFrame);
            multiGameFrame.timerStop();

        } catch (Exception e2) {
            System.out.println("multiGameFrame is null");
        }
        try {
            backScene(vsGameFrame, mainFrame);
            vsGameFrame.timerStop();
        } catch (Exception e) {
            System.out.println("vsGameFrame is null");
        }
        try {
            backScene(singleGameFrame, mainFrame);
            singleGameFrame.timerStop();
        } catch (Exception e) {
            System.out.println("singleGameFrame is null");
        }
    }

    public void moveAnimation(int mode) {
        switch (mode) {
        case 1:
            moveScene(mainFrame, loginFrame);
            break;
        case 2:
            moveScene(loginFrame, selectModeFrame);
            break;
        case 3:
            moveScene(loginFrame, registerFrame);
            break;
        case 4:
            moveScene(selectModeFrame, singleModeFrame);
            break;
        case 5:
            moveScene(selectModeFrame, multiModeFrame);
            break;
        case 6:
            moveScene(selectModeFrame, oneToOneModeFrame);
            break;
        case 7:
            moveScene(registerFrame, selectModeFrame);
            break;
        }
    }

    public void moveResult(int mode, int[] resultScore) {

        sortScore(resultScore);
        switch (mode) {
        case 1:
            beforeFrame = singleGameFrame;
            afterFrame = new ResultFrame(this, 1, resultScore);
            break;
        case 2:
            beforeFrame = vsGameFrame;
            afterFrame = new ResultFrame(this, 2, resultScore);
            break;
        case 3:
            beforeFrame = multiGameFrame;
            afterFrame = new ResultFrame(this, 3, resultScore);

        }
        Rectangle rectangle = beforeFrame.getBounds();
        afterFrame.setBounds((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(),
                (int) rectangle.getHeight());

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                afterFrame.setVisible(true);
                beforeFrame.dispose();
            }
        });
        battle.stop();
        battle.flush();
        battle.setFramePosition(0);
    }

    public int[] sortScore(int[] resultScore) {
        for (int i = 0; i < resultScore.length - 1; i++) {
            for (int j = 0; j < resultScore.length - i - 1; j++) {
                if (resultScore[j] < resultScore[j + 1]) {
                    int asc = resultScore[j];
                    resultScore[j] = resultScore[j + 1];
                    resultScore[j + 1] = asc;
                }
            }
        }
        return resultScore;
    }
}
