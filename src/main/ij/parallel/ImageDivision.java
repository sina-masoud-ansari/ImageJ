package ij.parallel;

import ij.Prefs;

public class ImageDivision {
	
	public final int numThreads, mod, ratio;
	public final Division[] divs;

	// Decide best number of threads automatically
	public ImageDivision(int roiX, int roiY, int roiWidth, int roiHeight) {
		// TODO Auto-generated constructor stub
		numThreads = Math.min(Prefs.getThreads(), roiHeight);
		ratio = roiHeight / numThreads;
		mod = roiHeight % numThreads;
		divs = new Division[numThreads];
		
		setDivsions(roiX, roiY, roiWidth);
	}
	
	// Set specific number of threads
	public ImageDivision(int roiX, int roiY, int roiWidth, int roiHeight, int threads) {
		// TODO Auto-generated constructor stub
		numThreads = Math.min(Math.max(threads, 1), roiHeight);
		ratio = roiHeight / numThreads;
		mod = roiHeight % numThreads;
		divs = new Division[numThreads];
		
		setDivsions(roiX, roiY, roiWidth);
	}	
	
	private void setDivsions(int roiX, int roiY, int roiWidth){
		int numRows, yStart, yLimit, xStart, xEnd;
		
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
			divs[i] = new Division(i, numRows, yStart, yLimit, xStart, xEnd);
		}		
	}
	
	public Division getDivision(int index){
		return divs[index];
	}
	
	public Division[] getDivisions(){
		return divs;
	}	
	
	public void processThreads(Thread[] threads){
		
		// start threads
		for (Thread t : threads){
			t.start();
		}
		// wait for threads to finish
		for (Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

}
	
