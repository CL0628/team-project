package use_case.playcard;

import entity.Game;
import entity.Player;
import entity.card.Card;
import entity.card.CardEffect;

public class PlayCardInteractor implements PlayCardInputBoundary{

    private final PlayCardOutputBoundary playCardPresenter;

    public PlayCardInteractor(PlayCardOutputBoundary playCardOutputBoundary) {
        this.playCardPresenter = playCardOutputBoundary;
    }


    @Override
    public void execute(PlayCardInputData playCardInputData) {
        Game game = playCardInputData.getGame();
        Player player = playCardInputData.getPlayer();
        Card selectedCard = playCardInputData.getCard();

        // check if chosenCard can be played
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

//        playCardPresenter.prepareSuccessView(new PlayCardOutputData(
//                player.getHand(),
//                player,
//                playedCard,
//                game.getDirection(),
//                game.getCurrentColor(),
//                game.isFinished(),
//                game.getWinnerId(),
//                game.getCurrentPlayer()
//        ));
    }
}
