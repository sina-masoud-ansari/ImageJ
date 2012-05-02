package ij.parallel;

import java.util.ArrayList;

public class ParallelPerformanceTest {
	
	private int iter; //number of iterations
	private int maxt; //max threads to use;
	
	ArrayList<PerformanceTest> tests;
	ArrayList<Results> results;
	
	public ParallelPerformanceTest(int i, int t){
		iter = i;
		maxt = t;
		tests = new ArrayList<PerformanceTest>();
		results = new ArrayList<Results>();
	}
	
	public void add(PerformanceTest t){
		tests.add(t);
	}
	
	public void collate(){
		String res = "";
		for (Results r : results){
			if (res.isEmpty()){
				res += r.getHeaderCSV();
			}
			res += r.collateCSV();
		}
		System.out.println(res.trim());
	}
	
	public void start(){
		for (PerformanceTest t : tests){ // for each test
			runIndependent(t);
			runDependent(t);
		}
	}
	
	private void runIndependent(PerformanceTest t){
		//System.out.println("Single setup, single run:\n");
		for (int n = 1; n <= maxt; n++){ // for each thread arrangement
			t.setProcessors(n);
			t.schedule(iter, true);
			results.add(t.getResults());
		}
	}
	
	private void runDependent(PerformanceTest t){
		//System.out.println("Single setup, multiple run:\n");
		for (int n = 1; n <= maxt; n++){ // for each thread arrangement
			t.setProcessors(n);
			t.schedule(iter, false);
			results.add(t.getResults());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int maxt = Runtime.getRuntime().availableProcessors(); // max number of threads to use.
		String url = args[0];
		int iter = Integer.parseInt(args[1]);
		
		// Manual testing
		//String url = "/Users/smas036/Dev/LARGE_COLOR_RGB.tif";	
		//String url = "/Users/smas036/Dev/ImageJ/build/resources/images/sample/image1000x1000.tif";
		//String url = "/Users/smas036/Dev/ImageJ/resources/test/images/tif/GRAY8.tif";
		//int iter = 1;
		
		ParallelPerformanceTest test = new ParallelPerformanceTest(iter, maxt);
		test.add(new NoiseFilterPerformanceTest(url));
		test.add(new SaltAndPepperFilterPerformanceTest(url));
		//test.add(new ShadowsFilterPerformanceTest(url));
		test.start();
		test.collate();
	}

}

