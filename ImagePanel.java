import java.awt.*;
import javax.swing.*;

class ImagePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Image img;

    public ImagePanel() {
        this(new ImageIcon("images/background.png").getImage());
    }

    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public ImagePanel(Image img) {
        this.img = img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }
}