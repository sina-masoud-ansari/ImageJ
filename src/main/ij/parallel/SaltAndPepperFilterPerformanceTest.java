package ij.parallel;

import java.io.File;

import ij.parallel.plugin.filter.ParallelSaltAndPepper;
import ij.plugin.filter.PlugInFilterRunner;
import ij.plugin.filter.SaltAndPepper;
import ij.process.ImageProcessor;

public class SaltAndPepperFilterPerformanceTest extends PerformanceTest {

	private static final double PERCENT = 0.05;
	
	public SaltAndPepperFilterPerformanceTest(File file, int threads) {
		super(file, threads);
	}

	@Override
	public void run_P_NONE() {
		SaltAndPepper sp = new SaltAndPepper();
		sp.setup("salt_and_pepper", null);
		sp.run(ip);
		ip.salt_and_pepper_NONE(PERCENT);
	}

	@Override
	public void run_P_SERIAL() {
		ParallelSaltAndPepper parallel_sp = new ParallelSaltAndPepper();
		parallel_sp.setup("salt_and_pepper serial", null);
		parallel_sp.run(ip);	
		ip.salt_and_pepper_SERIAL(PERCENT);

	}

	@Override
	public void run_P_SIMPLE() {
		ParallelSaltAndPepper parallel_sp = new ParallelSaltAndPepper();
		parallel_sp.setup("salt_and_pepper simple", null);
		parallel_sp.run(ip);
		ip.salt_and_pepper_SIMPLE(PERCENT);

	}

	@Override
	public void run_P_EXECUTOR() {
		ParallelSaltAndPepper parallel_sp = new ParallelSaltAndPepper();
		parallel_sp.setup("salt_and_pepper simple", null);
		parallel_sp.run(ip);		
		ip.salt_and_pepper_EXECUTOR(PERCENT);
	}

	@Override
	public void run_P_PARATASK() {
		ParallelSaltAndPepper parallel_sp = new ParallelSaltAndPepper();
		parallel_sp.setup("salt_and_pepper simple", null);
		parallel_sp.run(ip);		
		ip.salt_and_pepper_PARATASK(PERCENT);
		
	}

	@Override
	public void run_P_FORK_JOIN() {
		// TODO Auto-generated method stub
		ParallelSaltAndPepper parallel_sp = new ParallelSaltAndPepper();
		parallel_sp.setup("salt_and_pepper forkjoin", null);
		parallel_sp.run(ip);		
		ip.salt_and_pepper_PARATASK(PERCENT);
	}	

}
