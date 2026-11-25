package use_case.playcard;

import entity.Game;
import entity.Player;
import entity.card.Card;

public class PlayCardInputData {

    private final Card selectedCard;
    // is input data allowed to depend on a entity? Does this violate clean architecture?
    private final Player player;
    private Game game;

    public PlayCardInputData(Card card, Player player, Game game) {
        this.selectedCard = card;
        this.player = player;
        this.game = game;
    }

    Card getCard() {
        return selectedCard;
    }

    Player getPlayer() {
        return player;
    }

    Game getGame() {
        return game;  // feels weird. Am i allowed to return a entity?
    }

}
