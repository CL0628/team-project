package interface_adapter.playcard;

import java.util.ArrayList;
import java.util.List;

public class PlayCardViewModel {

    private final List<PlayCardViewModelListener> listeners = new ArrayList<>();

    public static final String VIEW_NAME = "in game";

    private InGameState state = new InGameState();

    public InGameState getState() {
        return this.state;
    }

    public void setState(InGameState state) {
        this.state = state;
    }

    public void addListener(PlayCardViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (PlayCardViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }

}
