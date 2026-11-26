package view;

import entity.card.CardColor;
import interface_adapter.declareUno.DeclareUnoController;
import interface_adapter.drawcard.DrawCardController;
import interface_adapter.playcard.InGameState;
import interface_adapter.playcard.PlayCardController;
import interface_adapter.playcard.PlayCardViewModel;
import interface_adapter.playcard.PlayCardViewModelListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The table view shared by the Draw Card, Play Card, and Declare UNO use cases.
 */
public class InGameView extends JPanel implements PlayCardViewModelListener {

    public static final int WINDOW_WIDTH = 1080;
    public static final int WINDOW_HEIGHT = 610;

    private static final double DRAW_PILE_X = 0.398;
    private static final double DISCARD_PILE_X = 0.602;
    private static final double TABLE_CARDS_Y = 0.50;

    private final PlayCardViewModel playCardViewModel;
    private final BufferedImage background;
    private final BufferedImage drawPileImage;
    private final BufferedImage unoImage;
    private final JLabel discardPileLabel = new JLabel();
    private final JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
    private final JButton drawPileButton;
    private final JButton declareUnoButton;
    private final List<JButton> handButtons = new ArrayList<>();
    private final List<List<JLabel>> opponentHands = new ArrayList<>();
    private final Map<String, BufferedImage> cardImages = new HashMap<>();

    private PlayCardController playCardController;
    private DrawCardController drawCardController;
    private DeclareUnoController declareUnoController;

    public InGameView(PlayCardViewModel playCardViewModel) {
        this.playCardViewModel = playCardViewModel;
        this.playCardViewModel.addListener(this);

        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setOpaque(true);
        setLayout(null);

        background = loadImage("/backgrounds/inGame_background.png");
        drawPileImage = loadImage("/cards/card_back.png");
        unoImage = loadImage("/cards/uno_card_template.png");

        drawPileButton = imageButton(drawPileImage, 146, 198);
        drawPileButton.addActionListener(event -> {
            if (drawCardController != null) {
                drawCardController.execute();
            }
        });
        add(drawPileButton);

        discardPileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(discardPileLabel);

        declareUnoButton = imageButton(unoImage, 125, 50);
        declareUnoButton.addActionListener(event -> {
            if (declareUnoController != null) {
                declareUnoController.execute(playCardViewModel.getState().getPlayerId());
            }
        });
        add(declareUnoButton);

        statusLabel.setForeground(new Color(15, 54, 95));
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        add(statusLabel);

        stateChanged();
    }

    private static BufferedImage loadImage(String path) {
        URL url = InGameView.class.getResource(path);
        if (url == null) {
            System.err.println("Image not found: " + path);
            return null;
        }
        try {
            return ImageIO.read(url);
        }
        catch (IOException exception) {
            System.err.println("Could not read image " + path + ": " + exception.getMessage());
            return null;
        }
    }

    private static JButton imageButton(BufferedImage image, int width, int height) {
        JButton button = new JButton(scaledIcon(image, width, height));
        button.setText(null);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }

