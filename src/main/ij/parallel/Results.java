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
	private String type;
	private String fname;
	private boolean indep;
	private int width, height;
	private int proc; //processors
	private HashMap<String, HashMap<String, ArrayList<long[]>>> cats;

	public Results(String s, String fn, int p, int t, boolean i){
		title = s;
		proc = p;
		type = "UNKNOWN";
		fname = fn;
		indep = i;
		numCat = DEF_NUM_CAT;
		numEvents = DEF_NUM_EVENTS;
		numTrials = t;
		cats = new HashMap<String, HashMap<String, ArrayList<long[]>>>(numCat);
	}
	
	public void setImageProperties(String t, int w, int h){
		type = t;
		width = w;
		height = h;
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
	
	public String getHeaderCSV(){
		String header="FName,IType,Pixels,Proc,Filter,Series";
		String akey = cats.keySet().toArray(new String[]{})[0];
		for (String e : cats.get(akey).keySet()){
			header += ","+e;
		}
		header+=",PType\n";
		return header;
	}
	
	public String collateCSV(){
		
		long sum = 0;
		int size = 0;
		String results = "";
		String rb = fname+","+type+","+width*height+","+proc+","+title+","+(indep?"INDEP":"DEP");
		String rt;
		
		// print results from each category
		for (String c : cats.keySet()){
			// print results from each event
			rt = "";
			for (String e : cats.get(c).keySet()){
				// print results from each trial
				for (long[] t : cats.get(c).get(e)){
					sum += t[2];
				}
				size = cats.get(c).get(e).size();
				rt += ","+(float)sum/(1000*size);
			}
			results += rb +rt +","+c+"\n";
		}

		return results;
		
	}	
	
}
