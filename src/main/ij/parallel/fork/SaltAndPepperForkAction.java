package ij.parallel.fork;

import java.util.Random;

import ij.parallel.Division;
import ij.parallel.ImageDivision;
import ij.parallel.fork.ForkAction;
import ij.process.ImageProcessor;

public class SaltAndPepperForkAction extends ForkAction {

	double percent;
	int n;
	Random rand;
	
	public SaltAndPepperForkAction(ImageProcessor ip, Runnable runnable, 
			Division d, int limit, int numDivisions, double percent,
			int n, Random rand) {
		super(ip, runnable, d, limit, numDivisions);
		this.percent = percent;
		this.n = n;
		this.rand = rand;
	}

	@Override
	void recurse() {
		Division[] divs = ImageDivision.splitDivision(division);
		SaltAndPepperForkAction first = new SaltAndPepperForkAction(ip, 
				ip.getSaltAndPepperRunnable(n,divs[0],divs.length,rand), divs[0], 
				limit, numDivisions+1, percent, n, rand);
		SaltAndPepperForkAction second = new SaltAndPepperForkAction(ip, 
				ip.getSaltAndPepperRunnable(n,divs[1],divs.length,rand), divs[1], 
				limit, numDivisions+1, percent, n, rand);
		invokeAll(first, second);
	}
	
}
