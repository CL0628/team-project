package interface_adapter.drawcard;

import use_case.drawcard.DrawCardInputBoundary;
import use_case.drawcard.DrawCardInputData;

/** Turns draw-pile interactions from the view into Draw Card use-case calls. */
public class DrawCardController {

    private final DrawCardInputBoundary drawCardUseCaseInteractor;

    public DrawCardController(DrawCardInputBoundary drawCardUseCaseInteractor) {
        this.drawCardUseCaseInteractor = drawCardUseCaseInteractor;
    }

    /** Executes the Draw Card use case. */
    public void execute() {
        drawCardUseCaseInteractor.execute(new DrawCardInputData());
    }
}
