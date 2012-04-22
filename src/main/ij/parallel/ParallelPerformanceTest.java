package ij.parallel;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.util.ArrayList;

public class ParallelPerformanceTest {
	
	private int iter; //number of iterations
	private int maxt; //max threads to use;
	
	ArrayList<PerformanceTest> tests;
	
	public ParallelPerformanceTest(int i, int maxt){
		iter = i;
		tests = new ArrayList<PerformanceTest>();
	}
	
	public void add(PerformanceTest t){
		tests.add(t);
	}
	
	public void start(){
		for (PerformanceTest t : tests){ // for each test
			for (int n = 1; n < maxt; n++){ // for each thread arrangement
				for (int i = 0; i < iter; i++){ // repeat trials for average
					t.schedule();
					Object o = t.getResults();
				}
			}
			
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int maxt = Runtime.getRuntime().availableProcessors(); // max number of threads to use.
		String url = args[0];
		
		ParallelPerformanceTest test = new ParallelPerformanceTest(10, maxt);
		test.add(new NoiseFilterPerformanceTest("Noise Filter", args[0]));
		test.start();
	}

}

abstract class PerformanceTest {
	
	private String name;
	private String path;
	private ImagePlus img;
	private ImageProcessor ip;
	private Object results; // holds results of test iteration.
	
	public PerformanceTest(String name, String path){
		this.name = name;
		this.path = path;
		results = null;
		
	}
	
	public void setup(){
		img = new ImagePlus(path);
		ip = img.getProcessor();
		results = new Object();
	}
	
	public void schedule(){
		setup();
		run_P_NONE(ip);
		setup();
		run_P_SERIAL(ip);
		setup();
		run_P_SIMPLE(ip);
	}
	
	public Object getResults(){
		return results;
	}
	
	public abstract void run_P_NONE(ImageProcessor ip);
	public abstract void run_P_SERIAL(ImageProcessor ip);
	public abstract void run_P_SIMPLE(ImageProcessor ip);
	
	public String toString(){
		return "Name: "+name+" path: "+path;
		// TODO: add some other relevant img properties
	}
}

class NoiseFilterPerformanceTest extends PerformanceTest {

	private static final double RANGE = 25.0;
	
	public NoiseFilterPerformanceTest(String name, String path) {
		super(name, path);
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

