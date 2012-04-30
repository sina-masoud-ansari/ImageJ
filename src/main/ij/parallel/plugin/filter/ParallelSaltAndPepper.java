package ij.parallel.plugin.filter;

import ij.ImagePlus;
import ij.parallel.Division;
import ij.parallel.ImageDivision;
import ij.plugin.filter.SaltAndPepper;
import ij.process.ImageProcessor;

public class ParallelSaltAndPepper extends SaltAndPepper {

	public int setup(String arg, ImagePlus imp) {
		return super.setup(arg, imp);
	}

	public void run(ImageProcessor ip) {
		
		//basic implementation
		if (arg.equals("salt_and_pepper")) {
			super.salt_and_pepper_NONE(ip, 0.05);
	 	}
		
		//should add salt and pepper in serial
		if (arg.equals("salt_and_pepper serial")) {
			salt_and_pepper_SERIAL(ip, 0.05);
	 		return;
	 	}
		
		//should add salt and pepper in simple
		if (arg.equals("salt_and_pepper simple")) {
			salt_and_pepper_SERIAL(ip, 0.05);
	 		return;
	 	}
	}
	
	public void salt_and_pepper_SERIAL(ImageProcessor ip, double percent) {
		ImageDivision div = new ImageDivision(ip.getRoi().x,ip.getRoi().y,ip.getRoi().width,ip.getRoi().height,1);
		Thread[] threads = new Thread[div.numThreads];
		super.setROIandPercent(ip, percent);
		for (int i = 0; i < div.numThreads; i++) {
			threads[i] = new Thread(getSaltAndPepperRunnable(super.n,div.getDivision(i),div.numThreads));
		}
		
		div.processThreads(threads);
	}
	
	public void salt_and_pepper_SIMPLE(ImageProcessor ip, double percent) {
		ImageDivision div = new ImageDivision(ip.getRoi().x,ip.getRoi().y,ip.getRoi().width,ip.getRoi().height,ip.getRoi().height);
		Thread[] threads = new Thread[div.numThreads];
		super.setROIandPercent(ip, percent);
		for (int i = 0; i < div.numThreads; i++) {
			threads[i] = new Thread(getSaltAndPepperRunnable(super.n,div.getDivision(i),div.numThreads));
		}
		
		div.processThreads(threads);
	}
	
	private Runnable getSaltAndPepperRunnable(final int n, final Division div, final int numThreads) {
		return new Runnable () {
			@Override
			public void run() {
				int rx,ry;
				//filter is not done per pixel but per block of rows
				//random pixel is picked to be either 255 or 0
				//we need to decrease the percentage as it is per block
				for (int i=0; i<n/(2*numThreads); i++) {
					rx = rand(div.xStart, div.xEnd);
					ry = rand(div.yStart, div.yLimit);
					pixels[ry*roi.width+rx] = (byte)255;
					rx = rand(div.xStart, div.xEnd);
					ry = rand(div.yStart, div.yLimit);
					pixels[ry*roi.width+rx] = (byte)0;
				}
			}
		};
	}
	
	
}
