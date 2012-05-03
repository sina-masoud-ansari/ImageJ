package ij.parallel;

import ij.parallel.plugin.filter.ParallelSaltAndPepper;
import ij.plugin.filter.PlugInFilterRunner;
import ij.plugin.filter.SaltAndPepper;
import ij.process.ImageProcessor;

public class SaltAndPepperFilterPerformanceTest extends PerformanceTest {

	private static final double PERCENT = 0.05;
	
	public SaltAndPepperFilterPerformanceTest(String path) {
		super("Salt and Pepper", path);
		super.path = path;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run_P_NONE(ImageProcessor ip) {
		// TODO Auto-generated method stub
		ip.salt_and_pepper_NONE(PERCENT);
	}

	@Override
	public void run_P_SERIAL(ImageProcessor ip) {
		// TODO Auto-generated method stub
		ip.salt_and_pepper_SERIAL(PERCENT);
	}

	@Override
	public void run_P_SIMPLE(ImageProcessor ip) {
		// TODO Auto-generated method stub
		ip.salt_and_pepper_SIMPLE(PERCENT);
	}

}
