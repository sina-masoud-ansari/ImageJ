package ij.parallel.process;

import ij.Prefs;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.Random;

/**
 * Class designed to implement ByteProcessor filters in parallel using a variety of approaches.
 * This is for development and testing of parallelisation approaches.
 * 
 * @author Sina Masoud-Ansari
 *
 */
public class ParallelByteProcessor extends ByteProcessor {

	public ParallelByteProcessor(Image img) {
		super(img);
	}

	public ParallelByteProcessor(int width, int height) {
		super(width, height);
	}

	public ParallelByteProcessor(int width, int height, byte[] pixels) {
		super(width, height, pixels);
	}

	public ParallelByteProcessor(int width, int height, byte[] pixels, ColorModel cm) {
		super(width, height, pixels, cm);
	}

	public ParallelByteProcessor(BufferedImage bi) {
		super(bi);
	}

	public ParallelByteProcessor(ImageProcessor ip, boolean scale) {
		super(ip, scale);
	}

	@Override
    public void noise_P_NONE(double range) {
    	super.noise_P_NONE(range);
    }	

	@Override
	public void noise_P_SERIAL(double r) {
		final double range = r;	
		//Divide the number of rows by the number of threads
		int numThreads = Math.min(roiHeight, 1);
		int ratio = roiHeight / numThreads;
		int mod = roiHeight % numThreads;
		Thread[] threads = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++){
			final int yIndex = i;
			final int numRowsPerThread;
			if ( i == (numThreads - 1)){
				// add remainder rows for the last thread if the roiHeight is not a multiple of numThreads
				numRowsPerThread = mod == 0 ? ratio : ratio + mod;
			} else {
				numRowsPerThread = ratio;
			}
			threads[i] = new Thread(new Runnable(){
				@Override
				public void run() {
					int yStart = roiY+yIndex*numRowsPerThread;
					int yLimit = yStart + numRowsPerThread;
					int xEnd = roiX + roiWidth;
					int p, v, ran;
					boolean inRange;
					Random rnd = new Random();
					// for each row
					for (int y = yStart; y < yLimit; y++){
						// process pixels in ROI
						for (int x = roiX; x < xEnd; x++){
							// pixels is a 1D array so need to map to it
							p = y * roiWidth + roiX + x;
							inRange = false;
							while (!inRange){
								ran = (int)Math.round(rnd.nextGaussian()*range);
								v = (pixels[p] & 0xff) + ran;
								inRange = v>=0 && v<=255;
								if (inRange){
									pixels[p] = (byte)v;								
								}
							}			
						} // end x loop
						if (y%20==0) {
							showProgress((double)(y-roiY)/roiHeight);
						}
					} // end y loop
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
		// indicate processing is finished
		showProgress(1.0);
		
	}    
    
	@Override
    public void noise_P_SIMPLE(double r) {	
		final double range = r;	
		//Divide the number of rows by the number of threads
		int numThreads = Math.min(roiHeight, Prefs.getThreads());
		//numThreads = 1;
		int ratio = roiHeight / numThreads;
		int mod = roiHeight % numThreads;
		Thread[] threads = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++){
			final int yIndex = i;
			final int numRowsPerThread;
			if ( i == (numThreads - 1)){
				// add remainder rows for the last thread if the roiHeight is not a multiple of numThreads
				numRowsPerThread = mod == 0 ? ratio : ratio + mod;
			} else {
				numRowsPerThread = ratio;
			}
			threads[i] = new Thread(new Runnable(){
				@Override
				public void run() {
					int yStart = roiY+yIndex*numRowsPerThread;
					int yLimit = yStart + numRowsPerThread;
					int xEnd = roiX + roiWidth;
					int p, v, ran;
					boolean inRange;
					Random rnd = new Random();
					// for each row
					for (int y = yStart; y < yLimit; y++){
						// process pixels in ROI
						for (int x = roiX; x < xEnd; x++){
							// pixels is a 1D array so need to map to it
							p = y * roiWidth + roiX + x;
							inRange = false;
							while (!inRange){
								ran = (int)Math.round(rnd.nextGaussian()*range);
								v = (pixels[p] & 0xff) + ran;
								inRange = v>=0 && v<=255;
								if (inRange){
									pixels[p] = (byte)v;								
								}
							}			
						} // end x loop
						if (y%20==0) {
							showProgress((double)(y-roiY)/roiHeight);
						}
					} // end y loop
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
		// indicate processing is finished
		showProgress(1.0);
    }

	
	
}
