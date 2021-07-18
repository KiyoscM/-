import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;

public class LoginFrame extends CommonFrame {

    private static final long serialVersionUID = 1L;

    private SceneController sceneController;
    private JTextField userNameTextField;
    private JPasswordField passwordField;
    private JLabel errorNameLabel;
    private JLabel errorPassLabel;

    public static final int SHAKE_DISTANCE = 10;
    public static final double SHAKE_CYCLE = 50;
    public static final int SHAKE_DURATION = 300;
    public static final int SHAKE_UPDATE = 5;

    private Point naturalLocation;
    private long startTime;
    private Timer shakeTimer;
    private final double TWO_PI = Math.PI * 2.0;
    private boolean isShake = false;

    public LoginFrame(SceneController sceneController) {
        super("漢字物語/login", new Rectangle(100, 100, 600, 400), 2);

        this.sceneController = sceneController;

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                initLoginUI();
            }
        });
    }

    protected void initLoginUI() {
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackground());

        JPanel loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(500, 140));
        loginPanel.setBackground(getBackgroundColor());

        Font font = null;
        Font font2 = new Font("メイリオ", Font.BOLD, 30);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/AmeChanPopMaruTTFLight-Regular.ttf"))
                    .deriveFont(Font.CENTER_BASELINE, 25f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RichJLabel userNameJLabel = new RichJLabel("ユーザ名", 8);
        userNameJLabel.setLeftShadow(1, 1, Color.WHITE);
        userNameJLabel.setRightShadow(2, 3, Color.BLACK);
        userNameJLabel.setForeground(new Color(255, 255, 255));
        if (font != null)
            userNameJLabel.setFont(font);
        userNameJLabel.setHorizontalAlignment(JLabel.CENTER);
        userNameJLabel.setPreferredSize(new Dimension(250, 40));

        userNameTextField = new JTextField("");
        userNameTextField.setPreferredSize(new Dimension(250, 40));
        userNameTextField.addActionListener(this.sceneController);
        userNameTextField.setFont(font2);
        userNameTextField.setBackground(new Color(0, 255, 255));
        userNameTextField.setActionCommand("login");

        errorNameLabel = new JLabel("");
        errorNameLabel.setForeground(new Color(255, 0, 0));
        errorNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        errorNameLabel.setPreferredSize(new Dimension(400, 20));

        RichJLabel passwordJLabel = new RichJLabel("パスワード", 0);
        passwordJLabel.setRightShadow(1, 1, Color.WHITE);
        passwordJLabel.setRightShadow(2, 3, Color.BLACK);
        passwordJLabel.setForeground(new Color(255, 255, 255));
        if (font != null) {
            passwordJLabel.setFont(font);
        }
        passwordJLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordJLabel.setPreferredSize(new Dimension(250, 40));
        passwordJLabel.setBackground(getBackgroundColor());

        passwordField = new JPasswordField("");
        passwordField.setPreferredSize(new Dimension(250, 40));
        passwordField.addActionListener(this.sceneController);
        passwordField.setFont(font2);
        passwordField.setBackground(new Color(0, 255, 255));
        passwordField.setActionCommand("login");

        errorPassLabel = new JLabel("");
        errorPassLabel.setForeground(new Color(255, 0, 0));
        errorPassLabel.setHorizontalAlignment(JLabel.RIGHT);
        errorPassLabel.setPreferredSize(new Dimension(400, 20));

        loginPanel.add(userNameJLabel);
        loginPanel.add(userNameTextField);
        loginPanel.add(errorNameLabel);

        loginPanel.add(passwordJLabel);
        loginPanel.add(passwordField);
        loginPanel.add(errorPassLabel);

        JPanel submitPanel = new JPanel();
        submitPanel.setPreferredSize(new Dimension(600, 60));

        JButton submitButton = new JButton(new ImageIcon("images/buttonlogin.png"));
        submitButton.setOpaque(false);
        submitButton.setContentAreaFilled(false);
        submitButton.setBorderPainted(false);
        submitButton.setPreferredSize(new Dimension(300, 60));
        submitButton.addActionListener(this.sceneController);
        submitButton.setActionCommand("login");

        submitPanel.add(submitButton);

        JPanel registerPanel = new JPanel();
        registerPanel.setPreferredSize(new Dimension(600, 60));

        JButton registerButton = new JButton(new ImageIcon("images/buttonCreate2.png"));
        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(new Dimension(300, 60));
        registerButton.addActionListener(this.sceneController);
        registerButton.setActionCommand("register");

        registerPanel.add(registerButton);

        JPanel backPanel = new JPanel();
        backPanel.setPreferredSize(new Dimension(600, 60));
        // backPanel.setLayout(new BorderLayout());

        JButton backButton = new JButton(new ImageIcon("images/buttonhome.png"));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(300, 60));
        backButton.addActionListener(this.sceneController);
        backButton.setActionCommand("backhome1");

        backPanel.add(backButton);

        HintAction hintAction = new HintAction(this.sceneController);
        JButton hintButton = new JButton(hintAction);
        hintButton.setIcon(new ImageIcon("images/hintButton.png"));
        hintButton.setOpaque(false);
        hintButton.setContentAreaFilled(false);
        hintButton.setBorderPainted(false);
        hintButton.setBounds(480, 290, 50, 50);

        backPanel.setOpaque(false);
        wholePanel.setOpaque(false);
        loginPanel.setOpaque(false);
        submitPanel.setOpaque(false);
        registerPanel.setOpaque(false);

        wholePanel.add(loginPanel);
        wholePanel.add(submitPanel);
        wholePanel.add(registerPanel);
        wholePanel.add(backPanel);

        commonBackgroundPanel.add(hintButton);
        commonBackgroundPanel.add(wholePanel, BorderLayout.CENTER);

    }

    public String[] getData() {
        String[] data = { "", "" };
        data[0] = userNameTextField.getText();
        char[] pass = passwordField.getPassword();
        data[1] = new String(pass);
        return data;
    }

    public void showError(String message) {
        if (message.equals("null")) {

            errorNameLabel.setText("※名前が入力されていません");
            errorPassLabel.setText("※パスワードが入力されていません");
            startShake();

        } else if (message.equals("nullName")) {
            errorNameLabel.setText("※名前が入力されていません");
            errorPassLabel.setText("");
            startShake();
        } else if (message.equals("nullPassword")) {
            errorNameLabel.setText("");
            errorPassLabel.setText("※パスワードが入力されていません");
            startShake();
        } else if (message.equals("false")) {
            errorNameLabel.setText("※ユーザ名、もしくはパスワードが違います");
            errorPassLabel.setText("");
            startShake();
        }
    }

    public void startShake() {
        naturalLocation = new Point(0, 0);
        startTime = System.currentTimeMillis();
        shakeTimer = new Timer(SHAKE_UPDATE, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsed = System.currentTimeMillis() - startTime;
                double waveOffset = (elapsed % SHAKE_CYCLE) / SHAKE_CYCLE;
                double angle = waveOffset * TWO_PI;
                int shakenX = (int) ((Math.sin(angle) * SHAKE_DISTANCE) + naturalLocation.x);
                commonBackgroundPanel.setLocation(shakenX, naturalLocation.y);
                commonBackgroundPanel.repaint();

                if (elapsed >= SHAKE_DURATION) {
                    stopShake();
                    isShake = false;
                }

            }
        });
        if (!isShake) {
            isShake = true;
            shakeTimer.start();
        }
    }

    public void stopShake() {
        shakeTimer.stop();
        commonBackgroundPanel.setLocation(naturalLocation);
        commonBackgroundPanel.repaint();
    }

}
