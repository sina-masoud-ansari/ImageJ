package ij.parallel;

import ij.parallel.plugin.filter.ParallelSaltAndPepper;
import ij.plugin.filter.PlugInFilterRunner;
import ij.plugin.filter.SaltAndPepper;
import ij.process.ImageProcessor;

public class SaltAndPepperFilterPerformanceTest extends PerformanceTest {

	private static final double PERCENT = 0.05;
	
	public SaltAndPepperFilterPerformanceTest(String path) {
		super("Salt and Pepper", path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run_P_NONE(ImageProcessor ip) {
		// TODO Auto-generated method stub
		SaltAndPepper sp = new SaltAndPepper();
		sp.setup("salt_and_pepper", null);
		sp.run(ip);
	}

	@Override
	public void run_P_SERIAL(ImageProcessor ip) {
		// TODO Auto-generated method stub
		ParallelSaltAndPepper parallel_sp = new ParallelSaltAndPepper();
		parallel_sp.setup("salt_and_pepper serial", null);
		parallel_sp.run(ip);

	}

	@Override
	public void run_P_SIMPLE(ImageProcessor ip) {
		// TODO Auto-generated method stub
		ParallelSaltAndPepper parallel_sp = new ParallelSaltAndPepper();
		parallel_sp.setup("salt_and_pepper simple", null);
		parallel_sp.run(ip);
	}

}
