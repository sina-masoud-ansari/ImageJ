package ij.parallel;

import java.io.File;

public class TestRunnerFactory {	
	
	
	public static TestRunner createTestRunner(File file, String filter, String stage, int method, int threads, int iter){
		
		PerformanceTest test = createTest(file, filter, threads);
		TestRunner runner = new TestRunner(test, stage, method, iter);
		return runner;
	}
	
	public static TestRunner createTestRunner(File file, String filter, String stage, int method, int threads){
		
		PerformanceTest test = createTest(file, filter, threads);
		TestRunner runner = new TestRunner(test, stage, method);
		return runner;
	}	
	
	private static PerformanceTest createTest(File file, String filter, int threads){
		PerformanceTest test = null;
		if (filter.equals(ParallelPerformanceTest.ADD_NOISE)) {
			test = new NoiseFilterPerformanceTest(file, threads);
		} else if (filter.equals(ParallelPerformanceTest.SHADOWS)) {
			test = new ShadowsFilterPerformanceTest(file, threads);
		} else if (filter.equals(ParallelPerformanceTest.SALT_AND_PEPPER)) {
			test = new SaltAndPepperFilterPerformanceTest(file, threads);
		}
		
		if (test == null){
			ParallelPerformanceTest.printError("Unable to initialize the Performance Test for file: '"+file.getAbsolutePath()+"' and filter '"+filter+"'");
		}
		return test;
	}
	
}
