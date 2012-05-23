package ij.parallel;

import java.io.File;

import ij.parallel.plugin.filter.ParallelShadows;
import ij.plugin.filter.Shadows;
import ij.process.ImageProcessor;


class ShadowsFilterPerformanceTest extends PerformanceTest {

	private static int[] kernel = {1,2,1, 0,1,0,  -1,-2,-1};
	public ShadowsFilterPerformanceTest(File file, int threads) {
		super(file, threads);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run_P_NONE() {
		Shadows ob=new Shadows();
		ob.north(ip);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run_P_SERIAL() {
		ParallelShadows ob= new ParallelShadows();
		ob.northSerial(ip);
	}

	@Override
	public void run_P_SIMPLE() {
		ParallelShadows ob= new ParallelShadows();
		ob.northSimple(ip);
	}
	
}
