import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

class CustomToolTip extends JToolTip {
    private static final long serialVersionUID = 1L;

    public CustomToolTip() {
        super();
        this.setOpaque(false);
    }

    public void paintComponent(Graphics g) {

        Component parent = this.getParent();
        if (parent != null) {
            if (parent instanceof JComponent) {
                JComponent jparent = (JComponent) parent;
                if (jparent.isOpaque()) {
                    jparent.setOpaque(false);
                }
            }
        }
        Shape round = new RoundRectangle2D.Float(4, 4, this.getWidth() - 1 - 8, this.getHeight() - 1 - 8, 15, 15);

        // 青の背景
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(20, 126, 219));
        g2.fill(round);

        // 橙のボーダー
        g2.setColor(new Color(219, 146, 20));
        g2.setStroke(new BasicStroke(3));
        g2.draw(round);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);

        String text = this.getComponent().getToolTipText();
        if (text != null) {
            Font f = new Font("メイリオ", Font.BOLD, 15);
            FontMetrics fm = g2.getFontMetrics(f);
            int h = fm.getAscent();
            g2.setColor(Color.WHITE);
            g2.drawString(text, 14, (this.getHeight() + h) / 2 - 3);
        }

    }

    public Dimension getPreferredSize() {
        Dimension dim = super.getPreferredSize();
        return new Dimension((int) dim.getWidth() + 20, (int) dim.getHeight() + 20);
    }
}

class CustomJButton extends JButton {
    JToolTip _tooltip;
    private static final long serialVersionUID = 1L;

    public CustomJButton() {
        _tooltip = new CustomToolTip();
        _tooltip.setComponent(this);
    }

    public JToolTip createToolTip() {
        return _tooltip;
    }
}