package interface_adapter.ingame;

import entity.card.*;

import javax.swing.*;
import java.awt.*;

/** Small standalone window for visually checking a card returned by CardImageProvider. */
public final class CardImagePreview {

    private CardImagePreview() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Card card = new WildDrawFourCard();
            ImageIcon cardImage = new CardImageProvider().getImage(card);

            JLabel cardLabel = new JLabel(cardImage);
            JFrame window = new JFrame("Card Image Preview");
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            window.add(cardLabel, BorderLayout.CENTER);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}
