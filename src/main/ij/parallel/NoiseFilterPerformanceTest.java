package ij.parallel;

import ij.process.ImageProcessor;


class NoiseFilterPerformanceTest extends PerformanceTest {

	private static final double RANGE = 25.0;
	
	public NoiseFilterPerformanceTest(String path) {
		super("Add Noise", path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run_P_NONE(ImageProcessor ip) {
		ip.noise_P_NONE(RANGE);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run_P_SERIAL(ImageProcessor ip) {
		// TODO Auto-generated method stub
		ip.noise_P_SERIAL(RANGE);
	}

	@Override
	public void run_P_SIMPLE(ImageProcessor ip) {
		// TODO Auto-generated method stub
		ip.noise_P_SIMPLE(RANGE);
	}
	
}
