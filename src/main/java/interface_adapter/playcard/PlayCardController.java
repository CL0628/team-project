package interface_adapter.playcard;

import entity.Player;
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
     * @param player the player at current turn, waiting ot play a card
     */
    public void execute(Player player) {
//        final PlayCardInputData playCardInputData = new PlayCardInputData();

//        playCardUseCaseInteractor.execute(playCardInputData);
    }
}
