package data_access;

import entity.playerData;
import use_case.login.LoginDataAccessInterface;
import use_case.signup.SignupDataAccessInterface;
import use_case.viewplayerstats.ViewPlayerStatsDataAccessInterface;
import use_case.viewplayerstats.ViewPlayerStatsSessionInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory implementation of the DAO for storing player data. This implementation does
 * NOT persist data between runs of the program. Useful for tests.
 */
public class InMemoryPlayerDataAccessObject implements LoginDataAccessInterface,
        SignupDataAccessInterface,
        ViewPlayerStatsDataAccessInterface,
        ViewPlayerStatsSessionInterface {

    private final Map<String, playerData> players = new HashMap<>();
    private String currentPlayerId;

    @Override
    public boolean existsById(String username) {
        return players.containsKey(username);
    }

    @Override
    public void save(playerData player) {
        players.put(player.getId(), player);
    }

    @Override
    public playerData getPlayerData(String username) {
        return players.get(username);
    }

    @Override
    public void setCurrentPlayerId(String username) {
        this.currentPlayerId = username;
    }

    @Override
    public String getCurrentPlayerId() {
        return currentPlayerId;
    }
}
