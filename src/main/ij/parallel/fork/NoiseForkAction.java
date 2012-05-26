package ij.parallel.fork;

import ij.parallel.Division;
import ij.parallel.ImageDivision;
import ij.parallel.fork.ForkAction;
import ij.process.ImageProcessor;

public class NoiseForkAction extends ForkAction{
	
	double range;
	
	public NoiseForkAction(ImageProcessor ip, Runnable r, Division d, int limit, int numDivisions, double range) {
		super(ip, r, d, limit, numDivisions);
		this.range = range;
	}

	@Override
	void recurse() {
		Division[] divs = ImageDivision.splitDivision(division);
		NoiseForkAction first = new NoiseForkAction(ip, ip.getNoiseRunnable(range, divs[0]), divs[0], limit, numDivisions+1, range);
		NoiseForkAction second = new NoiseForkAction(ip, ip.getNoiseRunnable(range, divs[1]), divs[1], limit, numDivisions+1, range);
		invokeAll(first, second);
	}
	
}