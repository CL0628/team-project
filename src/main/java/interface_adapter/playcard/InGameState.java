package interface_adapter.playcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The display state for the in-game table.
 */
public class InGameState {

    private String playerId = "";
    private List<CardImageData> hand = new ArrayList<>();
    private CardImageData discardCard;
    private List<Integer> opponentHandSizes = new ArrayList<>();
    private String message = "";
    private boolean gameFinished;
    private String winnerId;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = Objects.requireNonNull(playerId);
    }

    public List<CardImageData> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public void setHand(List<CardImageData> hand) {
        this.hand = new ArrayList<>(Objects.requireNonNull(hand));
    }

    public CardImageData getDiscardCard() {
        return discardCard;
    }

    public void setDiscardCard(CardImageData discardCard) {
        this.discardCard = discardCard;
    }

    public List<Integer> getOpponentHandSizes() {
        return Collections.unmodifiableList(opponentHandSizes);
    }

    public void setOpponentHandSizes(List<Integer> opponentHandSizes) {
        List<Integer> copy = new ArrayList<>(Objects.requireNonNull(opponentHandSizes));
        if (copy.stream().anyMatch(size -> size < 0)) {
            throw new IllegalArgumentException("Opponent hand sizes cannot be negative.");
        }
        this.opponentHandSizes = copy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? "" : message;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    /** Presentation data for a card image and whether playing it needs a color choice. */
    public static final class CardImageData {
        private final String imagePath;
        private final boolean wild;

        public CardImageData(String imagePath, boolean wild) {
            this.imagePath = Objects.requireNonNull(imagePath);
            this.wild = wild;
        }

        public String getImagePath() {
            return imagePath;
        }

        public boolean isWild() {
            return wild;
        }
    }
}