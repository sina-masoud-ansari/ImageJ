package ij.parallel.plugin.filter;

import ij.ImagePlus;
import ij.div.Division;
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
			ip.salt_and_pepper_NONE(0.05);
	 	}
		
		//should add salt and pepper in serial
		if (arg.equals("salt_and_pepper serial")) {
			ip.salt_and_pepper_SERIAL(0.05);
	 		return;
	 	}
		
		//should add salt and pepper in simple
		if (arg.equals("salt_and_pepper simple")) {
			ip.salt_and_pepper_SIMPLE(0.05);
	 		return;
	 	}
		
		System.out.println("going to ParallelSaltAndPepper PARATASK");
		if (arg.equals("salt_and_pepper paratask")) {
			System.out.println("in ParallelSaltAndPepper PARATASK");
			ip.salt_and_pepper_PARATASK(0.05);
	 		return;
		}
	}	
	
}
