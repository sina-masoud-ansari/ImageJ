package ij.parallel;

public class SinglePerformanceTest {

	static final int ARGC = 5;
	
	
	public static void printUsage(){
		System.out.println("Usage: filepath setup method threads function");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != ARGC){
			printUsage();
			System.exit(0);
		}

	}

}
