package playcard;

import entity.Deck;
import entity.Game;
import entity.Player;
import entity.gameSetting;
import entity.card.*;
import use_case.playcard.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayCardInteractorTest {

    @Test
    void successPlayMatchingColorCardTest() {
        // Arrange the one active game in the same in-memory location
        // that PlayCardInteractor uses in the actual application.

        final PlayCardInputData inputData =
                new PlayCardInputData("alice", 0);

        final PlayCardOutputBoundary successPresenter =
                new PlayCardOutputBoundary() {
                    @Override
                    public void prepareSuccessView(PlayCardOutputData outputData) {
                        assertEquals("bob", outputData.getNextPlayerId());
                        assertEquals(CardColor.RED, outputData.getCurrentColor());
                        assertFalse(outputData.isGameFinished());
                        assertEquals(1, outputData.getUpdatedHand().size());
                        assertEquals(CardColor.RED,
                                ((NumberCard) outputData.getPlayedCard()).getColor());
                        assertEquals(1,
                                ((NumberCard) outputData.getPlayedCard()).getNumber());
                    }

                    @Override
                    public void prepareFailView(String errorMessage) {
                        fail("Use case failure is unexpected: " + errorMessage);
                    }
                };

        PlayCardInputBoundary interactor = new PlayCardInteractor(successPresenter);

        // Act
        interactor.execute(inputData);
    }

}
