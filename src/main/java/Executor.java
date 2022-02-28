import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {
	private int[][] list;

	public void execute(int[] a, String heurisitc) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		executorService.execute(new Runnable() {
		    public void run() {
				System.out.println("Asynchronous task");
		        list = GameLogic.solve(a, heurisitc);
		    }
		});
	}
	
	public void Wait() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		executorService.execute(new Runnable() {
		    public void run() {
		    	try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
	}
	
	public int[][] getList() {
		return list;
	}
}
