import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Dialog.ModalityType;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class HintAction extends AbstractAction {
    private static final long serialVersionUID = 1L;
    private static final int ALPHA = 175; // how much see-thru. 0 to 255
    private static final Color GP_BG = new Color(0, 0, 0, ALPHA);
    private SceneController sceneController;
    private HintDialogPanel hintDialogPanel; // jpanel shown in JDialog

    public HintAction(SceneController sceneController) {
        super();
        this.sceneController = sceneController;
        hintDialogPanel = new HintDialogPanel(sceneController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // comp is our JButton
        Component comp = (Component) e.getSource();
        if (comp == null) {
            return;
        }

        // create our glass pane
        JPanel glassPane = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                // magic to make it dark without side-effects
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        // more magic below
        glassPane.setOpaque(false);
        glassPane.setBackground(GP_BG);

        // get the rootpane container, here the JFrame, that holds the JButton
        RootPaneContainer win = (RootPaneContainer) SwingUtilities.getWindowAncestor(comp);
        win.setGlassPane(glassPane); // set the glass pane
        glassPane.setVisible(true); // and show the glass pane

        // create a *modal* JDialog
        JDialog dialog = new JDialog((Window) win, "", ModalityType.APPLICATION_MODAL);
        dialog.getContentPane().add(hintDialogPanel); // add its JPanel to it
        dialog.setUndecorated(true); // give it no borders (if desired)
        dialog.pack(); // size it
        dialog.setLocationRelativeTo((Window) win); // ** Center it over the JFrame **
        dialog.setVisible(true); // display it, pausing the GUI below it

        // at this point the dialog is no longer visible, so get rid of glass pane
        glassPane.setVisible(false);

    }
}

// JPanel shown in the modal JDialog above
class HintDialogPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static final Color BG = new Color(123, 63, 0);
    private SceneController sceneController;

    public HintDialogPanel(SceneController sceneController) {
        this.sceneController = sceneController;
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                initDeDialogUI();
            }
        });

    }

    protected void initDeDialogUI() {

        RichJLabel pausedLabel = new RichJLabel("HELP", 0);
        pausedLabel.setLeftShadow(1, 1, Color.WHITE);
        pausedLabel.setRightShadow(2, 3, Color.BLACK);
        pausedLabel.setForeground(Color.ORANGE);
        pausedLabel.setFont(pausedLabel.getFont().deriveFont(30f));

        JPanel pausedPanel = new JPanel();
        pausedPanel.setOpaque(false);
        pausedPanel.add(pausedLabel);

        setBackground(BG);
        int eb = 40;
        setBorder(BorderFactory.createEmptyBorder(eb, eb, eb, eb));
        setLayout(new GridLayout(0, 1, 10, 10));
        add(pausedPanel);

        VectorButton howtoButton = new VectorButton();
        howtoButton.setText("遊び方");
        howtoButton.setBorderPainted(false);
        howtoButton.addActionListener(this.sceneController);
        howtoButton.setActionCommand("how_to_play");

        VectorButton creditButton = new VectorButton();
        creditButton.setText("クレジット");
        creditButton.setBorderPainted(false);
        creditButton.addActionListener(this.sceneController);
        creditButton.setActionCommand("credit");

        VectorButton continueButton = new VectorButton();
        continueButton.setText("閉じる");
        continueButton.setBorderPainted(false);
        continueButton.addActionListener(this);

        add(howtoButton);

        add(creditButton);
        add(continueButton);
    }

    public void actionPerformed(ActionEvent e) {
        Component comp = (Component) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();

    }

}