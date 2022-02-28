import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public class GameBoard {
	public GridPane puzzle15; // show buttons as a grid
	public Node[][] gridPaneArray;	// store buttons in 2d array
	public int[] gameArray;
	public int zeroIndex;
	
	GameBoard(int[] array) {
		this.puzzle15 = new GridPane();
		this.gameArray = array;
		this.zeroIndex = 0;
		this.Gameboard();
	}
	
	public void reset(int[] array) {
		this.gameArray = array;
		this.zeroIndex = 0;
		this.resetBoard();
	}

	/* create GridPane of buttons to make the puzzle 15 GUI */
	private void Gameboard() {
		this.gridPaneArray = null;
		int arrayIndex = 0;
		int length = 40;
		for (int i = 0; i < 4; i++) { // rows (y)
			for (int j = 0; j < 4; j++) { // columns (x)
				GameButton b = new GameButton();
				
				b.setShape(new Circle(length));
				b.setMinSize(2*length, 2*length);
				b.setMaxSize(2*length, 2*length);

				b.setValue(gameArray[arrayIndex]);
				b.setIndex(arrayIndex);
				if (b.getValue() == 0) {
					b.setText("");
				} else {
					b.setText(String.valueOf(b.getValue()));
				}

				// add button to puzzle15 GridPane
				this.puzzle15.add(b, j, i);
				
				if (gameArray[arrayIndex] == 0) {
					this.zeroIndex = arrayIndex;
				}
				arrayIndex++;
			}
		}

		// set gap based on button radius 
		this.puzzle15.setHgap(length/4);
		this.puzzle15.setVgap(length/4);
		
		// create button array for easy access when needed
		this.gridPaneArray = new Node[4][4];
	       for(Node node : this.puzzle15.getChildren())
	       {
	          this.gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
	       }
	}
	
	/* method that resets the game board */
	private void resetBoard() {
		int resetIndex = 0;
		for(Node node : this.puzzle15.getChildren())
	    {
	       this.gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
	       ((GameButton) node).setValue(this.gameArray[resetIndex]);
	       if ((int) ((GameButton) node).getValue() == 0) {
	    	   ((GameButton) node).setText("");
	       } else {
	    	   ((GameButton) node).setText(String.valueOf((int) ((GameButton) node).getValue()));
	       }
	       if (this.gameArray[resetIndex] == 0) {
	    	   this.zeroIndex = resetIndex;
	       }
	       resetIndex++;
	    }
	}
}
