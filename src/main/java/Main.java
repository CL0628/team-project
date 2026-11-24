import view.InGameView;
import view.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        JFrame frame = new JFrame("UNO Desktop");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new LoginView());
//        frame.pack();
//        frame.setVisible(true);
        JFrame frame = new JFrame("In Game View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new InGameView());
        frame.pack();
        frame.setVisible(true);
    }
}
