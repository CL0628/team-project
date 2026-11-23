package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LobbyView extends JPanel {
    private final JButton startGameButton = new JButton("Start Game");
    private final JButton settingButton = new JButton("Setting");
    private final JButton personalInformationButton = new JButton("Personal Information");
    private final JButton playerStatsButton = new JButton("Personal Stats");

    public LobbyView() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(personalInformationButton, BorderLayout.WEST);
        topPanel.add(settingButton, BorderLayout.EAST);
        topPanel.add(playerStatsButton, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridLayout());
        centerPanel.add(startGameButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public JButton getStartGameButton() {
        return startGameButton;
    }

    public JButton getSettingButton() {
        return settingButton;
    }

    public JButton getPersonalInformationButton() {
        return personalInformationButton;
    }

    public JButton getPlayerStatsButton() {return playerStatsButton; }
}
