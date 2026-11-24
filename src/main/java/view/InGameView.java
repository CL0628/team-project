package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The View for when the User is playing a Uno Game.
 * This View is shared by Play Card, Declare Uno, and Draw Card.
 */
public class InGameView extends JPanel {

    public static final int WINDOW_WIDTH = 1080;
    public static final int WINDOW_HEIGHT = 610;

    /* Centres of the two card spaces in inGame_background.png. */
    private static final double LEFT_CARD_X = 0.398;
    private static final double RIGHT_CARD_X = 0.602;
    private static final double CARD_Y = 0.500;
    private static final double CARD_WIDTH = 0.135;
    private static final double CARD_HEIGHT = 0.325;

    private final BufferedImage background;
    private final BufferedImage leftCard;
    private final BufferedImage rightCard;

    /**
     * Creates the in-game table with two randomly selected card faces.
     */
    public InGameView() {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setOpaque(true);

        background = loadImage("/backgrounds/inGame_background.png");

        leftCard = loadImage("/cards/blue/blue_0.png");
        rightCard = loadImage("/cards/green/green_5.png");
    }

    private static BufferedImage loadImage(String path) {
        URL url = InGameView.class.getResource(path);
        if (url == null) {
            System.err.println("Image not found: " + path);
            return null;
        }
        try {
            return ImageIO.read(url);
        }
        catch (IOException exception) {
            System.err.println("Could not read image " + path + ": " + exception.getMessage());
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            Rectangle backgroundBounds = drawBackground(g2);
            renderCard(g2, leftCard, backgroundBounds, LEFT_CARD_X);
            renderCard(g2, rightCard, backgroundBounds, RIGHT_CARD_X);
        }
        finally {
            g2.dispose();
        }
    }

    /** Draws the background with a cover scale and returns its painted bounds. */
    private Rectangle drawBackground(Graphics2D g2) {
        if (background == null) {
            g2.setColor(new Color(210, 235, 250));
            g2.fillRect(0, 0, getWidth(), getHeight());
            return new Rectangle(0, 0, getWidth(), getHeight());
        }

        double scale = Math.max((double) getWidth() / background.getWidth(),
                (double) getHeight() / background.getHeight());
        int width = (int) Math.ceil(background.getWidth() * scale);
        int height = (int) Math.ceil(background.getHeight() * scale);
        int x = (getWidth() - width) / 2;
        int y = (getHeight() - height) / 2;
        g2.drawImage(background, x, y, width, height, null);
        return new Rectangle(x, y, width, height);
    }

    private static void renderCard(Graphics2D g2, BufferedImage card, Rectangle backgroundBounds,
                                 double xPosition) {
        if (card == null) {
            return;
        }

        int width = (int) Math.round(backgroundBounds.width * CARD_WIDTH);
        int height = (int) Math.round(backgroundBounds.height * CARD_HEIGHT);
        int centreX = backgroundBounds.x + (int) Math.round(backgroundBounds.width * xPosition);
        int centreY = backgroundBounds.y + (int) Math.round(backgroundBounds.height * CARD_Y);
        g2.drawImage(card, centreX - width / 2, centreY - height / 2, width, height, null);
    }

}
