package ij.parallel.process;

import java.awt.Image;
import java.awt.Rectangle;

import ij.process.ByteProcessor;
import ij.process.ColorProcessor;

public class ParallelColorProcessor extends ColorProcessor {

	protected byte[] R, G, B;
	protected ByteProcessor r, g, b;
	protected Rectangle roi;
	
	public ParallelColorProcessor(Image img) {
		super(img);
		// TODO Auto-generated constructor stub
	}

	public ParallelColorProcessor(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	public ParallelColorProcessor(int width, int height, int[] pixels) {
		super(width, height, pixels);
		// TODO Auto-generated constructor stub
	}
	
	private void setup(){
		showProgress(0.01);
		R = new byte[width*height];
		G = new byte[width*height];
		B = new byte[width*height];		
		getRGB(R, G, B);
		roi = new Rectangle(roiX, roiY, roiWidth, roiHeight);
		
		r = new ByteProcessor(width, height, R, null);
		g = new ByteProcessor(width, height, G, null);	
		b = new ByteProcessor(width, height, B, null);	
		
		r.setRoi(roi);
		g.setRoi(roi);
		b.setRoi(roi);
		
		// Set background value and interpolation method
		r.setBackgroundValue((bgColor&0xff0000)>>16);
		g.setBackgroundValue((bgColor&0xff00)>>8);
		b.setBackgroundValue(bgColor&0xff);
		r.setInterpolationMethod(interpolationMethod);
		g.setInterpolationMethod(interpolationMethod);
		b.setInterpolationMethod(interpolationMethod);
		
		showProgress(0.15);		
	}
	
	private void finish() {
		R = (byte[])r.getPixels();
		G = (byte[])g.getPixels();
		B = (byte[])b.getPixels();
		
		setRGB(R, G, B);
		showProgress(1.0);		
	}


	@Override
	public void noise_P_NONE(double range) {
		super.noise_P_NONE(25.0);	

	}

	@Override
	public void noise_P_SERIAL(double range) {
		// TODO Auto-generated method stub
		setup();
		r.noise_P_SERIAL(range); showProgress(0.40);
		g.noise_P_SERIAL(range); showProgress(0.65);
		b.noise_P_SERIAL(range); showProgress(0.90);		
		finish();
		
	}

	@Override
	public void noise_P_SIMPLE(double range) {
		// TODO Auto-generated method stub
		setup();
		r.noise_P_SIMPLE(range); showProgress(0.40);
		g.noise_P_SIMPLE(range); showProgress(0.65);
		b.noise_P_SIMPLE(range); showProgress(0.90);		
		finish();		
		
	}

}