    private static ImageIcon scaledIcon(BufferedImage image, int width, int height) {
        if (image == null) {
            return new ImageIcon();
        }
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void setPlayCardController(PlayCardController playCardController) {
        this.playCardController = playCardController;
        updateDisplay();
    }

    /** Retained as an alias for callers using the original setter name. */
    public void setInGameViewController(PlayCardController playCardController) {
        setPlayCardController(playCardController);
    }

    public void setDrawCardController(DrawCardController drawCardController) {
        this.drawCardController = drawCardController;
        drawPileButton.setEnabled(drawCardController != null);
    }

    public void setDeclareUnoController(DeclareUnoController declareUnoController) {
        this.declareUnoController = declareUnoController;
        updateDisplay();
    }

    @Override
    public void stateChanged() {
        updateDisplay();
    }

    private void updateDisplay() {
        final var state = playCardViewModel.getState();
        discardPileLabel.setIcon(cardIcon(state.getDiscardCard(), 146, 198));

        for (JButton button : handButtons) {
            remove(button);
        }
        handButtons.clear();
        List<InGameState.CardImageData> hand = state.getHand();
        for (int index = 0; index < hand.size(); index++) {
            final int cardIndex = index;
            JButton cardButton = imageButton(cardImage(hand.get(index)), 80, 110);
            cardButton.setEnabled(playCardController != null);
            cardButton.addActionListener(event -> playCard(cardIndex));
            handButtons.add(cardButton);
            add(cardButton);
        }

        for (List<JLabel> opponentHand : opponentHands) {
            for (JLabel cardLabel : opponentHand) {
                remove(cardLabel);
            }
        }
        opponentHands.clear();
        for (int opponentHandSize : state.getOpponentHandSizes()) {
            List<JLabel> opponentHand = new ArrayList<>();
            for (int cardIndex = 0; cardIndex < opponentHandSize; cardIndex++) {
                JLabel cardLabel = new JLabel(scaledIcon(drawPileImage, 44, 60));
                opponentHand.add(cardLabel);
                add(cardLabel);
            }
            opponentHands.add(opponentHand);
        }

        declareUnoButton.setVisible(state.getHand().size() == 1 && !state.isGameFinished());
        declareUnoButton.setEnabled(declareUnoController != null);
        statusLabel.setText(state.getMessage());
        revalidate();
        repaint();
    }

    private void playCard(int cardIndex) {
        if (playCardController == null) {
            return;
        }
        final InGameState.CardImageData selectedCard = playCardViewModel.getState().getHand().get(cardIndex);
        CardColor chosenColor = null;
        if (selectedCard.isWild()) {
            Object choice = JOptionPane.showInputDialog(
                    this,
                    "Choose a color",
                    "Wild card",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    CardColor.values(),
                    CardColor.RED);
            if (!(choice instanceof CardColor)) {
                return;
            }
            chosenColor = (CardColor) choice;
        }
        playCardController.execute(playCardViewModel.getState().getPlayerId(), cardIndex, chosenColor);
    }

    private ImageIcon cardIcon(InGameState.CardImageData card, int width, int height) {
        return card == null
                ? new ImageIcon()
                : scaledIcon(cardImage(card), width, height);
    }

    private BufferedImage cardImage(InGameState.CardImageData card) {
        final String path = card.getImagePath();
        if (!cardImages.containsKey(path)) {
            cardImages.put(path, loadImage(path));
        }
        return cardImages.get(path);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            if (background == null) {
                g2.setColor(new Color(210, 235, 250));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
            else {
                double scale = Math.max((double) getWidth() / background.getWidth(),
                        (double) getHeight() / background.getHeight());
                int width = (int) Math.ceil(background.getWidth() * scale);
                int height = (int) Math.ceil(background.getHeight() * scale);
                g2.drawImage(background, (getWidth() - width) / 2,
                        (getHeight() - height) / 2, width, height, null);
            }
        }
        finally {
            g2.dispose();
        }
    }

    @Override
    public void doLayout() {
        int width = getWidth();
        int height = getHeight();
        int tableCardWidth = Math.min(146, Math.max(90, (int) (width * 0.135)));
        int tableCardHeight = (int) Math.round(tableCardWidth * 1.36);
        int centerY = (int) (height * TABLE_CARDS_Y);
        drawPileButton.setIcon(scaledIcon(drawPileImage, tableCardWidth, tableCardHeight));
        drawPileButton.setBounds((int) (width * DRAW_PILE_X) - tableCardWidth / 2,
                centerY - tableCardHeight / 2, tableCardWidth, tableCardHeight);
        discardPileLabel.setBounds((int) (width * DISCARD_PILE_X) - tableCardWidth / 2,
                centerY - tableCardHeight / 2, tableCardWidth, tableCardHeight);
        discardPileLabel.setIcon(cardIcon(playCardViewModel.getState().getDiscardCard(),
                tableCardWidth, tableCardHeight));

        int handSize = handButtons.size();
        int handCardWidth = Math.min(80, Math.max(56, (int) (width * 0.075)));
        int handCardHeight = (int) Math.round(handCardWidth * 1.375);
        int handAreaLeft = (int) (width * 0.19);
        int handAreaWidth = (int) (width * 0.78);
        int step = handSize <= 1 ? 0 : Math.max(0, Math.min(handCardWidth,
                (handAreaWidth - handCardWidth) / (handSize - 1)));
        int cardsWidth = handSize == 0 ? 0 : handCardWidth + step * (handSize - 1);
        int playerHandX = handAreaLeft + (handAreaWidth - cardsWidth) / 2;
        int playerHandY = height - handCardHeight - 8;
        for (int index = 0; index < handSize; index++) {
            handButtons.get(index).setIcon(scaledIcon(cardImage(
                    playCardViewModel.getState().getHand().get(index)), handCardWidth, handCardHeight));
            handButtons.get(index).setBounds(playerHandX + index * step, playerHandY,
                    handCardWidth, handCardHeight);
        }

        int opponentCardWidth = 44;
        int opponentCardHeight = 60;
        int opponentStep = 20;
        for (int handIndex = 0; handIndex < opponentHands.size(); handIndex++) {
            List<JLabel> opponentHand = opponentHands.get(handIndex);
            boolean topHand = handIndex == 0;
            boolean leftHand = handIndex == 1;
            if (topHand) {
                int handWidth = opponentHand.isEmpty() ? 0
                        : opponentCardWidth + opponentStep * (opponentHand.size() - 1);
                int handX = (width - handWidth) / 2;
                for (int cardIndex = 0; cardIndex < opponentHand.size(); cardIndex++) {
                    opponentHand.get(cardIndex).setBounds(handX + cardIndex * opponentStep,
                            18, opponentCardWidth, opponentCardHeight);
                }
            }
            else {
                int handHeight = opponentHand.isEmpty() ? 0
                        : opponentCardHeight + opponentStep * (opponentHand.size() - 1);
                int handY = (height - handHeight) / 2;
                int handX = leftHand ? 18 : width - opponentCardWidth - 18;
                for (int cardIndex = 0; cardIndex < opponentHand.size(); cardIndex++) {
                    opponentHand.get(cardIndex).setBounds(handX,
                            handY + cardIndex * opponentStep, opponentCardWidth, opponentCardHeight);
                }
            }
        }

        int unoWidth = Math.min(125, Math.max(90, (int) (width * 0.116)));
        int unoHeight = (int) Math.round(unoWidth * 0.4);
        declareUnoButton.setIcon(scaledIcon(unoImage, unoWidth, unoHeight));
        declareUnoButton.setBounds(24, height - unoHeight - 12, unoWidth, unoHeight);
        statusLabel.setBounds(width / 4, 8, width / 2, 28);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final PlayCardViewModel viewModel = new PlayCardViewModel();
            final InGameState demoState = new InGameState();
            demoState.setPlayerId("demo-player");
            demoState.setHand(List.of(
                    new InGameState.CardImageData("/cards/green/green_2.png", false)));
            demoState.setDiscardCard(
                    new InGameState.CardImageData("/cards/green/green_5.png", false));
            demoState.setOpponentHandSizes(Arrays.asList(5, 5, 5));
            demoState.setMessage("Demo game - current player has one card");
            viewModel.setState(demoState);

            JFrame application = new JFrame("UNO - In Game View Test");
            application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            application.setContentPane(new InGameView(viewModel));
            application.pack();
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
    }
}
