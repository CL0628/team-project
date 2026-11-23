package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

/**
 * The Sign Up screen. Shows a username box, a password box, a password confirm box, a Sign Up button and a
 * "Log in" link on top of the UNO background.
 */
public class SignupView extends BackgroundPanel {
    private static final int FORM_WIDTH = 380;   // width of the boxes and the button
    private static final Color LINK_ORANGE = new Color(232, 150, 30);

    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField passwordconfirmInputField = new JPasswordField(15);
    private final JLabel errorLabel = new JLabel(" ");
    private final RoundedButton signUpButton = new RoundedButton("Sign Up", FORM_WIDTH, 60);
    private final JLabel loginLink = new JLabel("Login");

    /** What happens when "Login" is clicked. Does nothing until Main sets it. */
    private Runnable onLogInClicked = () -> { };

    /**
     * Builds the sign up screen and lays out all of its components.
     */
    public SignupView() {
        setPreferredSize(new Dimension(1080, 610));
        setLayout(new BorderLayout());

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Create an Account");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 36));
        title.setForeground(new Color(255, 255, 255, 215));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedInputField usernameBox = new RoundedInputField(
                usernameInputField, "Username", RoundedInputField.IconType.USER);
        RoundedInputField passwordBox = new RoundedInputField(
                passwordInputField, "Password", RoundedInputField.IconType.LOCK);
        RoundedInputField passwordconfirmBox = new RoundedInputField(
                passwordconfirmInputField, "Confirm Password", RoundedInputField.IconType.LOCK);
        usernameBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordconfirmBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        form.add(title);
        form.add(Box.createVerticalStrut(40));  // gap under the title
        form.add(usernameBox);
        form.add(Box.createVerticalStrut(24));  // gap between the boxes
        form.add(passwordBox);
        form.add(Box.createVerticalStrut(24));  // gap between the boxes
        form.add(passwordconfirmBox);
        form.add(buildErrorArea());             // 24px gap that also holds error messages
        form.add(signUpButton);
        form.add(Box.createVerticalStrut(22));
        form.add(buildLogInRow());

        // ---- Left column: centres the form vertically ----
        JPanel leftColumn = new JPanel(new GridBagLayout());
        leftColumn.setOpaque(false);
        leftColumn.setPreferredSize(new Dimension(460, 0));
        leftColumn.add(form);
        add(leftColumn, BorderLayout.WEST);
    }

    private JPanel buildErrorArea() {
        errorLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        errorLabel.setForeground(new Color(255, 200, 185));

        JPanel area = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        area.setOpaque(false);
        area.setAlignmentX(Component.CENTER_ALIGNMENT);
        area.add(errorLabel);

        Dimension size = new Dimension(FORM_WIDTH, 24);
        area.setPreferredSize(size);
        area.setMaximumSize(size);
        return area;
    }

    /** "Already have an account? Login" with "Login" as an orange link. */
    private JPanel buildLogInRow() {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

        JLabel prompt = new JLabel("Already have an account?");
        prompt.setFont(font);
        prompt.setForeground(new Color(255, 255, 255, 225));

        loginLink.setFont(font);
        loginLink.setForeground(LINK_ORANGE);
        loginLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Underline the link while the mouse is over it
        Map<TextAttribute, Object> underline = Map.of(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        Font underlined = font.deriveFont(underline);
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onLogInClicked.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginLink.setFont(underlined);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginLink.setFont(font);
            }
        });

        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.add(prompt);
        row.add(Box.createHorizontalStrut(4));
        row.add(loginLink);
        return row;
    }

    public void setOnLogInClicked(Runnable action) {
        this.onLogInClicked = action;
    }
}
