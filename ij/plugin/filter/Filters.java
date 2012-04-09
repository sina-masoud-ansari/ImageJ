package ij.plugin.filter;
import ij.*;
import ij.gui.*;
import ij.process.*;
import java.awt.*;

/** This plugin implements the Invert, Smooth, Sharpen, Find Edges, 
	and Add Noise commands. */
public class Filters implements PlugInFilter {
	
	private static double sd = Prefs.getDouble(Prefs.NOISE_SD, 25.0);
	private String arg;
	private ImagePlus imp;
	private int slice;
	private boolean canceled;

	public int setup(String arg, ImagePlus imp) {
		this.arg = arg;
		this.imp = imp;
		if (imp!=null) {
			Roi roi = imp.getRoi();
			if (roi!=null && !roi.isArea())
				imp.killRoi(); // ignore any line selection
		}
		int flags = IJ.setupDialog(imp, DOES_ALL-DOES_8C+SUPPORTS_MASKING);
		if ((flags&PlugInFilter.DOES_STACKS)!=0 && imp.getType()==ImagePlus.GRAY16 && imp.getStackSize()>1 && arg.equals("invert")) {
				invert16BitStack(imp);
				return DONE;
		}
		return flags;
	}

	public void run(ImageProcessor ip) {
	
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
	 		ip.noise(25.0, "original");
	 		return;
	 	}
	 	
	 	// Adds noise in serial using the ParallelByteProcessor and a single thread
	 	
	 	// TODO: This approach is messy, perhaps there is a better way?
	 	
	 	// TODO: Need a way to test our implementations for correctness
	 	
	 	if (arg.equals("add serial")) {
	 		ip.noise(25.0, "serial");
	 		return;
	 	}	
	 	
	    // Adds noise in parallel using the ParallelByteProcessor and simple thread launching
	 	if (arg.equals("add simple")) {
	 		ip.noise(25.0, "simple");
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
	 		ip.noise(sd, "original");
	 		IJ.register(Filters.class);
	 		return;
	 	}
        	 	
	}
	
	void invert16BitStack(ImagePlus imp) {
		imp.killRoi();
		imp.getCalibration().disableDensityCalibration();
		ImageStatistics stats = new StackStatistics(imp);
		ImageStack stack = imp.getStack();
		int nslices = stack.getSize();
		int min=(int)stats.min, range=(int)(stats.max-stats.min);
		int n = imp.getWidth()*imp.getHeight();
		for (int slice=1; slice<=nslices; slice++) {
			ImageProcessor ip = stack.getProcessor(slice);
			short[] pixels = (short[])ip.getPixels();
			for (int i=0; i<n; i++) {
				int before = pixels[i]&0xffff;
				pixels[i] = (short)(range-((pixels[i]&0xffff)-min));
			}
		}
		imp.setStack(null, stack);
		imp.setDisplayRange(0, range);
		imp.updateAndDraw();
	}
	
	/** Returns the default standard deviation used by Process/Noise/Add Specified Noise. */
	public static double getSD() {
		return sd;
	}
	
}
