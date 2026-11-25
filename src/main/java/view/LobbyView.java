package view;

import interface_adapter.lobby.LobbyViewModel;
import interface_adapter.lobby.LobbyViewModelListener;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

/**
 * PLACEHOLDER lobby shown after logging in. Replace with the real main menu
 * (Start Game, View Stats, Log Out...).
 */
public class LobbyView extends BackgroundPanel implements LobbyViewModelListener {

    private final LobbyViewModel lobbyViewModel;
    private final JLabel greeting = new JLabel(" ");
    private final RoundedButton viewStatsButton =
            new RoundedButton("View Stats", 180, 44);
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
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 20, 0);
        add(greeting, constraints);

        constraints.gridy = 1;
        add(viewStatsButton, constraints);
    }

    /**
     * Registers the action triggered by the View Stats button.
     *
     * @param listener handles the statistics navigation request
     */
    public void addViewStatsListener(ActionListener listener) {
        viewStatsButton.addActionListener(listener);
    }

    /**
     * Shows who is logged in.
     */
    @Override
    public void stateChanged() {
        greeting.setText("Welcome, " + lobbyViewModel.getState().getUsername() + "!");
    }
}
