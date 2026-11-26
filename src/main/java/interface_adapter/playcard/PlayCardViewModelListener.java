package interface_adapter.playcard;

/**
 * Something that wants to know when the playcard state changes (the inGameView).
 */
public interface PlayCardViewModelListener {

    /**
     * Called when the PlayCardViewModel's state has changed.
     */
    void stateChanged();
}