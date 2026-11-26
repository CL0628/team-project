package interface_adapter.declareUno;

import use_case.declareUno.DeclareUnoInputBoundary;
import use_case.declareUno.DeclareUnoInputData;

/** Passes the UNO button interaction to the Declare UNO use case. */
public class DeclareUnoController {

    private final DeclareUnoInputBoundary declareUnoUseCase;

    public DeclareUnoController(DeclareUnoInputBoundary declareUnoUseCase) {
        this.declareUnoUseCase = declareUnoUseCase;
    }

    public void execute(String playerId) {
        declareUnoUseCase.execute(new DeclareUnoInputData(playerId));
    }
}
