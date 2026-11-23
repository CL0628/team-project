package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

/**
 * The login screen. Shows a username box, a password box, a Login button and a
 * "Sign Up" link on top of the UNO background.
 */

public class LoginView extends BackgroundPanel {

    private static final int FORM_WIDTH = 380;   // width of the boxes and the button
    private static final Color LINK_ORANGE = new Color(232, 150, 30);

    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel errorLabel = new JLabel(" ");
    private final RoundedButton loginButton = new RoundedButton("Login", FORM_WIDTH, 60);
    private final JLabel signUpLink = new JLabel("Sign Up");

    public LoginView() {
        setPreferredSize(new Dimension(1080, 610));
        setLayout(new BorderLayout());

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Welcome Back");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 36));
        title.setForeground(new Color(255, 255, 255, 215));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedInputField usernameBox = new RoundedInputField(
                usernameInputField, "Username", RoundedInputField.IconType.USER);
        RoundedInputField passwordBox = new RoundedInputField(
                passwordInputField, "Password", RoundedInputField.IconType.LOCK);
        usernameBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        form.add(title);
        form.add(Box.createVerticalStrut(40));  // gap under the title
        form.add(usernameBox);
        form.add(Box.createVerticalStrut(24));  // gap between the boxes
        form.add(passwordBox);
        form.add(buildErrorArea());             // 24px gap that also holds error messages
        form.add(loginButton);
        form.add(Box.createVerticalStrut(22));
        form.add(buildSignUpRow());

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

    /** "Don't have an account? Sign Up" with "Sign Up" as an orange link. */
    private JPanel buildSignUpRow() {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

        JLabel prompt = new JLabel("Don't have an account?");
        prompt.setFont(font);
        prompt.setForeground(new Color(255, 255, 255, 225));

        signUpLink.setFont(font);
        signUpLink.setForeground(LINK_ORANGE);
        signUpLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Underline the link while the mouse is over it
        Map<TextAttribute, Object> underline = Map.of(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        Font underlined = font.deriveFont(underline);
        signUpLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Placeholder until the Sign Up view exists
                JOptionPane.showMessageDialog(LoginView.this, "Sign up is coming soon!");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                signUpLink.setFont(underlined);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signUpLink.setFont(font);
            }
        });

        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.add(prompt);
        row.add(Box.createHorizontalStrut(4));
        row.add(signUpLink);
        return row;
    }
}