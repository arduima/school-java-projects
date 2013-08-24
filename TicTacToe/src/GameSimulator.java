import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class GameSimulator {
	// Icon tracker: 0 is X, 2 is O, 1 is None
	private int currentMove = 0;
	// Data field for the buttons
	CustomButton[][] buttons;
	JLabel moveIndicator;
	Integer TimerCounter;

	GameSimulator(CustomButton[][] boardButtons, JLabel moveIndicator) {
		this.buttons = boardButtons;
		this.moveIndicator = moveIndicator;

		Timer labelTimer;
		TimerCounter = new Integer(0);

		// Set the icon to X(0)
		this.setCurrentMove(0);
		if (TicTacToeMain.getGAME_TYPE() == 2)
			// Set the text
			TicTacToeMain.getMoveIndicator().setText("X's Turn");
		else
			// Set the text
			TicTacToeMain.getMoveIndicator().setText("Your Turn");

		labelTimer = new Timer(1000, new TimerListener(moveIndicator,
				TimerCounter));
		labelTimer.start();

	}

	public void simulateMove(CustomButton thisButton) {
		// Get all the variables
		int currentMove = this.getCurrentMove();
		ImageIcon[] buttonLabels = TicTacToeMain.getImageArray();
		int numberOfPlayers = TicTacToeMain.getGAME_TYPE();

		if (numberOfPlayers == 1)
			cpuMove();
		else
			// Change the indicator text
			moveIndicator.setText(((currentMove == 0) ? "O" : "X") + "'s Turn");

		// Set the button icon and state
		thisButton.setIcon(buttonLabels[currentMove]);
		thisButton.setButtonState(currentMove);

		// Check if the player just won
		if (!checkForWinner(currentMove))
			checkForFull();

		// Change to the next icon
		this.setCurrentMove(((currentMove == 0) ? 2 : 0));
	}

	private void cpuMove() {
		// Change the indicator text
		moveIndicator.setText(((TicTacToeMain.getGameSimulator()
				.getCurrentMove() == 0) ? "Your" : "CPU's") + " Turn");

	}

	// Check for a winner
	public boolean checkForWinner(int _currentIcon) {
		// Store the coordinates of possible winning
		int coordiantes[][][] = { { { 0, 0 }, { 1, 1 }, { 2, 2 } },
				{ { 0, 2 }, { 1, 1 }, { 2, 0 } },
				{ { 0, 0 }, { 0, 1 }, { 0, 2 } },
				{ { 1, 0 }, { 1, 1 }, { 1, 2 } },
				{ { 2, 0 }, { 2, 1 }, { 2, 2 } },
				{ { 0, 0 }, { 1, 0 }, { 2, 0 } },
				{ { 0, 1 }, { 1, 1 }, { 2, 1 } },
				{ { 0, 2 }, { 1, 2 }, { 2, 2 } } };

		for (int n = 0; n < 8; n++) {
			int sum = buttons[coordiantes[n][0][0]][coordiantes[n][0][1]]
					.getButtonState()
					+ buttons[coordiantes[n][1][0]][coordiantes[n][1][1]]
							.getButtonState()
					+ buttons[coordiantes[n][2][0]][coordiantes[n][2][1]]
							.getButtonState();
			// Person Won -----------------------------
			if (sum == _currentIcon * 3) {
				disableAll(coordiantes, n);
				moveIndicator
						.setText(((this.getCurrentMove() == 0) ? "X" : "O")
								+ " WON!");

				return true;
			}
		}
		return false;
	}

	private boolean checkForFull() {
		for (int rows = 0; rows < 3; rows++)
			for (int columns = 0; columns < 3; columns++)
				if (buttons[rows][columns].getButtonState() == 1)
					return false;

		disableAll();
		moveIndicator.setText("It's a Tie");
		return true;
	}

	// Disable all buttons and select the winner
	private void disableAll(int[][][] coordiantes, int winner) {
		for (int rows = 0; rows < 3; rows++)
			for (int columns = 0; columns < 3; columns++) {
				buttons[rows][columns].setEnabled(false);
				buttons[rows][columns]
						.removeMouseListener(buttons[rows][columns]
								.getThisMouse());
			}

		for (int n = 0; n < 3; n++)
			buttons[coordiantes[winner][n][0]][coordiantes[winner][n][1]]
					.setEnabled(true);
	}

	private void disableAll() {
		for (int rows = 0; rows < 3; rows++)
			for (int columns = 0; columns < 3; columns++)
				buttons[rows][columns].setEnabled(false);
	}

	public int getCurrentMove() {
		return currentMove;
	}

	private void setCurrentMove(int currentMove) {
		this.currentMove = currentMove;
	}

}

class TimerListener implements ActionListener {

	JLabel moveIndicator;
	Integer TimerCounter;

	TimerListener(JLabel moveIndicator, Integer TimerCounter) {
		this.moveIndicator = moveIndicator;
		this.TimerCounter = TimerCounter;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println();
		String output = moveIndicator.getText() + ".";

		TimerCounter++;

		moveIndicator.setText(output);

	}

}
