package ij.parallel.process;

import java.awt.Color;
import java.awt.Image;

import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

public class ParallelImageProcessor extends ImageProcessor {

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValue(double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBackgroundValue(double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getBackgroundValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMinAndMax(double min, double max) {
		// TODO Auto-generated method stub

	}

	@Override
	public void flipVertical() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fill(ImageProcessor mask) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getPixels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPixelsCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPixel(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void set(int x, int y, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void set(int index, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getf(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getf(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setf(int x, int y, float value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setf(int index, float value) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getInterpolatedPixel(double x, double y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPixelInterpolated(double x, double y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void putPixel(int x, int y, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getPixelValue(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void putPixelValue(int x, int y, double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawPixel(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPixels(Object pixels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void copyBits(ImageProcessor ip, int xloc, int yloc, int mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void applyTable(int[] lut) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image createImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageProcessor createProcessor(int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void snapshot() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swapPixelArrays() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset(ImageProcessor mask) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSnapshotPixels(Object pixels) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getSnapshotPixels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void convolve3x3(int[] kernel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void filter(int type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void medianFilter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void noise(double range, int mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public ImageProcessor crop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void threshold(int level) {
		// TODO Auto-generated method stub

	}

	@Override
	public ImageProcessor duplicate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void scale(double xScale, double yScale) {
		// TODO Auto-generated method stub

	}

	@Override
	public ImageProcessor resize(int dstWidth, int dstHeight) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rotate(double angle) {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getHistogram() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void erode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dilate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void convolve(float[] kernel, int kernelWidth, int kernelHeight) {
		// TODO Auto-generated method stub

	}

	@Override
	public FloatProcessor toFloat(int channelNumber, FloatProcessor fp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPixels(int channelNumber, FloatProcessor fp) {
		// TODO Auto-generated method stub

	}

}
