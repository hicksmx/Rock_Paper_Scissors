import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * The main frame for the Rock Paper Scissors game.
 * This class extends JFrame and implements the game logic and user interface.
 */
public class RockPaperScissorsFrame extends JFrame implements ActionListener {
    private JButton rockButton, paperButton, scissorsButton, quitButton;
    private JTextField playerWinsField, computerWinsField, tiesField;
    private JTextArea resultsArea;
    private int playerWins = 0, computerWins = 0, ties = 0;
    private Random random = new Random();

    /**
     * Constructor for the RockPaperScissorsFrame.
     * Initializes the frame, creates and adds all necessary components.
     */
    public RockPaperScissorsFrame() {
        setTitle("Rock Paper Scissors Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        createComponents();
        addComponents();

        setVisible(true);
    }

    /**
     * Creates all the UI components for the game.
     */
    private void createComponents() {
        // Create buttons
        rockButton = new JButton("Rock", new ImageIcon("src/rock.png"));
        paperButton = new JButton("Paper", new ImageIcon("src/paper.png"));
        scissorsButton = new JButton("Scissors", new ImageIcon("src/scissors.png"));
        quitButton = new JButton("Quit");

        // Add action listeners
        rockButton.addActionListener(this);
        paperButton.addActionListener(this);
        scissorsButton.addActionListener(this);
        quitButton.addActionListener(this);

        // Create text fields for stats
        playerWinsField = new JTextField("0", 5);
        playerWinsField.setEditable(false);
        computerWinsField = new JTextField("0", 5);
        computerWinsField.setEditable(false);
        tiesField = new JTextField("0", 5);
        tiesField.setEditable(false);

        // Create results area
        resultsArea = new JTextArea(10, 30);
        resultsArea.setEditable(false);
    }

    /**
     * Adds all created components to the frame.
     */
    private void addComponents() {
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Choose your move"));
        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(3, 2));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Stats"));
        statsPanel.add(new JLabel("Player Wins:"));
        statsPanel.add(playerWinsField);
        statsPanel.add(new JLabel("Computer Wins:"));
        statsPanel.add(computerWinsField);
        statsPanel.add(new JLabel("Ties:"));
        statsPanel.add(tiesField);
        add(statsPanel, BorderLayout.WEST);

        // Results area
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Handles button click events.
     * @param e The ActionEvent object containing details about the event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quitButton) {
            System.exit(0);
        } else {
            playGame(e.getActionCommand());
        }
    }

    /**
     * Plays a single round of the game.
     * @param playerMove The move chosen by the player.
     */
    private void playGame(String playerMove) {
        String[] moves = {"Rock", "Paper", "Scissors"};
        String computerMove = moves[random.nextInt(3)];
        String result;
        String condition = "";

        if (playerMove.equals(computerMove)) {
            result = "Tie";
            condition = "Both chose " + playerMove;
            ties++;
        } else if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) ||
                (playerMove.equals("Paper") && computerMove.equals("Rock")) ||
                (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {
            result = "Player wins";
            condition = getCondition(playerMove, computerMove);
            playerWins++;
        } else {
            result = "Computer wins";
            condition = getCondition(computerMove, playerMove);
            computerWins++;
        }

        updateStats();
        resultsArea.append(playerMove + " vs " + computerMove + ": " + condition + " (" + result + ")\n");
    }

    /**
     * Gets the condition string describing why one move beats another.
     * @param winningMove The move that won.
     * @param losingMove The move that lost.
     * @return A string describing the winning condition.
     */
    private String getCondition(String winningMove, String losingMove) {
        switch (winningMove) {
            case "Rock":
                return "Rock breaks Scissors";
            case "Paper":
                return "Paper covers Rock";
            case "Scissors":
                return "Scissors cuts Paper";
            default:
                return "";
        }
    }

    /**
     * Updates the statistics display with current game counts.
     */
    private void updateStats() {
        playerWinsField.setText(String.valueOf(playerWins));
        computerWinsField.setText(String.valueOf(computerWins));
        tiesField.setText(String.valueOf(ties));
    }
}