package use_case.playcard;

import entity.Direction;
import entity.Player;
import entity.card.Card;
import entity.card.CardColor;

/**
 * Output Data for the PlayCard Use Case.
 */
public class PlayCardOutputData {

    private final Player player;
    private final Card playedCard;
    private Direction direction;
    private CardColor currentColor;  // color might change

    public PlayCardOutputData(Player player, Card playedCard,
                              Direction dir, CardColor color) {
        this.player = player;
        this.playedCard = playedCard;
        this.direction = dir;
        this.currentColor = color;
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

    public CardColor getCurrentColor() {
        return currentColor;
    }
}
