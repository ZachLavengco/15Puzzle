import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	Scene startScene;
	BorderPane startPane;
	VBox vBox,puzzleBox,newquitBox,solveVBox;
	HBox buttonBox,solveHBox;
	Text welcomeTxt;
	int randomIndex,checkPrevRandomIndex;
	String solveSelected;
	
	private static BorderPane bPane;
	private static Button newPuzzle;
	private static Button solve1;
	private static Button solve2;
	private static Button exitProgram;
	private static Button seeSolution;
	
	ExecutorService executorService = Executors.newFixedThreadPool(10);
	Executor ex = new Executor();
	int[][] solvedList;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Project 4 Threaded AI 15 Puzzle");
		
		this.welcomeTxt = new Text("Welcome to 15 Puzzle");
		this.vBox = new VBox(welcomeTxt);
		this.vBox.setAlignment(Pos.CENTER);
		this.startPane = new BorderPane();
		this.startPane.setPadding(new Insets(70));
		this.startPane.setCenter(vBox);
		
		this.startScene = new Scene(startPane, 500,500);
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>( ) {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
		
		primaryStage.setScene(startScene);
		primaryStage.show();
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), (ActionEvent event) -> {
			primaryStage.setScene(createGUI());
			primaryStage.setTitle("15 Puzzle");
        }));
        timeline.play();
		
	}
	
	
	public Scene createGUI() {
		int[][] puzzles = GameLogic.puzzles;

		GameLogic.printPuzzles();
		Random rand = new Random();
		randomIndex = rand.nextInt(puzzles.length);
		checkPrevRandomIndex = randomIndex;
		GameBoard board = new GameBoard(puzzles[randomIndex]);
//		GameBoard board = new GameBoard(GameLogic.puzzle11); // to check winCondition

		/* Initial setup */
		bPane = new BorderPane(new BorderPane());
		bPane.setStyle("-fx-background-color: lavender");
		bPane.setPadding(new Insets(50));
		puzzleBox = new VBox(board.puzzle15);
		bPane.setCenter(puzzleBox);
		
		Text youWin = new Text("You Win");
        VBox topBox = new VBox(youWin);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20));
        topBox.setVisible(false);
        bPane.setTop(topBox);
        
		newPuzzle = new Button ("New Puzzle");
		solve1 = new Button ("Solve 1");
		solve2 = new Button ("Solve 2");
		exitProgram = new Button ("Exit");
		seeSolution = new Button ("See Solution");
		seeSolution.setDisable(true);
		
		/* button to reset the game with a new puzzle */
		newPuzzle.setOnAction(e->{
			while (checkPrevRandomIndex == randomIndex) {
				randomIndex = rand.nextInt(puzzles.length);
			}
			checkPrevRandomIndex = randomIndex;
			board.reset(puzzles[randomIndex]);
			
			topBox.setVisible(false);
			solve1.setDisable(false);
			solve2.setDisable(false);
			solveSelected = "";
		});

		/* button to exit the program */
		exitProgram.setOnAction(e->{ 
			try {
				Platform.exit();
				System.exit(0);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		/* button to solve using A* H1 algorithm */
		solve1.setOnAction(e->{
			ex.execute(board.gameArray, "heuristicOne");
			
			solveSelected = "solve1";
			solve1.setDisable(true);
			solve2.setDisable(true);
			seeSolution.setDisable(false);
		});

		/* button to solve using A* H2 algorithm */
		solve2.setOnAction(e->{ 
			ex.execute(board.gameArray, "heuristicTwo");
			
			solveSelected = "solve2";
			solve1.setDisable(true);
			solve2.setDisable(true);
			seeSolution.setDisable(false);
		});

		/* button to see the solution (or next 10 moves) */
		seeSolution.setOnAction(e->{
			Platform.runLater(new Runnable() {
			    public void run() {
			    	solvedList = ex.getList();

					/* loop through each list and update the game board (array and buttons) */
					for(int[] arr: solvedList) {
				        for(int n: arr) {
				            System.out.print(n+" ");
				        }
				        System.out.println();
				        
				       board.gameArray = arr;

				       /* loop through buttons and update values */
				       int currIndex = 0;
				       for (Node node: board.puzzle15.getChildren()) {
							board.gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
							if(((GameButton) node).getValue() != arr[currIndex]) {
								((GameButton) node).setValue(arr[currIndex]);
								if (arr[currIndex] == 0) {
									((GameButton) node).setText("");
								} else {
									((GameButton) node).setText(String.valueOf(arr[currIndex]));
								}
							}
							currIndex++;
				       }

				       /* check if player won the game */
				       if (GameLogic.winCondition(board.gameArray)) {
							System.out.println("YOU WIN");
							topBox.setVisible(true);
		                    solve1.setDisable(true);
		                    solve2.setDisable(true);
		                    seeSolution.setDisable(true);
						}
				    }

					solve1.setDisable(false);
					solve2.setDisable(false);
					seeSolution.setDisable(true);
			    }
			});
		});

		this.solveHBox = new HBox(solve1, solve2);
		this.solveVBox = new VBox(solveHBox,seeSolution);
		this.newquitBox = new VBox(newPuzzle, exitProgram);
		this.buttonBox = new HBox(solveVBox, newquitBox);
		
		this.solveVBox.setAlignment(Pos.CENTER);
		this.solveVBox.setSpacing(5);
		this.newquitBox.setAlignment(Pos.CENTER);
		this.newquitBox.setSpacing(5);
		this.buttonBox.setAlignment(Pos.CENTER);
		this.buttonBox.setSpacing(15);
		bPane.setRight(buttonBox);
		
		/* loop through all buttons on the board and update pieces */
		for (Node node: board.puzzle15.getChildren()) {
			board.gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
			((GameButton) node).setOnAction(a-> {
				int button_val = (int) ((GameButton) node).getValue();
				int button_index = (int) ((GameButton) node).getIndex();
				if (GameLogic.validMove(board.gameArray, button_index)) {
					board.gameArray = GameLogic.movePieces(board.gameArray, button_index, board.zeroIndex);
                    board.zeroIndex = button_index;

					/* loop through buttons and find button with containing 0 */
					for (Node node2: board.puzzle15.getChildren()) {
						if ((int) ((GameButton) node2).getValue() == 0) {
							((GameButton) node2).setValue(button_val);
							if (button_val == 0) {
								((GameButton) node2).setText("");
							} else {
								((GameButton) node2).setText(String.valueOf(button_val));
							}
						}
					}

					/* update button pressed to be the new zero value */
					((GameButton) node).setValue(0);
					((GameButton) node).setText("");
				}
				
				/* check if player one the game */
				if (GameLogic.winCondition(board.gameArray)) {
					System.out.println("YOU WIN");
					topBox.setVisible(true);
                    solve1.setDisable(true);
                    solve2.setDisable(true);
                    seeSolution.setDisable(true);
				}
			});
		}

		Scene scene = new Scene(bPane, 750, 500);

//		Thread t = new Thread(()-> {A_IDS_A_15solver ids = new A_IDS_A_15solver();});
//		t.start();

		Thread t = new Thread(()-> {executorService.execute(new Runnable() {
            public void run() { }
        });});
        t.start();

		return scene;
	}
}
