package use_case.declareUno;

/** Input data for declaring UNO. */
public class DeclareUnoInputData {

    private final String playerId;

    public DeclareUnoInputData(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
