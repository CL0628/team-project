package interface_adapter.playcard;

import entity.Game;
import entity.Player;
import entity.card.Card;
import use_case.playcard.PlayCardInputBoundary;
import use_case.playcard.PlayCardInputData;

public class PlayCardController {

    private final PlayCardInputBoundary playCardUseCaseInteractor;

    public PlayCardController(PlayCardInputBoundary playCardUseCaseInteractor) {
        this.playCardUseCaseInteractor = playCardUseCaseInteractor;
    }

    /**
     * Executes the PlayCard Use Case.
     *
     * @param card the card played by player
     * @param player the player at current turn, waiting ot play a card
     * @param game the current game, where the card is played
     */
    public void execute(Card card, Player player, Game game) {
        final PlayCardInputData playCardInputData = new PlayCardInputData(card, player, game);

        playCardUseCaseInteractor.execute(playCardInputData);

    }
}
