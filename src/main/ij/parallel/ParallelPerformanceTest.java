package ij.parallel;

import ij.process.ImageProcessor;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import paratask.runtime.ParaTask;

public class ParallelPerformanceTest {

	static final int ARGC = 6;
	static File file;
	static String filter;
	static String setup;
	static String stage;
	static String methodString;
	static int method;
	static int threads;
	static int iter;
	
	static final String 
		ADD_NOISE = "Add Noise",
		SHADOWS = "Shadows",
		SALT_AND_PEPPER = "Salt and Pepper";
	
	static final String
		DEPENDENT = "DEPENDENT",
		INDEPENDENT = "INDEPENDENT";
	
	static final String
		P_NONE_STR = "P_NONE",
		P_SERIAL_STR = "P_SERIAL",
		P_SIMPLE_STR = "P_SIMPLE",
		P_EXECUTOR_STR = "P_EXECUTOR",
		P_PARATASK_STR = "P_PARATASK",
		P_FORK_JOIN_STR = "P_FORK_JOIN";
		// TODO: add executor etc
	
	static final String 
		SETUP = "SETUP",
		RUN = "RUN";
		
	
	protected static void printError(String msg){
		System.out.println("ERROR: "+msg);
		System.exit(1);
	}	
	
	private static void printUsage(){
		System.out.println("Usage: filepath filter setup method threads stage [iter]");
		System.exit(0);
	}
	
	private static boolean validFilter(String f){
		if (f.equals(ADD_NOISE)){
			return true;
		} else if (f.equals(SHADOWS)){
			return true;
		} else if (f.equals(SALT_AND_PEPPER)) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean validSetup(String s){
		if (s.equals(DEPENDENT)){
			return true;
		} else if (s.equals(INDEPENDENT)){
			return true;	
		} else {
			return false;
		}
	}
	
	private static boolean validMethod(String m){
		if (m.equals(P_NONE_STR)){
			method = ImageProcessor.P_NONE;
			return true;
		} else if (m.equals(P_SERIAL_STR)){
			method = ImageProcessor.P_SERIAL;
			return true;
		} else if (m.equals(P_SIMPLE_STR)) {
			method = ImageProcessor.P_SIMPLE;
			return true;
		} else if (m.equals(P_EXECUTOR_STR)) {
			method = ImageProcessor.P_EXECUTOR;
			return true;
		} else if (m.equals(P_PARATASK_STR)) {
			method = ImageProcessor.P_PARATASK;
			return true;
		} else if (m.equals(P_FORK_JOIN_STR)) {
			method = ImageProcessor.P_FORK_JOIN;
			return true;
		} else {
			return false;
		}
	}	
	
	private static void processArgs(String[] args){
		
		// Check for valid file
		file = new File(args[0]);
		if (!file.isFile()){
			printError("First argument must be a file");
		}
		
		// Check for valid filter
		filter = args[1];
		if (filter.isEmpty()){
			printError("Second argument must be a non-empty string");
		} else if (!validFilter(filter)){
			printError("Second argument must be one of {'"+ADD_NOISE+"','"+SHADOWS+"','"+SALT_AND_PEPPER+"'}");
		}
		
		// Check for valid setup option
		setup = args[2];
		if (setup.isEmpty()){
			printError("Third argument must be a non-empty string");
		} else if (!validSetup(setup)){
			printError("Third argument must be one of {'"+DEPENDENT+"','"+INDEPENDENT+"'}");
		}		
		
		// Check for valid method
		methodString = args[3];
		if (methodString.isEmpty()){
			printError("Fourth argument must be a non-empty string");
		} else if (!validMethod(methodString)){
			printError("Fourth argument must be one of {'"+P_NONE_STR+"','"+P_SERIAL_STR+"', '"+P_SIMPLE_STR+"', '"+P_EXECUTOR_STR+"', '"+P_PARATASK_STR+"', '"+P_FORK_JOIN_STR+"'}");
		}
		
		// Check for valid threads
		try {
			threads = Integer.parseInt(args[4]);
			if (threads < 1) {
				printError("Fifth argument must be an integer > 0");
			}
		} catch (NumberFormatException e){
			printError("Fifth argument must be an integer");
		}
		
		// Check for valid stage
		stage = args[5];
		if (stage.isEmpty()){
			printError("Sixth argument must be a non-empty string");
		} else if (!validMethod(methodString)){
			printError("Sixth argument must be one of {'"+SETUP+"','"+RUN+"'}");
		}		
		
		// Check for valid iter (reuqired for dependent tests)
		try {
			if (setup.equals(DEPENDENT)){
				if (args.length != 7){
					printError("Argument 'iterations' required for "+DEPENDENT+" configurations");
				} else {
					iter = Integer.parseInt(args[6]);
				}
				if (iter < 1) {
					printError("Argument 'iterations' should be a positive integer");
				}
			}
		} catch (NumberFormatException e){
			printError("Sixth argument must be an integer");
		}	
	}
	

	/**
	 * @param args
	 * 
	 * Argument vector is as follows:
	 * 
	 * filepath	: path to the image file
	 * filter	: the filter to perform on the image
	 * setup	: independent or dependent runs. Dependent runs use iter 		
	 * method	: the parallelisation method
	 * threads 	: number o threads to use
	 * stage	: what part of the filtering process to measure 
	 * [iter]	: Optional. The number of iterations to perform on dependent setups.
	 */
	public static void main(String[] args) {
		ParaTask.init();
		if (args.length < 6){
			String s = args.length+" ARGS:";
			for (String a : args){
				s+=" "+a;
			}
			System.out.println(s);
			printUsage();
		} else {
			processArgs(args);
		}
		
		TestRunner runner = null;
		if (setup.equals(DEPENDENT)){
			runner = TestRunnerFactory.createTestRunner(file, filter, stage, method, threads, iter);
		} else if (setup.equals(INDEPENDENT)){
			runner = TestRunnerFactory.createTestRunner(file, filter, stage, method, threads);
		}
		if (runner == null){
			printError("Unable to initialise test runner");
		} else {
			runner.run();
			String fname = file.getName();
			int nchannels = runner.test.img.getNChannels();
			int bitdepth = runner.test.img.getBitDepth();
			int totalPixels = runner.test.img.getWidth() * runner.test.img.getHeight();
			long timetaken = runner.timetaken;
			InetAddress addr = null;
			try {
				addr = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String hostname = addr.getHostName();
			
			// FileName, NumChannels, BitDepth, TotalPixels, Threads, Setup, Filter, Method, Stage, TimeTaken
			System.out.printf("%s,%s,%d,%d,%d,%d,%s,%s,%s,%s,%d\n", hostname, fname, nchannels, bitdepth, totalPixels, threads, setup, filter, methodString, stage, timetaken);
		}
	}

}
