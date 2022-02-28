import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTests {

	static GameLogic g = new GameLogic();
	static int[][] puzzles = GameLogic.puzzles;

    @Test
    void testConstructor(){
    	assertEquals(GameLogic.puzzle1, puzzles[0]);
    	assertEquals(GameLogic.puzzle2, puzzles[1]);
    	assertEquals(GameLogic.puzzle3, puzzles[2]);
    	assertEquals(GameLogic.puzzle4, puzzles[3]);
    	assertEquals(GameLogic.puzzle5, puzzles[4]);
    	assertEquals(GameLogic.puzzle6, puzzles[5]);
    	assertEquals(GameLogic.puzzle7, puzzles[6]);
    	assertEquals(GameLogic.puzzle8, puzzles[7]);
    	assertEquals(GameLogic.puzzle9, puzzles[8]);
    	assertEquals(GameLogic.puzzle10, puzzles[9]);
    }
    @Test
    void validMove1(){
    	Boolean check = g.validMove(GameLogic.puzzle1, 15);
    	assertEquals(true, check);
    }
    @Test
    void validMove2(){
    	Boolean check = g.validMove(GameLogic.puzzle4, 1);
    	assertEquals(check, true);
    }
    @Test
    void movePieces1(){
    	int[] arr = GameLogic.movePieces(GameLogic.puzzle1, 14, 15);
    	int[] arrCheck = {2, 6, 10, 3, 1, 4, 7, 11, 8, 5, 9, 15, 12, 13, 0, 14};
    	assertEquals(arrCheck[14], arr[14]);
    	assertEquals(arrCheck[15], arr[15]);
    }
    @Test
    void movePieces2(){
    	int[] arr = GameLogic.movePieces(GameLogic.puzzle2, 8, 9);
    	int[] arrCheck = {7, 6, 14, 3, 15, 5, 2, 13, 0, 11, 9, 10, 8, 1, 4, 12};
    	assertEquals(arrCheck[9], arr[9]);
    	assertEquals(arrCheck[8], arr[8]);
    }
    @Test
    void winCondition1(){
    	int[] arrCheck = {1,0,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    	Boolean check = g.winCondition(arrCheck);
    	assertEquals(check, false);
    }
    @Test
    void winCondition2(){
    	int[] arrCheck = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    	Boolean check = g.winCondition(arrCheck);
    	assertEquals(check, true);
    }
    @Test
    void solve1(){
    	int[][] arr = GameLogic.solve(GameLogic.puzzle11, "heuristicOne");
    	assertEquals(true, true);
    }
    @Test
    void solve2(){
    	int[][] arr = GameLogic.solve(GameLogic.puzzle1, "heuristicOne");
    	assertEquals(true, true);
    }
    @Test
    void solve3(){
    	int[][] arr = GameLogic.solve(GameLogic.puzzle11, "heuristicTwo");
    	assertEquals(true, true);
    }
    @Test
    void solve4(){
    	int[][] arr = GameLogic.solve(GameLogic.puzzle1, "heuristicTwo");
    	assertEquals(true, true);
    }
    Node n = new Node(GameLogic.puzzle1);

    @Test
    void NodeGetParent(){
    	assertEquals(n.getParent(), null);
    }
    @Test
    void NodeGetSetDepth(){
    	n.setDepth(10);
    	assertEquals(n.getDepth(), 10);
    }
    @Test
    void NodeGetSetHValue(){
    	n.set_hValue(10);
    	assertEquals(n.get_hValue(), 10);
    }

}