import java.awt.*;

public class VenetianPane extends TransitionTabbedPane {
    private static final long serialVersionUID = 1L;

    public void paintTransition(Graphics2D g2, int step, Rectangle size, Image prev) {
        int length = getAnimationLength();
        int half = length / 2;

        Rectangle blind = new Rectangle();

        if (step >= 0 && step < half) {
            if (prev != null) {
                g2.drawImage(prev, (int) size.getX(), (int) size.getY(), null);
            }

            blind = new Rectangle((int) size.getX(), (int) size.getY(), step, (int) size.getHeight());
        }

        if (step >= half && step < length) {
            blind = new Rectangle((int) size.getX(), (int) size.getY(), length - step, (int) size.getHeight());
            blind.translate(step - half, 0);
        }

        for (int i = 0; i < size.getWidth() / half; i++) {
            g2.setColor(Color.WHITE);
            g2.fill(blind);
            blind.translate(half, 0);
        }
    }

}