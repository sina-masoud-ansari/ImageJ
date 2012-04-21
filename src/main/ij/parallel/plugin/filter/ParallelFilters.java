package ij.parallel.plugin.filter;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.parallel.process.ParallelImageProcessor;
import ij.plugin.filter.Filters;

public class ParallelFilters extends Filters {

	public void run(ParallelImageProcessor ip) {
	
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
	 		ip.noise(25.0, ParallelImageProcessor.P_NONE);
	 		return;
	 	}
	 	
	 	// Adds noise in serial using the ParallelByteProcessor and a single thread
	 		 	
	 	// TODO: Need a way to test our implementations for correctness
	 	
	    // TODO: Exception when type is not correct?
	 	
	 	if (arg.equals("add serial")) {
	 		ip.noise(25.0, ParallelImageProcessor.P_SERIAL);
	 		return;
	 	}	
	 	
	    // Adds noise in parallel using the ParallelByteProcessor and simple thread launching
	 	if (arg.equals("add simple")) {
	 		ip.noise(25.0, ParallelImageProcessor.P_SIMPLE);
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
	 		ip.noise(sd, ParallelImageProcessor.P_NONE);
	 		IJ.register(Filters.class);
	 		return;
	 	}
        	 	
	}


}