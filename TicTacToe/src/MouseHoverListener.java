import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHoverListener implements MouseListener {
	// Keep track of the coordinates because the ActionListeners are anonymous
	private int ID_Row, ID_Column;

	MouseHoverListener(int ID_Row, int ID_Column) {
		this.ID_Row = ID_Row;
		this.ID_Column = ID_Column;
		CustomButton[][] tempButton = TicTacToeMain.getBoardButtons();
		tempButton[this.ID_Row][this.ID_Column].setThisMouse(this);
	}

	public void mouseEntered(MouseEvent e) {
		CustomButton[][] buttons = TicTacToeMain.getBoardButtons();
		buttons[ID_Row][ID_Column]
				.setIcon(TicTacToeMain.getImageArray()[TicTacToeMain
						.getGameSimulator().getCurrentMove()]);
	}

	public void mouseExited(MouseEvent e) {
		CustomButton[][] buttons = TicTacToeMain.getBoardButtons();
		if (buttons[ID_Row][ID_Column].getButtonState() == 1)
			buttons[ID_Row][ID_Column]
					.setIcon(TicTacToeMain.getImageArray()[1]);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Get the buttons
		CustomButton[][] buttons = TicTacToeMain.getBoardButtons();

		TicTacToeMain.getGameSimulator().simulateMove(
				buttons[ID_Row][ID_Column]);

		// Remove the ActionListener from this button
		buttons[ID_Row][ID_Column]
				.removeMouseListener(this);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
