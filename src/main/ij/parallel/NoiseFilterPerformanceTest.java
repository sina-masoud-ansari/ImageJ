package ij.parallel;

import java.io.File;

import ij.process.ImageProcessor;


class NoiseFilterPerformanceTest extends PerformanceTest {

	private static final double RANGE = 25.0;
	
	public NoiseFilterPerformanceTest(File file, int threads) {
		super(file, threads);
	}

	@Override
	public void run_P_NONE() {
		ip.noise_P_NONE(RANGE);
		
	}

	@Override
	public void run_P_SERIAL() {
		ip.noise_P_SERIAL(RANGE);
	}

	@Override
	public void run_P_SIMPLE() {
		ip.noise_P_SIMPLE(RANGE);
	}
	
	@Override
	public void run_P_EXECUTOR() {
		ip.noise_P_EXECUTOR(RANGE);
	}
	
	@Override
	public void run_P_PARATASK() {
		ip.noise_P_PARATASK(RANGE);
	}	
	
	@Override
	public void run_P_FORK_JOIN() {
		ip.noise_P_FORK_JOIN(RANGE);
	}	
	
}
