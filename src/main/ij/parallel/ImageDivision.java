package ij.parallel;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import ij.Prefs;

public class ImageDivision {
	
	public final int numThreads, mod, ratio;
	public final Division[] divs;
	

	// Decide best number of divisions automatically
	public ImageDivision(int roiX, int roiY, int roiWidth, int roiHeight) {
		// TODO Auto-generated constructor stub
		numThreads = Math.min(Prefs.getThreads(), roiHeight);
		ratio = roiHeight / numThreads;
		mod = roiHeight % numThreads;
		divs = new Division[numThreads];
		
		setDivsions(roiX, roiY, roiWidth);
	}
	
	// Set specific number of divisions
	public ImageDivision(int roiX, int roiY, int roiWidth, int roiHeight, int divisions) 
	{
		numThreads = divisions == 0 ? 1 : divisions;
		ratio = roiHeight / numThreads;
		mod = roiHeight % numThreads;
		divs = new Division[numThreads];	
		setDivsions(roiX, roiY, roiWidth);
	}		

	// Constructor that creates divisions with x and y boundaries
	public ImageDivision(int roiX, int roiY, int roiWidth, int roiHeight, int width, int height) {
		// TODO Auto-generated constructor stub
		Prefs.setThreads(Runtime.getRuntime().availableProcessors());
		numThreads = Math.min(Prefs.getThreads(), roiHeight);
		ratio = roiHeight / numThreads;
		int a = 0;
		mod = roiHeight % numThreads;
		divs = new Division[numThreads];
		setDivsionsWithKernel(roiX, roiY, roiWidth, roiHeight, width, height);
		setDivsionsWithKernel(roiX, roiY, roiWidth,roiHeight, width, height);
	}	

	// Constructor that creates divisions with x and y boundaries, sets specific number of divisions
	public ImageDivision(int roiX, int roiY, int roiWidth, int roiHeight, int divisions, int width, int height) {
		// TODO Auto-generated constructor stub
		Prefs.setThreads(Runtime.getRuntime().availableProcessors());
		numThreads = divisions == 0 ? 1 : divisions;
		ratio = roiHeight / numThreads;
		mod = roiHeight % numThreads;
		divs = new Division[numThreads];
		setDivsionsWithKernel(roiX, roiY, roiWidth,roiHeight, width, height);
	}
	
	private void setDivsions(int roiX, int roiY, int roiWidth){
	{
		int numRows, yStart, yLimit, xEnd,xStart;

		
		for (int i = 0; i < numThreads; i++){
			if ( i == (numThreads - 1)){
				// add remainder rows for the last thread if the roiHeight is not a multiple of numThreads
				numRows = mod == 0 ? ratio : ratio + mod;
			} else {
				numRows = ratio;
			}
			yStart = roiY+i*ratio;
			yLimit = yStart + numRows;
			xStart = roiX;
			xEnd = roiX + roiWidth;
			divs[i] = new Division(numRows, yStart, yLimit, xStart, xEnd);
		}
	}
}

	private void setDivsionsWithKernel(int roiX, int roiY, int roiWidth, int roiHeight,int width, int height){
		int numRows, yStart, yLimit, xEnd,xStart;
		
		for (int i = 0; i < numThreads; i++){
			if ( i == (numThreads - 1)){
				// add remainder rows for the last thread if the roiHeight is not a multiple of numThreads
				numRows = mod == 0 ? ratio : ratio + mod;
			} else {
				numRows = ratio;
			}
			int r = roiY+i*numRows;
			yStart = Math.max(1, roiY+i*numRows); //max
			yLimit = Math.min(yStart+numRows-1,height-2);
			xEnd = Math.min(width - 2,roiX + roiWidth-1);
			xEnd = Math.min(roiX + roiWidth - 1, width - 2);
			xStart=Math.max(roiX,1); //max	
			
			divs[i] = new Division(numRows, yStart, yLimit, xStart, xEnd);
		}		
	}	
	
	public Division getDivision(int index){
		return divs[index];
	}
	
	public Division[] getDivisions(){
		return divs;
	}
	
	public static Division[] splitDivision(Division div){
		Division[] split = new Division[2];
		
		int rem = div.numRows % 2; // remainder of rows
		int rows = div.numRows/2;
		int yStart = div.yStart;
		int yLimit = div.yLimit;
		int xStart = div.xStart;
		int xEnd = div.xEnd;
		
		split[0] = new Division(rows, yStart, (yStart + rows), xStart, xEnd);
		split[1] = new Division((rows + rem) , (yStart + rows), yLimit, xStart, xEnd);
		
		return split;
		
	}
	
	public void processThreads(Thread[] threads){
		
		// start threads
		for (Thread t : threads){
//			long name = t.getId();
//		    System.out.println("Thread id: " + name);
			t.start();
		}
		// wait for threads to finish
		for (Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void processTasks(ConcurrentLinkedQueue<Runnable> tasks){
		PTRunner runner = new PTRunner(tasks);
		runner.run();
	}
	
	public void processFutures(Collection<Future<?>> futures){
		for (Future<?> future:futures) {
		    try {
				future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

}
	
