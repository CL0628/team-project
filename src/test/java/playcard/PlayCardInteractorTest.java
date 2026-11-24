package playcard;

import entity.Deck;
import entity.Game;
import entity.Player;
import entity.gameSetting;
import entity.card.*;
import use_case.playcard.PlayCardInputData;
import use_case.playcard.PlayCardInteractor;
import use_case.playcard.PlayCardOutputBoundary;
import use_case.playcard.PlayCardOutputData;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayCardInteractorTest {

    @Test
    void successPlayNumberCardTest() {
        List<Card> hand = new ArrayList<Card>();
        hand.add(new NumberCard(CardColor.RED, 1));
        hand.add(new NumberCard(CardColor.YELLOW, 2));
        hand.add(new NumberCard(CardColor.GREEN, 3));
        hand.add(new NumberCard(CardColor.BLUE, 4));

        Player player = new Player("alice", hand);
        Player player2 = new Player("bob", new ArrayList<>());
        Player player3 = new Player("carol", new ArrayList<>());
        Player player4 = new Player("dave", new ArrayList<>());

        List<Player> players = List.of(player, player2, player3, player4);
        Game game = new Game(
                new gameSetting(3, 7),
                players,
                new Deck(),
                new NumberCard(CardColor.RED, 7),
                CardColor.RED
        );

        Card redOne = hand.get(0);
        PlayCardOutputBoundary presenter = new PlayCardOutputBoundary() {
            @Override
            public void prepareSuccessView(PlayCardOutputData outputData) {
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail(errorMessage);
            }
        };
        PlayCardInteractor interactor =
                new PlayCardInteractor(null, presenter);

        interactor.execute(new PlayCardInputData(redOne, player, game));

        assertEquals(3, player.getHand().size());
        assertFalse(player.getHand().contains(redOne));
        assertEquals(2, game.getDiscardPile().size());
        assertSame(redOne, game.getTopDiscard());
        assertEquals(CardColor.RED, game.getCurrentColor());
    }

    @Test
    void failurePlayNumberCardTest() {
        List<Card> hand = new ArrayList<Card>();
        hand.add(new NumberCard(CardColor.RED, 1));
        hand.add(new NumberCard(CardColor.GREEN, 2));
        hand.add(new NumberCard(CardColor.BLUE, 3));
        hand.add(new NumberCard(CardColor.YELLOW, 4));

        Player player = new Player("alice", hand);
        Player player2 = new Player("bob", new ArrayList<>());
        Player player3 = new Player("carol", new ArrayList<>());
        Player player4 = new Player("dave", new ArrayList<>());

        List<Player> players = List.of(player, player2, player3, player4);
        NumberCard yellowTopCard = new NumberCard(CardColor.YELLOW, 7);
        Game game = new Game(
                new gameSetting(3, 7),
                players,
                new Deck(),
                yellowTopCard,
                CardColor.YELLOW
        );

        List<Card> originalHand = player.getHand();
        List<Card> originalDiscardPile = game.getDiscardPile();
        Card redOne = hand.get(0);

        final boolean[] failViewCalled = {false};
        PlayCardOutputBoundary presenter = new PlayCardOutputBoundary() {
            @Override
            public void prepareSuccessView(PlayCardOutputData outputData) {
                fail("The red 1 card should not be playable.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                failViewCalled[0] = true;
            }
        };
        PlayCardInteractor interactor =
                new PlayCardInteractor(null, presenter);

        interactor.execute(new PlayCardInputData(redOne, player, game));

        assertTrue(failViewCalled[0]);
        assertEquals(originalHand, player.getHand());
        assertEquals(originalDiscardPile, game.getDiscardPile());
        assertSame(yellowTopCard, game.getTopDiscard());
        assertEquals(CardColor.YELLOW, game.getCurrentColor());
    }
}
