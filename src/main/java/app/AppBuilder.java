package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;
import view.LoginView;
import view.SignupView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

/**
 * Builds the application: creates every screen and wires them together.
 * This is the only class that knows about all the concrete classes.
 */
public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private LoginView loginView;
    private SignupView signupView;

    /**
     * Creates an empty builder.
     */
    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the login screen.
     *
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginView = new LoginView();
        cardPanel.add(loginView, LoginViewModel.VIEW_NAME);
        return this;
    }

    /**
     * Adds the sign up screen.
     *
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupView = new SignupView();
        cardPanel.add(signupView, SignupViewModel.VIEW_NAME);
        return this;
    }

    /**
     * TEMPORARY: makes the "Sign Up" and "Login" links switch screens.
     * Call after addLoginView() and addSignupView().
     *
     * <p>Once the use cases exist, delete this method and add addLoginUseCase() and
     * addSignupUseCase() instead: they give each view its controller, and the
     * presenters switch screens through the ViewManagerModel.</p>
     *
     * @return this builder
     */
    public AppBuilder addViewSwitching() {
        loginView.setOnSignUpClicked(() -> showView(SignupViewModel.VIEW_NAME));
        signupView.setOnLogInClicked(() -> showView(LoginViewModel.VIEW_NAME));
        return this;
    }

    /**
     * Creates the window, starting on the login screen.
     *
     * @return the application window, ready to show
     */
    public JFrame build() {
        JFrame application = new JFrame("UNO Desktop");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        showView(LoginViewModel.VIEW_NAME);
        return application;
    }

    /**
     * Tells the ViewManagerModel to switch to the given screen.
     *
     * @param viewName the name of the screen to show
     */
    private void showView(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.notifyViewChanged();
    }
}
