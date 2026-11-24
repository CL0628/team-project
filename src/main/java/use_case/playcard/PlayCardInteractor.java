package use_case.playcard;

import entity.Game;
import entity.Player;
import entity.card.Card;
import entity.card.CardEffect;

public class PlayCardInteractor implements PlayCardInputBoundary{

    private final PlayCardDataAccessInterface dataAccessObject;
    private final PlayCardOutputBoundary playCardPresenter;

    public PlayCardInteractor(PlayCardDataAccessInterface dataAccessInterface,
                              PlayCardOutputBoundary playCardOutputBoundary) {
        this.dataAccessObject = dataAccessInterface;
        this.playCardPresenter = playCardOutputBoundary;
    }


    @Override
    public void execute(PlayCardInputData playCardInputData) {
        Game game = playCardInputData.getGame();
        Player player = playCardInputData.getPlayer();
        Card selectedCard = playCardInputData.getCard();

        // check whether the selected card is in player's hand
        int cardIndex = -1;
        for (int i = 0; i < player.getHand().size(); i++) {
            if (player.getHand().get(i) == selectedCard) {
                cardIndex = i;
                break;
            }
        }

        if (cardIndex == -1) {
            playCardPresenter.prepareFailView("The selected card is not in the player's hand.");
            return;
        }

        if (!game.canPlayCard(selectedCard)) {
            playCardPresenter.prepareFailView("That card cannot be played.");
            return;
        }

        Card playedCard = player.playCard(cardIndex);
        game.addToDiscardPile(playedCard);

        if (playedCard instanceof CardEffect) {
            ((CardEffect) playedCard).applyEffect(game);
        }

        game.checkForWinner();
        if (!game.isFinished()) {
            game.advanceTurn();
        }

        playCardPresenter.prepareSuccessView(new PlayCardOutputData(
                player.getHand(),
                player,
                playedCard,
                game.getDirection(),
                game.getCurrentColor(),
                game.isFinished(),
                game.getWinnerId(),
                game.getCurrentPlayer()
        ));
    }
}
