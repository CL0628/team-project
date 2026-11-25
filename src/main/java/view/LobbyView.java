package view;

import interface_adapter.lobby.LobbyViewModel;
import interface_adapter.lobby.LobbyViewModelListener;

import javax.swing.*;
import java.awt.*;

/**
 * PLACEHOLDER lobby shown after logging in. Replace with the real main menu
 * (Start Game, View Stats, Log Out...).
 */
public class LobbyView extends BackgroundPanel implements LobbyViewModelListener {

    private final LobbyViewModel lobbyViewModel;
    private final JLabel greeting = new JLabel(" ");

    /**
     * Builds the placeholder lobby and starts listening to its view model.
     *
     * @param lobbyViewModel the view model this screen displays
     */
    public LobbyView(LobbyViewModel lobbyViewModel) {
        this.lobbyViewModel = lobbyViewModel;
        this.lobbyViewModel.addListener(this);

        setPreferredSize(new Dimension(1080, 610));
        setLayout(new GridBagLayout());
        greeting.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));
        greeting.setForeground(Color.WHITE);
        add(greeting);
    }

    /**
     * Shows who is logged in.
     */
    @Override
    public void stateChanged() {
        greeting.setText("Welcome, " + lobbyViewModel.getState().getUsername() + "!");
    }
}
