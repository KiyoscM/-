import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends CommonFrame {
    private static final long serialVersionUID = 1l;

    private SceneController sceneController;
    private JTextField newUserNameField;
    private JPasswordField newPasswordField;
    private JLabel errorNameLabel;
    private JLabel errorPassLabel;
    private JLabel errorCheckPassLabel;
    private JPasswordField checkPasswordField;

    public static final int SHAKE_DISTANCE = 10;
    public static final double SHAKE_CYCLE = 50;
    public static final int SHAKE_DURATION = 300;
    public static final int SHAKE_UPDATE = 5;

    private Point naturalLocation;
    private long startTime;
    private Timer shakeTimer;
    private final double TWO_PI = Math.PI * 2.0;

    public RegisterFrame(SceneController sceneController) {
        super("漢字物語/register", new Rectangle(100, 100, 600, 400), 4);

        this.sceneController = sceneController;

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                initRegisterUI();
            }
        });
    }

    protected void initRegisterUI() {
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());
        wholePanel.setOpaque(false);

        JPanel registPanel = new JPanel();
        registPanel.setPreferredSize(new Dimension(550, 210));
        registPanel.setOpaque(false);

        Font font = null;
        Font font2 = new Font("メイリオ", Font.BOLD, 30);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/AmeChanPopMaruTTFLight-Regular.ttf"))
                    .deriveFont(Font.CENTER_BASELINE, 20f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RichJLabel newUserNameLabel = new RichJLabel("ユーザ名", 33);
        newUserNameLabel.setRightShadow(2, 3, Color.BLACK);
        newUserNameLabel.setLeftShadow(1, 1, Color.WHITE);
        newUserNameLabel.setForeground(new Color(255, 255, 255));
        if (font != null)
            newUserNameLabel.setFont(font);

        newUserNameLabel.setHorizontalAlignment(JLabel.CENTER);
        newUserNameLabel.setPreferredSize(new Dimension(250, 40));
        newUserNameLabel.setOpaque(false);

        RichJLabel newPasswordLabel = new RichJLabel("パスワード", 21);
        newPasswordLabel.setRightShadow(2, 3, Color.BLACK);
        newPasswordLabel.setLeftShadow(1, 1, Color.WHITE);
        newPasswordLabel.setForeground(new Color(255, 255, 255));
        if (font != null)
            newPasswordLabel.setFont(font);

        newPasswordLabel.setHorizontalAlignment(JLabel.CENTER);
        newPasswordLabel.setPreferredSize(new Dimension(250, 40));
        newPasswordLabel.setOpaque(false);

        RichJLabel checkPasswordLabel = new RichJLabel("パスワードかくにん", 0);
        checkPasswordLabel.setRightShadow(2, 3, Color.BLACK);
        checkPasswordLabel.setLeftShadow(1, 1, Color.WHITE);
        checkPasswordLabel.setForeground(new Color(255, 255, 255));
        if (font != null)
            checkPasswordLabel.setFont(font);
        checkPasswordLabel.setHorizontalAlignment(JLabel.CENTER);
        checkPasswordLabel.setPreferredSize(new Dimension(250, 40));
        checkPasswordLabel.setOpaque(false);

        newUserNameField = new JTextField("");
        newUserNameField.setPreferredSize(new Dimension(250, 40));
        newUserNameField.setBackground(new Color(0, 255, 255));
        newUserNameField.addActionListener(this.sceneController);
        newUserNameField.setFont(font2);
        newUserNameField.setActionCommand("create");

        errorNameLabel = new JLabel("");
        errorNameLabel.setForeground(new Color(255, 0, 0));
        errorNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        errorNameLabel.setPreferredSize(new Dimension(400, 20));

        newPasswordField = new JPasswordField("");
        newPasswordField.setPreferredSize(new Dimension(250, 40));
        newPasswordField.setBackground(new Color(0, 255, 255));
        newPasswordField.addActionListener(this.sceneController);
        newPasswordField.setFont(font2);
        newPasswordField.setActionCommand("create");

        errorPassLabel = new JLabel("");
        errorPassLabel.setForeground(new Color(255, 0, 0));
        errorPassLabel.setHorizontalAlignment(JLabel.RIGHT);
        errorPassLabel.setPreferredSize(new Dimension(400, 20));

        checkPasswordField = new JPasswordField();
        checkPasswordField.setPreferredSize(new Dimension(250, 40));
        checkPasswordField.setBackground(new Color(0, 255, 255));
        checkPasswordField.addActionListener(this.sceneController);
        checkPasswordField.setFont(font2);
        checkPasswordField.setActionCommand("create");

        errorCheckPassLabel = new JLabel("");
        errorCheckPassLabel.setForeground(new Color(255, 0, 0));
        errorCheckPassLabel.setHorizontalAlignment(JLabel.RIGHT);
        errorCheckPassLabel.setPreferredSize(new Dimension(400, 20));

        JPanel createPanel = new JPanel();
        createPanel.setPreferredSize(new Dimension(600, 60));
        createPanel.setOpaque(false);

        JButton createButton = new JButton(new ImageIcon("images/buttonUser.png"));
        createButton.setOpaque(false);
        createButton.setContentAreaFilled(false);
        createButton.setBorderPainted(false);
        createButton.setPreferredSize(new Dimension(300, 60));
        createButton.addActionListener(this.sceneController);
        createButton.setActionCommand("create");

        JPanel backPanel = new JPanel();
        backPanel.setPreferredSize(new Dimension(600, 60));
        backPanel.setOpaque(false);

        JButton backButton = new JButton(new ImageIcon("images/buttonHome.png"));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(300, 60));
        backButton.addActionListener(this.sceneController);
        backButton.setActionCommand("backhome2");

        HintAction hintAction = new HintAction(this.sceneController);
        JButton hintButton = new JButton(hintAction);
        hintButton.setIcon(new ImageIcon("images/hintButton.png"));
        hintButton.setOpaque(false);
        hintButton.setContentAreaFilled(false);
        hintButton.setBorderPainted(false);
        hintButton.setBounds(480, 290, 50, 50);

        JButton backButton2 = new CustomJButton();
        backButton2.setToolTipText("戻る");
        backButton2.setIcon(new ImageIcon("images/backButton2.png"));
        backButton2.setOpaque(false);
        backButton2.setContentAreaFilled(false);
        backButton2.setBorderPainted(false);
        backButton2.setBounds(50, 290, 50, 50);
        backButton2.addActionListener(this.sceneController);
        backButton2.setActionCommand("backlogin");

        backPanel.add(backButton);
        createPanel.add(createButton);

        registPanel.add(newUserNameLabel);
        registPanel.add(newUserNameField);
        registPanel.add(errorNameLabel);

        registPanel.add(newPasswordLabel);
        registPanel.add(newPasswordField);
        registPanel.add(errorPassLabel);

        registPanel.add(checkPasswordLabel);
        registPanel.add(checkPasswordField);
        registPanel.add(errorCheckPassLabel);

        wholePanel.add(registPanel);
        wholePanel.add(createPanel);
        wholePanel.add(backButton);

        commonBackgroundPanel.add(backButton2);
        commonBackgroundPanel.add(hintButton);
        commonBackgroundPanel.add(wholePanel, BorderLayout.CENTER);
    }

    public String[] getData() {
        String[] data = { "", "" };
        data[0] = newUserNameField.getText();
        char[] pass = newPasswordField.getPassword();
        data[1] = new String(pass);
        return data;
    }

    public String[] checkPass() {
        String[] checker = { "", "" };
        char[] pass = newPasswordField.getPassword();
        checker[0] = new String(pass);
        char[] check = checkPasswordField.getPassword();
        checker[1] = new String(check);
        return checker;
    }

    public void showError(String message) {
        if (message.equals("null")) {

            errorNameLabel.setText("※名前が入力されていません");
            errorPassLabel.setText("※パスワードが入力されていません");
            errorCheckPassLabel.setText("");
            startShake();

        } else if (message.equals("nullName")) {

            errorNameLabel.setText("※名前が入力されていません");
            errorPassLabel.setText("");
            errorCheckPassLabel.setText("");
            startShake();

        } else if (message.equals("nullPassword")) {

            errorNameLabel.setText("");
            errorPassLabel.setText("※パスワードが入力されていません");
            errorCheckPassLabel.setText("");
            startShake();

        } else if (message.equals("check")) {

            errorPassLabel.setText("");
            errorNameLabel.setText("");
            errorCheckPassLabel.setText("※パスワードが一致していません");
            startShake();

        } else if (message.equals("already")) {
            errorPassLabel.setText("");
            errorNameLabel.setText("※既に存在するユーザ名です");
            errorCheckPassLabel.setText("");
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
                }
            }
        });
        shakeTimer.start();
    }

    public void stopShake() {
        shakeTimer.stop();
        commonBackgroundPanel.setLocation(naturalLocation);
        commonBackgroundPanel.repaint();
    }
}
