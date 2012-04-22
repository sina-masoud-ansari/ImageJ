package ij.parallel;

import ij.Prefs;

public class ImageDivision {
	
	public final int numThreads, mod, ratio;
	public final Division[] divs;

	public ImageDivision(int roiX, int roiY, int roiWidth, int roiHeight, int limit) {
		// TODO Auto-generated constructor stub
		numThreads = Math.min(Prefs.getThreads(), limit);
		ratio = roiHeight / numThreads;
		mod = roiHeight % numThreads;
		divs = new Division[numThreads];
		
		setDivsions(roiX, roiY, roiWidth);
	}
	
	public ImageDivision(int roiX, int roiY, int roiWidth, int roiHeight, int threads, int limit) {
		// TODO Auto-generated constructor stub
		numThreads = Math.min(threads, limit);
		ratio = roiHeight / numThreads;
		mod = roiHeight % numThreads;
		divs = new Division[numThreads];
		
		setDivsions(roiX, roiY, roiWidth);
	}	
	
	private void setDivsions(int roiX, int roiY, int roiWidth){
		int numRows, yStart, yLimit, xEnd;
		
		for (int i = 0; i < numThreads; i++){
			if ( i == (numThreads - 1)){
				// add remainder rows for the last thread if the roiHeight is not a multiple of numThreads
				numRows = mod == 0 ? ratio : ratio + mod;
			} else {
				numRows = ratio;
			}
			yStart = roiY+i*numRows;
			yLimit = yStart + numRows;
			xEnd = roiX + roiWidth;
			divs[i] = new Division(i, numRows, yStart, yLimit, xEnd);
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
	
