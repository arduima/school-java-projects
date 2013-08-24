import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SquarePanel extends JPanel {
	private CustomButton[][] buttons;
	private ImageIcon[] buttonLabels;
	private final int initialIcon = 1;

	SquarePanel(LayoutManager thisLayout, CustomButton[][] boardButtons,
			ImageIcon[] _buttonLabels) {
		this.setLayout(thisLayout);
		this.buttonLabels = _buttonLabels;
		this.buttons = boardButtons;

		for (int rows = 0; rows < 3; rows++)
			for (int columns = 0; columns < 3; columns++) {
				buttons[rows][columns] = new CustomButton(1);
				buttons[rows][columns].setSize(100, 100);

				buttons[rows][columns].setIcon(buttonLabels[this.initialIcon]);
				buttons[rows][columns].setFocusPainted(false);
				// buttons[rows][columns].addActionListener(new ButtonListener(
				// rows, columns));

				buttons[rows][columns].addMouseListener(new MouseHoverListener(
						rows, columns));

				this.add(buttons[rows][columns]);
			}
	}

	// Repaint everything on the screen
	void repaintPanel() {
		for (int rows = 0; rows < 3; rows++)
			for (int columns = 0; columns < 3; columns++) {
				// Enable and set icon to None
				buttons[rows][columns].setEnabled(true);
				buttons[rows][columns].setIcon(buttonLabels[1]);
				// Add MouseListener Again
				buttons[rows][columns].addMouseListener(new MouseHoverListener(
						rows, columns));

				// Set the button tracker to None(1)
				buttons[rows][columns].setButtonState(1);
			}
	}
}
