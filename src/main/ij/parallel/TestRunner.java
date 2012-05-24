package ij.parallel;

import ij.process.ImageProcessor;

public class TestRunner {

	// Define the functions
	private static final int
		SETUP = 0,
		RUN = 1;
	
	private PerformanceTest test;
	private int stage;
	private int method;
	private int iter;
	private boolean doIter;
	
	public TestRunner(PerformanceTest test, String stage, int method) {
		this.test = test;
		this.method = method;
	
		doIter = false;
		setStage(stage);	
	}
	
	public TestRunner(PerformanceTest test, String stage, int method, int iter) {
		this.test = test;
		this.method = method;
		this.iter = iter;
		
		doIter = true;
		setStage(stage);	
	}
	
	private void setStage(String stage){
		if (stage.equals(ParallelPerformanceTest.SETUP)) {
			this.stage = SETUP;
		} else if (stage.equals(ParallelPerformanceTest.RUN)) {
			this.stage = RUN;
		} else {
			ParallelPerformanceTest.printError("Bad stage set: '"+stage+"'");
		}
	}	
	
	private void performSETUP(){
		test.setup();
	}
	
	// requires performSETUP to be called first
	private void performRUN(){
		switch (method){
			case ImageProcessor.P_NONE:
				test.run_P_NONE();
				break;
			case ImageProcessor.P_SERIAL:
				test.run_P_SERIAL();
				break;
			case ImageProcessor.P_SIMPLE:
				test.run_P_SIMPLE();
				break;
			case ImageProcessor.P_EXECUTOR:
				test.run_P_EXECUTOR();
				break;				
		}
	}
	
	public long run() {
		
		if (doIter) {
			long timeTotal = 0L;
			long timeStart, timeFinish;
			for (int i = 0; i < iter; i++){
				
				if (stage == SETUP) {
					timeStart = System.currentTimeMillis();
					performSETUP();
				} else {
					performSETUP();
					timeStart = System.currentTimeMillis();
					performRUN();
				}
				timeFinish =  System.currentTimeMillis();
				timeTotal += timeFinish - timeStart;
			}
			return timeTotal/iter;
		} else {
			long timeStart; 
			if (stage == SETUP) {
				timeStart = System.currentTimeMillis();
				performSETUP();
			} else {
				performSETUP();
				timeStart = System.currentTimeMillis();
				performRUN();
			}	
			long timeFinish =  System.currentTimeMillis();
			return timeFinish - timeStart;
		}
	}
	
}