package ij.parallel;

import java.io.File;

import ij.process.ImageProcessor;


class NoiseFilterPerformanceTest extends PerformanceTest {

	private static final double RANGE = 25.0;
	
	public NoiseFilterPerformanceTest(File file, int threads) {
		super(file, threads);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run_P_NONE() {
		ip.noise_P_NONE(RANGE);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run_P_SERIAL() {
		// TODO Auto-generated method stub
		ip.noise_P_SERIAL(RANGE);
	}

	@Override
	public void run_P_SIMPLE() {
		// TODO Auto-generated method stub
		ip.noise_P_SIMPLE(RANGE);
	}
	
}
