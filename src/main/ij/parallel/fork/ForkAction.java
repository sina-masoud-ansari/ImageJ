package ij.parallel.fork;

import ij.Prefs;
import ij.parallel.Division;
import ij.process.ImageProcessor;

import java.util.concurrent.RecursiveAction;

public abstract class ForkAction extends RecursiveAction {

	ImageProcessor ip;
	Runnable runnable;
	Division division;
	int limit;
	int numDivisions;
	
	public ForkAction(ImageProcessor ip, Runnable r, Division d, int limit, int numDivisions){
		this.ip = ip;
		runnable = r;
		division = d;
		this.limit = limit;
		this.numDivisions = numDivisions;
	}
	
	abstract void recurse();
	
	@Override
	protected void compute(){
		if (numDivisions == limit) {
			runnable.run();
		} else {
			recurse();
		}
	}
	
}