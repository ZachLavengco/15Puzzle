import java.util.ArrayList;
import java.util.Arrays;

public class GameLogic {
	/* 10 puzzles that can be solved by the A algorithm */
	static int[] puzzle1 = {2, 6, 10, 3, 1, 4, 7, 11, 8, 5, 9, 15, 12, 13, 14, 0};  // 2 6 10 3 1 4 7 11 8 5 9 15 12 13 14 0
	static int[] puzzle2 = {7, 6, 14, 3, 15, 5, 2, 13, 11, 0, 9, 10, 8, 1, 4, 12};  // 7 6 14 3 15 5 2 13 11 0 9 10 8 1 4 12
	static int[] puzzle3 = {6, 3, 4, 11, 5, 8, 10, 15, 2, 0, 12, 7, 1, 13, 9, 14};  // 6 3 4 11 5 8 10 15 2 0 12 7 1 13 9 14
	static int[] puzzle4 = {5, 7, 0, 10, 2, 1, 3, 4, 14, 6, 8, 15, 12, 13, 11, 9};  // 5 7 0 10 2 1 3 4 14 6 8 15 12 13 11 9
	static int[] puzzle5 = {3, 8, 12, 11, 10, 0, 4, 15, 9, 5, 13, 7, 2, 1, 6, 14};  // 3 8 12 11 10 0 4 15 9 5 13 7 2 1 6 14
	static int[] puzzle6 = {9, 3, 11, 13, 15, 14, 6, 10, 7, 5, 2, 12, 8, 1, 0, 4};  // 9 3 11 13 15 14 6 10 7 5 2 12 8 1 0 4
	static int[] puzzle7 = {10, 3, 9, 7, 8, 12, 11, 15, 1, 2, 0, 13, 6, 4, 14, 5};  // 10 3 9 7 8 12 11 15 1 2 0 13 6 4 14 5
	static int[] puzzle8 = {15, 5, 11, 0, 4, 10, 2, 7, 1, 6, 3, 9, 13, 8, 14, 12};  // 15 5 11 0 4 10 2 7 1 6 3 9 13 8 14 12
	static int[] puzzle9 = {8, 12, 5, 11, 2, 3, 6, 7, 1, 15, 13, 9, 4, 14, 0, 10};  // 8 12 5 11 2 3 6 7 1 15 13 9 4 14 0 10
	static int[] puzzle10 = {13, 6, 3, 7, 9, 11, 1, 0, 2, 14, 8, 10, 12, 4, 5, 15};  // 13 6 3 7 9 11 1 0 2 14 12 8 10 4 5 15
	static int[][] puzzles = {puzzle1,puzzle2,puzzle3,puzzle4,puzzle5,puzzle6, puzzle7, puzzle8, puzzle9, puzzle10};
	
	static int[] puzzle11 = {1,0,2,3,4,5,6,7,8,9,10,11,12,13,14,15}; // used to check win condition
	private static int[] goalState = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};	//goal state used for comparison
	
	/* Test function to print all puzzles */
	public static void printPuzzles() {
		for(int[] arr: puzzles) {
	        for(int n: arr) {
	            System.out.print(n+" ");
	        }
	        System.out.println();
	    }
	}
	
	/* method that checks if a move is valid in a puzzle 15 integer array, given the index */
	public static boolean validMove (int[] array, int index) {

		// check left
		if (index-1 >= 0 && index-1 != 3 && index-1 != 7 && index-1 != 11) {
			if (array[index-1] == 0) {
				return true;
			}
		}

		// check right
		if (index+1 <= 15 && index+1 != 4 && index+1 != 8 && index+1 != 12) {
			if (array[index+1] == 0) {
				return true;
			}
		}

		// check up
		if (index-4 >= 0) {
			if (array[index-4] == 0) {
				return true;
			}
		}
		
		// check down
		if (index+4 <= 15) {
			if (array[index+4] == 0) {
				return true;
			}
		}

		return false; // neighbors do not contain the 0 value
	}
	
	/* update pieces on the puzzle 15 integer array */
	public static int[] movePieces(int[] array, int index, int indexZero) {
		int[] arr = array;
		int indexVal = arr[index];
		arr[index] = 0;
		arr[indexZero] = indexVal;
		return arr;
	}
	
	public static boolean winCondition(int[] puzzleArray){
		
		if(Arrays.equals(puzzleArray, goalState))
			return true;
		else
			return false;
	}
	
	/* solves the next 10 or less moves using A* heuristics */
	public static int[][] solve(int[] puzzleArray, String heuristic) {
		
		Node newNode = new Node(puzzleArray);
		newNode.setDepth(0);
		DB_Solver2 solver1 = new DB_Solver2(newNode, heuristic);
		Node solution = solver1.findSolutionPath();
		ArrayList<Node> solutionArray = solver1.getSolutionPath(solution);
		int size = solutionArray.size();
		if (size > 10) {
			size = 10;
		}

		int[][] nodeList = new int[size][];
		for(int i=0; i < size; i++){
			int[] currentVal = solutionArray.get(i).getKey();
			nodeList[i]= currentVal;
			if (winCondition(currentVal)) {
				break;
			}
		}

		return nodeList;
	}

}
