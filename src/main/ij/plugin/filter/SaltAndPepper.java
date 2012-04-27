package ij.plugin.filter;
import java.awt.*;
import java.util.*;

import ij.*;
import ij.process.*;

/** Implements ImageJ's Process/Noise/Salt and Pepper command. */
public class SaltAndPepper implements PlugInFilter {

	/** Parallelisation approach to be used for the filters 
	 *  P_NONE is the original Image Processor method
	 *  P_SERIAL is original method implemented in the parallel subclass of the Image Processor
	 *  P_SIMPLE is using simple thread launching
	 *  P_FORK_JOIN is using fork/join
	 *  P_EXECUTOR is using the ExecutorService with thread pools
	 *  P_PARALLEL_TASK is using the parallel task library
	 *  **/
	public static final int P_NONE=ImageProcessor.P_NONE, 
			P_SERIAL=ImageProcessor.P_SERIAL, 
			P_SIMPLE=ImageProcessor.P_SIMPLE, 
			P_FORK_JOIN=ImageProcessor.P_FORK_JOIN, 
			P_EXECUTOR=ImageProcessor.P_EXECUTOR, 
			P_PARALLEL_TASK=ImageProcessor.P_PARALLEL_TASK;
	
	Random r = new Random();
	private String arg;
	//private ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.arg = arg;
		//this.imp = imp;
		return IJ.setupDialog(imp, DOES_8G+DOES_8C+SUPPORTS_MASKING);
	}

	public void run(ImageProcessor ip) {
		
		//basic implementation
		if (arg.equals("salt_and_pepper")) {
			add(ip, 0.05, P_NONE);
	 		return;
	 	}
		
		//should add salt and pepper in serial
		if (arg.equals("salt_and_pepper serial")) {
			add(ip, 0.05, P_SERIAL);
	 		return;
	 	}
		
		//should add salt and pepper in simple
		if (arg.equals("salt_and_pepper simple")) {
			add(ip, 0.05, P_SIMPLE);
	 		return;
	 	}
	}

	public int rand(int min, int max) {
		return min + (int)(r.nextDouble()*(max-min));
	}

	public void add(ImageProcessor ip, double percent, int mode) {
		//sets up pixels for filter processing
		final Rectangle roi = ip.getRoi();
		final int n = (int)(percent*roi.width*roi.height);
		final byte[] pixels = (byte[])ip.getPixels();
		final int width = ip.getWidth();
		final int xmin = roi.x;
		final int xmax = roi.x+roi.width-1;
		final int ymin = roi.y;
		final int ymax = roi.y+roi.height-1;		
		
		
		switch (mode)
		{
		
		case P_NONE: //original implementation
			int rx;
			int ry;
			for (int i=0; i<n/2; i++) {
				rx = rand(xmin, xmax);
				ry = rand(ymin, ymax);
				pixels[ry*width+rx] = (byte)255;
				rx = rand(xmin, xmax);
				ry = rand(ymin, ymax);
				pixels[ry*width+rx] = (byte)0;
			}		
			break;
			
		case P_SERIAL:

			for (int i=0; i<n/2; i++) {
				rx = rand(xmin, xmax);
				ry = rand(ymin, ymax);
				pixels[ry*width+rx] = (byte)255;
				rx = rand(xmin, xmax);
				ry = rand(ymin, ymax);
				pixels[ry*width+rx] = (byte)0;
			}		
			break;
			
		case P_SIMPLE:
			//Divide the number of rows by the number of threads
			final int numThreads = Math.min(roi.height, Prefs.getThreads());
			//numThreads = 1;
			int ratio = roi.height / numThreads;
			Thread[] threads = new Thread[numThreads];
			for (int i = 0; i < numThreads; i++){
				final int yIndex = i;
				final int numRowsPerThread;
				if ( (i == numThreads - 1)){
					// add an additional row for the last thread if the roiHeight is not a multiple of numThreads
					numRowsPerThread = roi.height % numThreads == 0 ? ratio : ratio + (roi.height % numThreads);
				} else {
					numRowsPerThread = ratio;
				}
				threads[i] = new Thread(new Runnable(){
					@Override
					public void run() {
						int yStart = ymin + yIndex*numRowsPerThread;
						int yLimit = yStart + numRowsPerThread;
						int rx,ry;
						//filter is not done per pixel but per block of rows
						//random pixel is picked to be either 255 or 0
						//we need to decrease the percentage as it is per block
						for (int i=0; i<n/(2*numThreads); i++) {
							rx = rand(xmin, xmax);
							ry = rand(yStart, yLimit);
							pixels[ry*width+rx] = (byte)255;
							rx = rand(xmin, xmax);
							ry = rand(yStart, yLimit);
							pixels[ry*width+rx] = (byte)0;
						}							
					} // end run				
				}); // end new thread definition
			}
			// start threads
			for (Thread t : threads){
				t.start();
			}
			// wait for threads to finish
			for (Thread t : threads){
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
		
	}
}

