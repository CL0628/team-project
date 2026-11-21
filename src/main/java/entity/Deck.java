package entity;

import entity.card.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * A class representing the deck in a UNO game.
 * It manages card avaliability and random selection
 */
public class Deck {

    // Represents the cards in a Deck
    private List<Card> cardPile;

    /**
     * Shuffles the cards in the cardPile.
     */
    public void shuffle() {
        Collections.shuffle(cardPile);
    }

    /**
     * extracts a specified number of cards from the front of Deck.
     * @param n number of cards to draw from the Deck
     * @return returns n cards from the Deck
     */
    public List<Card> drawCards(int n) {
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            drawnCards.add(cardPile.remove(0));
        }
        return drawnCards;
    }

    /**
     * Deal cards from cardPile to players one by one. Called at the start of a game.
     *
     * @param numCards number of cards dealt for each player
     * @param players players to deal cards to
     */
    public void DealCards(int numCards, List<Player> players) {
        for (Player player : players) {
            List<Card> cardsDrawn = this.drawCards(numCards);
            player.takeCards(cardsDrawn);
        }
    }

}
