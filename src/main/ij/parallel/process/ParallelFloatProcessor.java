package ij.parallel.process;

import java.awt.image.ColorModel;

import ij.process.FloatProcessor;

public class ParallelFloatProcessor extends FloatProcessor {

	public ParallelFloatProcessor(int width, int height, float[] pixels) {
		super(width, height, pixels);
		// TODO Auto-generated constructor stub
	}

	public ParallelFloatProcessor(int width, int height, float[] pixels,
			ColorModel cm) {
		super(width, height, pixels, cm);
		// TODO Auto-generated constructor stub
	}

	public ParallelFloatProcessor(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	public ParallelFloatProcessor(int width, int height, int[] pixels) {
		super(width, height, pixels);
		// TODO Auto-generated constructor stub
	}

	public ParallelFloatProcessor(int width, int height, double[] pixels) {
		super(width, height, pixels);
		// TODO Auto-generated constructor stub
	}

	public ParallelFloatProcessor(float[][] array) {
		super(array);
		// TODO Auto-generated constructor stub
	}

	public ParallelFloatProcessor(int[][] array) {
		super(array);
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
