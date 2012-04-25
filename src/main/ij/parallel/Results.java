package ij.parallel;

import java.util.ArrayList;
import java.util.HashMap;


public class Results {
	
	private static final int
		DEF_NUM_CAT = 5,
		DEF_NUM_EVENTS = 2,
		DEF_NUM_TRIALS = 1;
	
	private int numCat;
	private int numEvents;
	private int numTrials;
	public final String title;
	private int proc; //processors
	private HashMap<String, HashMap<String, ArrayList<long[]>>> cats;
	
	/*
	public Results(String s, int p){
		title = s;
		proc = p;
		numCat = DEF_NUM_CAT;
		numEvents = DEF_NUM_EVENTS;
		numTrials = DEF_NUM_TRIALS;
		cats = new HashMap<String, HashMap<String, ArrayList<long[]>>>(numCat);
	}
	*/

	public Results(String s, int p, int t){
		title = s;
		proc = p;
		numCat = DEF_NUM_CAT;
		numEvents = DEF_NUM_EVENTS;
		numTrials = t;
		cats = new HashMap<String, HashMap<String, ArrayList<long[]>>>(numCat);
	}	
	
	public void newCategory(String c){
		if (!cats.containsKey(c)) {
			HashMap<String, ArrayList<long[]>> events = new HashMap<String, ArrayList<long[]>>(numEvents);
			cats.put(c, events);
		}
	}
	
	public void start(String c, String e){
		if (!cats.get(c).containsKey(e)){
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
	
	/*
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
	*/
	
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
	
	public String collate(){
		
		String summary = title+" "+proc+" cores\n";
		// print results from each category
		for (String c : cats.keySet()){
			summary += c+":\n";
			// print results from each event
			for (String e : cats.get(c).keySet()){
				summary += "\t"+e+": ";
				// print results from each trial
				for (long[] t : cats.get(c).get(e)){
					summary += (float)(t[2])/1000+" ";
				}
				summary += "\n";
			}
			summary += "\n";
		}
		return summary;
	}
	
}
