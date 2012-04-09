package ij.process;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 * Class designed to implement ByteProcessor filters in parallel using a variety of approaches.
 * 
 * @author Sina Masoud-Ansari
 *
 */
public class ParallelByteProcessor extends ByteProcessor {
	
	public enum Type {SERIAL, SIMPLE, FORK_JOIN, EXECUTOR, PARALLEL_TASK}
	private Type type;

	public ParallelByteProcessor(Image img, Type t) {
		super(img);
		type = t;
	}

	public ParallelByteProcessor(int width, int height, Type t) {
		super(width, height);
		type = t;
	}

	public ParallelByteProcessor(int width, int height, byte[] pixels, Type t) {
		super(width, height, pixels);
		type = t;
	}

	public ParallelByteProcessor(int width, int height, byte[] pixels,
			ColorModel cm, Type t) {
		super(width, height, pixels, cm);
		type = t;
	}

	public ParallelByteProcessor(BufferedImage bi, Type t) {
		super(bi);
		type = t;
	}

	public ParallelByteProcessor(ImageProcessor ip, boolean scale, Type t) {
		super(ip, scale);
		type = t;
	}
	
	

}
