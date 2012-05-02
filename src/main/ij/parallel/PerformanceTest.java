package ij.parallel;

import java.io.File;

import ij.ImagePlus;
import ij.Prefs;
import ij.process.ImageProcessor;


public abstract class PerformanceTest {

	private String name;
	protected String path;
	private ImagePlus img;
	private ImageProcessor ip;
	private Results res; // holds results of test iteration.
	private int proc; //processors

	public PerformanceTest(String n, String p){
		name = n;
		path = p;
	}

	public void setup(){
		img = new ImagePlus(path);
		ip = img.getProcessor();
		res.setImageProperties(img.getTypeString(), img.getWidth(), img.getHeight());

	}
	
	public void setProcessors(int p){
		proc = p;
		Prefs.setThreads(p);
	}

	public void schedule(int repeat, boolean indep){
		String fname = new File(path).getName();
		res = new Results(name, fname, proc, repeat, indep);
		if (indep){
			for (int i=0; i< repeat; i++){
				try {
					timeIndependent(ImageProcessor.P_NONE, "P_NONE", i);
					timeIndependent(ImageProcessor.P_SERIAL, "P_SERIAL", i);
					timeIndependent(ImageProcessor.P_SIMPLE, "P_SIMPLE", i);			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		} else {		
			try {
				for (int i = 0; i < repeat; i++){
					timeDependent(ImageProcessor.P_NONE, "P_NONE", i);
				}
				for (int i = 0; i < repeat; i++){
					timeDependent(ImageProcessor.P_SERIAL, "P_SERIAL", i);
				}
				for (int i = 0; i < repeat; i++){
					timeDependent(ImageProcessor.P_SIMPLE, "P_SIMPLE", i);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}			
		}

	}

	private void timeIndependent(int mode, String modeName, int i) throws Exception{
		res.newCategory(modeName);
		res.start(modeName, "Setup");
		setup();
		res.stop(modeName, "Setup", i);
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
		res.stop(modeName, "Run", i);		
	}

	private void timeDependent(int mode, String modeName, int t) throws Exception{
		if (t == 0){
			timeDependentSetup(mode, modeName, t);			
		}
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
		res.stop(modeName, "Run", t);		
	}	
	
	private void timeDependentSetup(int mode, String modeName, int i) throws Exception{
		res.newCategory(modeName);
		res.start(modeName, "Setup");
		setup();
		res.stop(modeName, "Setup", i);	
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