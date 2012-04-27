package ij.parallel;

import java.util.ArrayList;

public class ParallelPerformanceTest {
	
	private int iter; //number of iterations
	private int maxt; //max threads to use;
	
	ArrayList<PerformanceTest> tests;
	
	public ParallelPerformanceTest(int i, int t){
		iter = i;
		maxt = t;
		tests = new ArrayList<PerformanceTest>();
	}
	
	public void add(PerformanceTest t){
		tests.add(t);
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
			Results r = t.getResults();
			System.out.println(r.collateCSV());
		}		
	}
	
	private void runDependent(PerformanceTest t){
		//System.out.println("Single setup, multiple run:\n");
		for (int n = 1; n <= maxt; n++){ // for each thread arrangement
			t.setProcessors(n);
			t.schedule(iter, false);
			Results r = t.getResults();
			System.out.println(r.collateCSV());
		}		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int maxt = Runtime.getRuntime().availableProcessors(); // max number of threads to use.
		int iter = Integer.parseInt(args[1]);
		String url = args[0];
		
		ParallelPerformanceTest test = new ParallelPerformanceTest(iter, maxt);
		test.add(new NoiseFilterPerformanceTest(url));
		//test.add(new SaltAndPepperFilterPerformanceTest(url));
		test.start();
	}

}

