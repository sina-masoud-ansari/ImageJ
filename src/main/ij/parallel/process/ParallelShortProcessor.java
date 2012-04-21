package ij.parallel.process;

import ij.process.ShortProcessor;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class ParallelShortProcessor extends ShortProcessor {

	public ParallelShortProcessor(int width, int height, short[] pixels,
			ColorModel cm) {
		super(width, height, pixels, cm);
		// TODO Auto-generated constructor stub
	}

	public ParallelShortProcessor(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	public ParallelShortProcessor(BufferedImage bi) {
		super(bi);
		// TODO Auto-generated constructor stub
	}

	public ParallelShortProcessor(int width, int height, boolean unsigned) {
		super(width, height, unsigned);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void noise_P_NONE(double range) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noise_P_SERIAL(double range) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noise_P_SIMPLE(double range) {
		// TODO Auto-generated method stub
		
	}

}
