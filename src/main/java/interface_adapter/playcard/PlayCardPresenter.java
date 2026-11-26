package interface_adapter.playcard;

import entity.card.Card;
import entity.card.CardColor;
import entity.card.CardType;
import entity.card.ColoredCard;
import entity.card.NumberCard;
import use_case.playcard.PlayCardOutputBoundary;
import use_case.playcard.PlayCardOutputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Maps Play Card use-case results into the in-game view model.
 */
public class PlayCardPresenter implements PlayCardOutputBoundary {

    private final PlayCardViewModel playCardViewModel;

    public PlayCardPresenter(PlayCardViewModel playCardViewModel) {
        this.playCardViewModel = playCardViewModel;
    }

    @Override
    public void prepareSuccessView(PlayCardOutputData outputData) {
        final InGameState state = playCardViewModel.getState();
        state.setHand(toCardImageData(outputData.getUpdatedHand()));
        state.setDiscardCard(toCardImageData(outputData.getPlayedCard()));
        state.setMessage(outputData.isGameFinished()
                ? "Winner: " + outputData.getWinnerId()
                : outputData.getEffectMessage());
        state.setGameFinished(outputData.isGameFinished());
        state.setWinnerId(outputData.getWinnerId());
        playCardViewModel.notifyStateChanged();
    }

    private static List<InGameState.CardImageData> toCardImageData(List<Card> cards) {
        List<InGameState.CardImageData> result = new ArrayList<>();
        for (Card card : cards) {
            result.add(toCardImageData(card));
        }
        return result;
    }

    private static InGameState.CardImageData toCardImageData(Card card) {
        final CardType cardType = card.getCardType();
        final String path;
        if (cardType == CardType.WILD) {
            path = "/cards/wild/wild.png";
        }
        else if (cardType == CardType.WILD_DRAW_FOUR) {
            path = "/cards/wild/wild_draw_four.png";
        }
        else {
            if (!(card instanceof ColoredCard)) {
                throw new IllegalArgumentException("Unsupported card type: " + cardType);
            }
            final CardColor color = ((ColoredCard) card).getColor();
            final String colorName = color.name().toLowerCase(Locale.ROOT);
            final String face = cardType == CardType.NUMBER
                    ? Integer.toString(((NumberCard) card).getNumber())
                    : cardType.name().toLowerCase(Locale.ROOT);
            path = "/cards/" + colorName + "/" + colorName + "_" + face + ".png";
        }
        return new InGameState.CardImageData(path,
                cardType == CardType.WILD || cardType == CardType.WILD_DRAW_FOUR);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        playCardViewModel.getState().setMessage(errorMessage);
        playCardViewModel.notifyStateChanged();
    }
}
