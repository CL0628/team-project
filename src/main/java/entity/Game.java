package entity;

import entity.card.Card;
import entity.card.CardColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * Represents a single UNO game and maintains its current state.
 *
 * <p>Stores the game configuration, participating players, draw pile,
 * discard pile, current player, turn direction, and active colour.
 * Players may be either human players or computer-controlled bots.
 *
 * <p>The players, deck, first discard, and initial colour are supplied
 * when the game is created. Each game receives a unique identifier.
 *
 * <p>Provides access to game state and supports advancing the turn
 * in the current direction. User interface interactions, bot decisions,
 * and persistent data storage are handled outside this class.
 */

public class Game {
    // Unique identifier for this game.
    private final String gameId;
    // Configuration for this game, such as bot count and initial hand size.
    private final gameSetting settings;
    // Players in seating order, including both humans and bots.
    private final List<Player> players;
    // Deck from which players draw cards.
    private final Deck drawPile;
    // Discarded cards; the last element is the top card.
    private final List<Card> discardPile;

    // Index of the player whose turn it currently is.
    private int currentPlayerIndex;
    // Turn direction: 1 moves forward(clockwise), -1 moves backward(counterclockwise).
    private int direction;
    // Active color that cards must match, unless another rule allows play.
    private CardColor currentColor;
    // ID of the winning player; null while no winner has been recorded.
    private String winnerId;
    // Whether the next player should be skipped when the turn advances.
    private boolean skipPending;

public Game(gameSetting settings, List<Player> players, Deck drawPile, Card firstDiscard, CardColor initialColor){
    /*to make sure that the inputs are avalible.*/
    Objects.requireNonNull(settings, "Settings cannot be null.");
    Objects.requireNonNull(players, "Players cannot be null.");
    /*to make sure game start with min 2 max 4 players, both human and bot count as a player.*/
    if (players.size() < 2 || players.size() > 4) {
        throw new IllegalArgumentException(
                "A game needs 2 to 4 players.");
    }
    this.settings = new gameSetting(settings.getNumBots(), settings.getCardsDealt());
    this.players = List.copyOf(players);
    this.gameId = UUID.randomUUID().toString();

    this.drawPile = Objects.requireNonNull(drawPile, "Draw pile cannot be null.");

    this.discardPile = new ArrayList<>();
    this.discardPile.add(Objects.requireNonNull(firstDiscard, "First discard cannot be null"));

    this.currentPlayerIndex = 0;
    this.direction = 1;
    this.currentColor = Objects.requireNonNull(initialColor, "Initial color cannot be null.");

}

    public String getGameId() {
        return gameId;
    }

    public gameSetting getSettings() {
        return new gameSetting(settings.getNumBots(),
                                settings.getCardsDealt());
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Deck getDrawPile() {
        return drawPile;
    }

    public List<Card> getDiscardPile() {
        return List.copyOf(discardPile);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getDirection() {
        return direction;
    }

    public CardColor getCurrentColor() {
        return currentColor;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    public Card getTopDiscard() {
        return discardPile.get(discardPile.size() - 1);
    }

    public void advanceTurn() {
        if (isFinished()) {
            throw new IllegalStateException(
                    "Cannot advance the turn after the game has finished.");
        }

        // Move two positions for Skip; otherwise move one.
        int steps = skipPending ? 2 : 1;

        currentPlayerIndex = Math.floorMod(
                currentPlayerIndex + direction * steps,
                players.size());

        skipPending = false;
    }

    public void checkForWinner() {
        if (isFinished()) {
            return;
        }
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer.getHand().isEmpty()) {
            winnerId = Objects.requireNonNull(
                    currentPlayer.getId(),
                    "Winner ID cannot be null.");
        }
    }

    public boolean isFinished(){
        return winnerId != null;
    }

    public String getWinnerId(){
        return winnerId;
    }

    public void skipNextPlayer() {
        if (isFinished()) {
            throw new IllegalStateException(
                    "Cannot skip a player after the game has finished.");
        }

        skipPending = true;
    }


}