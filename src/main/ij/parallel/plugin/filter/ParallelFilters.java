package ij.parallel.plugin.filter;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.Roi;
import ij.plugin.filter.Filters;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class ParallelFilters extends Filters {
	
	public final static double RANGE = 25.0;	
	
	@Override
	public void run(ImageProcessor ip) {
		

		if (arg.equals("invert")) {
			super.run(ip);
		}

		if (arg.equals("smooth")) {
			super.run(ip);
		}

		if (arg.equals("sharpen")) {
			super.run(ip);
		}

		if (arg.equals("edge")) {
			super.run(ip);
		}

		if (arg.equals("add")) {
			ip.noise_P_NONE(RANGE);
			return;
		}

		// Adds noise in serial using the ParallelByteProcessor and a single thread
		if (arg.equals("add serial")) {
			ip.noise_P_SERIAL(RANGE);
			return;
		}	

		// Adds noise in parallel using the ParallelByteProcessor and simple thread launching
		if (arg.equals("add simple")) {
			ip.noise_P_SIMPLE(RANGE);
			return;
		}
		
		if (arg.equals("add paratask")) {
			ip.noise_P_PARATASK(RANGE);
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
			//pip.noise_P_NONE(sd);
			IJ.register(Filters.class);
			return;
		}
	}
	// TODO: throw exception 	
}