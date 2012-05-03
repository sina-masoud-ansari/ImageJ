package ij.plugin.filter;
import java.awt.*;
import java.util.*;

import ij.*;
import ij.gui.Roi;
import ij.process.ImageProcessor;

/** Implements ImageJ's Process/Noise/Salt and Pepper command. */
public class SaltAndPepper implements PlugInFilter {

	Random r = new Random();
	protected String arg;
	protected Rectangle roi;
	protected int n;
	protected byte[] pixels;
	protected boolean isGreyscale = false;
	protected ImagePlus imp = null;

	public int setup(String arg, ImagePlus imp) {
		this.arg = arg;
		this.imp = imp;
		//return IJ.setupDialog(imp, DOES_8G+DOES_8C+SUPPORTS_MASKING);
		if (imp!=null) {
			Roi roi = imp.getRoi();
			if (roi!=null && !roi.isArea())
				imp.killRoi(); // ignore any line selection
		}
		int flags = IJ.setupDialog(imp, DOES_ALL-DOES_8C+SUPPORTS_MASKING);
		return flags;
	}

	public void run(ImageProcessor ip) {
		
		//basic implementation
		if (arg.equals("salt_and_pepper")) {
			ip.salt_and_pepper_NONE(0.05);
	 		return;
	 	}

	}
	
	public int rand(int min, int max) {
		return min + (int)(r.nextDouble()*(max-min));
	}

	public void setROIandPercent(ImageProcessor ip, double percent) {

			roi = ip.getRoi();
			n = (int)(percent*roi.width*roi.height);
			pixels = (byte[])ip.getPixels();

	}
	
	public void salt_and_pepper_NONE(ImageProcessor ip, double percent) {	
		setROIandPercent(ip, percent);
		int width = ip.getWidth();
		int xmin = roi.x;
		int xmax = roi.x+roi.width-1;
		int ymin = roi.y;
		int ymax = roi.y+roi.height-1;	
		int rx, ry;
		for (int i=0; i<n/2; i++) {
			rx = rand(xmin, xmax);
			ry = rand(ymin, ymax);
			pixels[ry*width+rx] = (byte)255;
			rx = rand(xmin, xmax);
			ry = rand(ymin, ymax);
			pixels[ry*width+rx] = (byte)0;
		}		
	}
}

