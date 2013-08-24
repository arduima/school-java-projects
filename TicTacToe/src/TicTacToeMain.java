import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TicTacToeMain {
	// One(1) or Two(2) player game
	private static int GAME_TYPE = 2;
	// Create the button array on the board
	private static CustomButton[][] boardButtons = new CustomButton[3][3];
	// Setup the image array
	private static final ImageIcon[] IMAGE_ARRAY = new ImageIcon[] {
			new ImageIcon("src/images/x.gif"),
			new ImageIcon("src/images/None.psd"),
			new ImageIcon("src/images/o.gif") };
	// Label on top
	private static JLabel moveIndicator = new JLabel();

	// Create the move simulator
	private static GameSimulator gameSimulator = new GameSimulator(boardButtons,
			moveIndicator);

	// Create the panel with the buttons
	private static SquarePanel boardPanel;

	public static void main(String[] args) {
		// Timer textTimer = new Timer(1000, new TimerListener());

		// Create a frame
		JFrame myFrame = new JFrame("Tic Tac Toe");
		myFrame.setLayout(new BorderLayout());

		JPanel messagePanel = new JPanel();

		moveIndicator.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		moveIndicator.setText("X's Turn...");
		messagePanel.add(moveIndicator);

		// Create the panel with the buttons
		boardPanel = new SquarePanel(new GridLayout(3, 3, 1, 1), boardButtons,
				IMAGE_ARRAY);

		JPanel buttonPanel = new JPanel();
		JButton onePlayerButton = new JButton("One Player Game");
		onePlayerButton.setEnabled(false);
		JButton twoPalyerButton = new JButton("Two Player Game");
	
		buttonPanel.add(onePlayerButton);
		buttonPanel.add(twoPalyerButton);

		// Add in line button listeners
		onePlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("One Player");
				GAME_TYPE = 1;
				gameSimulator = new GameSimulator(boardButtons,
						moveIndicator);
				boardPanel.repaintPanel();
			}
		});

		twoPalyerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Two Player");
				GAME_TYPE = 2;
				gameSimulator = new GameSimulator(boardButtons,
						moveIndicator);
				boardPanel.repaintPanel();
			}
		});

		// Setup the frame
		myFrame.setSize(320, 350);
		myFrame.setLocationRelativeTo(null);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myFrame.add(messagePanel, BorderLayout.NORTH);
		myFrame.add(boardPanel, BorderLayout.CENTER);
		myFrame.add(buttonPanel, BorderLayout.SOUTH);

		// myFrame.pack();
		myFrame.setVisible(true);

	}

	public static int getGAME_TYPE() {
		return GAME_TYPE;
	}

	public static void setGAME_TYPE(int _GAME_TYPE) {
		GAME_TYPE = _GAME_TYPE;
	}

	public static CustomButton[][] getBoardButtons() {
		return boardButtons;
	}

	public static ImageIcon[] getImageArray() {
		return IMAGE_ARRAY;
	}

	public static GameSimulator getGameSimulator() {
		return gameSimulator;
	}

	public static JLabel getMoveIndicator() {
		return moveIndicator;
	}
}
