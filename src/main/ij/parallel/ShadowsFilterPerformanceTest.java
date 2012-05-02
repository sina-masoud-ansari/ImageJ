package ij.parallel;

import ij.parallel.plugin.filter.ParallelShadows;
import ij.plugin.filter.Shadows;
import ij.process.ImageProcessor;


class ShadowsFilterPerformanceTest extends PerformanceTest {

	private static int[] kernel = {1,2,1, 0,1,0,  -1,-2,-1};
	public ShadowsFilterPerformanceTest(String path) {
		super("North", path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run_P_NONE(ImageProcessor ip) {
		Shadows ob=new Shadows();
		ob.north(ip);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run_P_SERIAL(ImageProcessor ip) {
		ParallelShadows ob= new ParallelShadows();
		ob.northSerial(ip);
	}

	@Override
	public void run_P_SIMPLE(ImageProcessor ip) {
		ParallelShadows ob= new ParallelShadows();
		ob.northSimple(ip);
	}
	
}
