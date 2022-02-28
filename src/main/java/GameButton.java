import javafx.scene.control.Button;

public class GameButton extends Button{
	private int value;
	private int index;
	
	GameButton() {
		value = 0;
		index = 0;
	}
	
	public void setValue(int v) {
		value = v;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setIndex(int i) {
		index = i;
	}
	
	public int getIndex() {
		return index;
	}
}
