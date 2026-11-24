package use_case.playcard;

import entity.Direction;
import entity.Player;
import entity.card.Card;
import entity.card.CardColor;

import java.util.List;

/**
 * Output Data for the PlayCard Use Case.
 */
public class PlayCardOutputData {

    private final List<Card> currentPlayerHand;
    private final Player player;
    private final Card playedCard;
    private Direction direction;
    private CardColor currentColor;
    private final boolean isGameOver;
    private final String winnerId;
    private final Player currentPlayer;

    public PlayCardOutputData(List<Card> currentPlayerHand, Player player, Card playedCard,
                              Direction direction, CardColor currentColor, boolean isGameOver,
                              String winnerId, Player currentPlayer) {
        this.currentPlayerHand = currentPlayerHand;
        this.player = player;
        this.playedCard = playedCard;
        this.direction = direction;
        this.currentColor = currentColor;
        this.isGameOver = isGameOver;
        this.winnerId = winnerId;
        this.currentPlayer = currentPlayer;
    }

    public List<Card> getCurrentPlayerHand() {
        return currentPlayerHand;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getPlayedCard() {
        return playedCard;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public CardColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(CardColor currentColor) {
        this.currentColor = currentColor;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}