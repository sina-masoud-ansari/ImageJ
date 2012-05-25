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
		
		if (arg.equals("salt_and_pepper")) {
			ip.salt_and_pepper_NONE(0.05);
	 	}
		
		if (arg.equals("salt_and_pepper serial")) {
			ip.salt_and_pepper_SERIAL(0.05);
	 		return;
	 	}
		
		if (arg.equals("salt_and_pepper simple")) {
			ip.salt_and_pepper_SIMPLE(0.05);
	 		return;
	 	}
		
		if (arg.equals("salt_and_pepper executor")) {
			ip.salt_and_pepper_EXECUTOR(0.05);
	 		return;
		}
		
		if (arg.equals("salt_and_pepper paratask")) {
			ip.salt_and_pepper_PARATASK(0.05);
	 		return;
	 	}
		

	}	
	
}
