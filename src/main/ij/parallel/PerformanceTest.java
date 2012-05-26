package ij.parallel;

import java.io.File;

import ij.ImagePlus;
import ij.Prefs;
import ij.process.ImageProcessor;

public abstract class PerformanceTest {

	protected File file;
	protected int threads;
	protected ImagePlus img;	
	protected ImageProcessor ip;
	
	public PerformanceTest(File file, int threads){
		this.file = file;
		this.threads = threads;
	}
	
	protected void setup(){
		img = new ImagePlus(file.getAbsolutePath());
		ip = img.getProcessor();
		Prefs.setThreads(threads);		
	}
	
	public abstract void run_P_NONE();
	public abstract void run_P_SERIAL();
	public abstract void run_P_SIMPLE();
	public abstract void run_P_EXECUTOR();
	public abstract void run_P_PARATASK();
	public abstract void run_P_FORK_JOIN();
	
}

