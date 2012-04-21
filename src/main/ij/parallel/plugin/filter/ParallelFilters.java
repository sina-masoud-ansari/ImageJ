package ij.parallel.plugin.filter;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.parallel.process.ParallelImageProcessor;
import ij.plugin.filter.Filters;
import ij.process.ImageProcessor;

public class ParallelFilters extends Filters {

	@Override
	public void run(ImageProcessor ip) {
	
		if (ip instanceof ParallelImageProcessor){
		
			if (arg.equals("invert")) {
		 		ip.invert();
		 		return;
		 	}
		 	
			if (arg.equals("smooth")) {
				ip.setSnapshotCopyMode(true);
		 		ip.smooth();
				ip.setSnapshotCopyMode(false);
		 		return;
		 	}
		 	
			if (arg.equals("sharpen")) {
				ip.setSnapshotCopyMode(true);
		 		ip.sharpen();
				ip.setSnapshotCopyMode(false);
		 		return;
		 	}
		 	
			if (arg.equals("edge")) {
				ip.setSnapshotCopyMode(true);
				ip.findEdges();
				ip.setSnapshotCopyMode(false);
		 		return;
			}
							
		 	if (arg.equals("add")) {
		 		// ip.noise(25.0, ImageProcessor.P_NONE);
		 		((ParallelImageProcessor) ip).noise_P_NONE(25.0);
		 		return;
		 	}
		 	
		 	// Adds noise in serial using the ParallelByteProcessor and a single thread
		 		 	
		 	// TODO: Need a way to test our implementations for correctness
		 	
		    // TODO: Exception when type is not correct?
		 	
		 	if (arg.equals("add serial")) {
		 		//ip.noise(25.0, ImageProcessor.P_SERIAL);
		 		return;
		 	}	
		 	
		    // Adds noise in parallel using the ParallelByteProcessor and simple thread launching
		 	if (arg.equals("add simple")) {
		 		//ip.noise(25.0, ImageProcessor.P_SIMPLE);
		 		return;
		 	}	 	
		 	
		 	if (arg.equals("noise")) {
		 		if (canceled)
		 			return;
		 		slice++;
		 		if (slice==1) {
					GenericDialog gd = new GenericDialog("Gaussian Noise");
					gd.addNumericField("Standard Deviation:", sd, 2);
					gd.showDialog();
					if (gd.wasCanceled()) {
						canceled = true;
						return;
					}
					sd = gd.getNextNumber();
				}
		 		// TODO: Extend for custom noise to use parallel approaches
		 		//ip.noise(sd, ImageProcessor.P_NONE);
		 		IJ.register(Filters.class);
		 		return;
		 	}
		}
        	// TODO: throw exception 	
	}


}