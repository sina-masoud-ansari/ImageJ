package ij.parallel;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.HashMap;

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
					Results r = t.getResults();
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
		test.add(new NoiseFilterPerformanceTest("Add Noise Filter", args[0]));
		test.start();
	}

}

abstract class PerformanceTest {
	
	private String name;
	private String path;
	private ImagePlus img;
	private ImageProcessor ip;
	private Results res; // holds results of test iteration.
	
	public PerformanceTest(String n, String p){
		name = n;
		path = p;
		res = new Results(name);
		
	}
	
	public void setup(){
		img = new ImagePlus(path);
		ip = img.getProcessor();
		
	}
	
	public void schedule(){
		
		try {
			time(ImageProcessor.P_NONE, "P_NONE");
			time(ImageProcessor.P_SERIAL, "P_SERIAL");
			time(ImageProcessor.P_SIMPLE, "P_SIMPLE");			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void time(int mode, String modeName) throws Exception{
		res.newCategory(modeName);
		res.start(modeName, "Setup");
		setup();
		res.stop(modeName, "Setup");
		res.start(modeName, "Run");
		switch (mode){
		case ImageProcessor.P_NONE:
			run_P_NONE(ip);
			break;
		case ImageProcessor.P_SERIAL:
			run_P_SERIAL(ip);
			break;
		case ImageProcessor.P_SIMPLE:
			run_P_SIMPLE(ip);
			break;
		default:
			throw new Exception("Unknown ImageProcessor mode: "+mode);	
		}
		res.stop(modeName, "Run");		
	}
	
	public void repeat(int n){
		setup();
		for (int i = 0; i < n; i++){
			run_P_NONE(ip);
		}
		for (int i = 0; i < n; i++){
			run_P_SERIAL(ip);
		}
		for (int i = 0; i < n; i++){
			run_P_SIMPLE(ip);
		}
		
	}
	
	public Results getResults(){
		return res;
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

class Results {
	
	private static final int
		DEF_NUM_CAT = 5,
		DEF_NUM_EVENTS = 2,
		DEF_NUM_TRIALS = 1;
	
	private int numCat;
	private int numEvents;
	private int numTrials;
	public final String title;
	private HashMap<String, HashMap<String, ArrayList<long[]>>> cats;
	
	public Results(String s){
		title = s;
		numCat = DEF_NUM_CAT;
		numEvents = DEF_NUM_EVENTS;
		numTrials = DEF_NUM_EVENTS;
		cats = new HashMap<String, HashMap<String, ArrayList<long[]>>>(numCat);
	}

	public Results(String s, int t){
		title = s;
		numCat = DEF_NUM_CAT;
		numEvents = DEF_NUM_EVENTS;
		numTrials = t;
		cats = new HashMap<String, HashMap<String, ArrayList<long[]>>>(numCat);
	}	
	
	public void newCategory(String c){
		HashMap<String, ArrayList<long[]>> events = new HashMap<String, ArrayList<long[]>>(numEvents);
		cats.put(c, events);
	}
	
	public void start(String c, String e){
		if (!cats.containsKey(c)){
			ArrayList<long[]> times = new ArrayList<long[]>(numTrials);  
			long[] trial = new long[3]; // {start, stop, difference}
			trial[0] = System.currentTimeMillis();
			times.add(trial);	
			cats.get(c).put(e, times);
		} else { //multiple trials
			ArrayList<long[]> times = cats.get(c).get(e);
			long[] trial = new long[3]; // {start, stop, difference}
			trial[0] = System.currentTimeMillis();
			times.add(trial);			
		}
	}
	
	// assuming 1 trial
	public void stop(String c, String e) throws Exception{
		if (numTrials != DEF_NUM_TRIALS) {
			throw new Exception("Number of trials must be "+DEF_NUM_TRIALS+". See Results.stop(String, String, int) for multiple trials");
		} else {
			ArrayList<long[]> times = cats.get(c).get(e);
			long[] trial = times.get(0);
			trial[1] = System.currentTimeMillis();	
			trial[2] = trial[1] - trial[0];
		}
	}
	
	// for multiple trials
	public void stop(String c, String e, int t) throws Exception{
		if (t > numTrials) {
			throw new Exception("Trial index is out of bounds: "+t);
		} else {
			ArrayList<long[]> times = cats.get(c).get(e);
			long[] trial = times.get(t);
			trial[1] = System.currentTimeMillis();	
			trial[2] = trial[1] - trial[0];
		}
	}	
	
	public void collate(){
		
	}
	
}
