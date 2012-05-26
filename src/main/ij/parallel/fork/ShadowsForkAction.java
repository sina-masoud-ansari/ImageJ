package ij.parallel.fork;

import ij.parallel.Division;
import ij.parallel.ImageDivision;
import ij.parallel.fork.ForkAction;
import ij.process.ImageProcessor;

public class ShadowsForkAction extends ForkAction{
	
	double range;
	
	public ShadowsForkAction(ImageProcessor ip, Runnable r, Division d, int limit, int numDivisions, double range) {
		super(ip, r, d, limit, numDivisions);
		this.range = range;
	}

	@Override
	void recurse() {
		Division[] divs = ImageDivision.splitDivision(division);
		ShadowsForkAction first = new ShadowsForkAction(ip, ip.getRunnableConvolve(division), divs[0], limit, numDivisions+1, range);
		ShadowsForkAction second = new ShadowsForkAction(ip, ip.getRunnableConvolve(division), divs[1], limit, numDivisions+1, range);
		invokeAll(first, second);
	}
	
}