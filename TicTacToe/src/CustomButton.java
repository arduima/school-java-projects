import javax.swing.JButton;

public class CustomButton extends JButton {
	// Button tracker: 0 is X, 2 is O, 1 is None
	private int buttonState;
	public MouseHoverListener thisMouse;
	
	CustomButton(int buttonState){
		this.buttonState = buttonState;
	}

	public void setButtonState(int buttonState) {
		this.buttonState = buttonState;
	}

	public int getButtonState() {
		return this.buttonState;
	}

	public MouseHoverListener getThisMouse() {
		return thisMouse;
	}

	public void setThisMouse(MouseHoverListener thisMouse) {
		this.thisMouse = thisMouse;
	}	
}
