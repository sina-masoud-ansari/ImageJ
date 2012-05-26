package ij.parallel.plugin.filter;

import static org.junit.Assert.*;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.net.URL;

import org.apache.commons.math3.stat.inference.TTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import paratask.runtime.ParaTask;

@RunWith(value = Parameterized.class)
public class ParallelFiltersTest {

	private final static int
		
		//Number of channels per image type
		CH_COLOR_RGB = 3,
		CH_COLOR_256 = 3,
		CH_GRAY8 = 1,
		CH_GRAY16 = 1,
		CH_GRAY32 = 1;
	
	private final static double
	
		// Statistical parameters
		RANGE = 25,
		ALPHA = 0.05,
		PERCENT = 0.05;

	private final static String 
	
		// Example image paths for each image type
		P_COLOR_RGB = "/resources/images/tif/COLOR_RGB.tif",
		P_COLOR_256 = "/resources/images/tif/COLOR_256.tif",
		P_GRAY8 = "/resources/images/tif/GRAY8.tif",
		P_GRAY16 = "/resources/images/tif/GRAY16.tif",
		P_GRAY32 = "/resources/images/tif/GRAY32.tif";
	
	private final static DecimalFormat DP3 = new DecimalFormat("#.###");
	
	private ImagePlus imgA, imgB;
	private final int nChannels, mode;
	private final URL url;

	public ParallelFiltersTest(String s, int n, int m) {	
		url = this.getClass().getResource(s);
		nChannels = n;
		mode = m;
	}

	//TODO: javadoc
	/**
	 * Image paths, number of channels and mode
	 * 
	 * @return
	 */
	@Parameters
	public static Collection<Object[]> testImages() {
		
		Object[][] images = new Object[][] { 
				{ P_COLOR_RGB, CH_COLOR_RGB, ImageProcessor.P_NONE }, { P_COLOR_RGB, CH_COLOR_RGB, ImageProcessor.P_SERIAL }, { P_COLOR_RGB, CH_COLOR_RGB, ImageProcessor.P_SIMPLE }, { P_COLOR_RGB, CH_COLOR_RGB, ImageProcessor.P_PARATASK },
				{ P_COLOR_256, CH_COLOR_256, ImageProcessor.P_NONE }, { P_COLOR_256, CH_COLOR_256, ImageProcessor.P_SERIAL }, { P_COLOR_256, CH_COLOR_256, ImageProcessor.P_SIMPLE }, { P_COLOR_256, CH_COLOR_256, ImageProcessor.P_PARATASK },
				{ P_GRAY8, CH_GRAY8, ImageProcessor.P_NONE }, { P_GRAY8, CH_GRAY8, ImageProcessor.P_SERIAL }, { P_GRAY8, CH_GRAY8, ImageProcessor.P_SIMPLE },  { P_GRAY8, CH_GRAY8, ImageProcessor.P_PARATASK },
				{ P_GRAY16, CH_GRAY16 , ImageProcessor.P_NONE }, { P_GRAY16, CH_GRAY16 , ImageProcessor.P_SERIAL }, { P_GRAY16, CH_GRAY16 , ImageProcessor.P_SIMPLE }, { P_GRAY16, CH_GRAY16 , ImageProcessor.P_PARATASK },
				{ P_GRAY32, CH_GRAY32, ImageProcessor.P_NONE }, { P_GRAY32, CH_GRAY32, ImageProcessor.P_SERIAL }, { P_GRAY32, CH_GRAY32, ImageProcessor.P_SIMPLE },  { P_GRAY32, CH_GRAY32, ImageProcessor.P_PARATASK }
		};
		return Arrays.asList(images);
	}
	
	@Before
	public void setUp(){
		ParaTask.init();
		imgA = new ImagePlus(url.getPath());
		imgB = new ImagePlus(url.getPath());	
	}

	
	@Test
	public void testAddNoise() {
		
		//System.out.println("Image: "+imgA.getTitle()+", mode: "+mode);
		
		imgA.getProcessor().noise_P_NONE(RANGE);
		ImageProcessor ipB = imgB.getProcessor();
		
		switch (mode){
			case ImageProcessor.P_NONE:
				ipB.noise_P_NONE(RANGE);
				break;
			case ImageProcessor.P_SERIAL:
				ipB.noise_P_SERIAL(RANGE);
				break;
			case ImageProcessor.P_SIMPLE:
				ipB.noise_P_SIMPLE(RANGE);
				break;
			case ImageProcessor.P_PARATASK:
				ipB.noise_P_PARATASK(RANGE);
				break;		
		}
		
		ArrayList<double[]> aChannles = getChannels(imgA);
		ArrayList<double[]> bChannles = getChannels(imgB);
		
		TTest test = new TTest();
		boolean reject;
		for (int i = 0; i < nChannels; i++){
			reject = test.homoscedasticTTest(aChannles.get(i), bChannles.get(i), ALPHA);
			assertEquals(false, reject);
		}
	}
	
	@Test
	public void testSaltAndPepper() {
	
		imgA.getProcessor().salt_and_pepper_NONE(PERCENT);
		ImageProcessor ipB = imgB.getProcessor();
		
		switch (mode){
			case ImageProcessor.P_NONE:
				ipB.salt_and_pepper_NONE(PERCENT);
				break;
			case ImageProcessor.P_SERIAL:
				ipB.salt_and_pepper_SERIAL(PERCENT);
				break;
			case ImageProcessor.P_SIMPLE:
				ipB.salt_and_pepper_SIMPLE(PERCENT);
				break;
			case ImageProcessor.P_PARATASK:
				ipB.salt_and_pepper_PARATASK(PERCENT);
				break;
		}
		
		ArrayList<double[]> aChannles = getChannels(imgA);
		ArrayList<double[]> bChannles = getChannels(imgB);
		
		TTest test = new TTest();
		boolean reject;
		for (int i = 0; i < nChannels; i++){
			reject = test.tTest(aChannles.get(i), bChannles.get(i), ALPHA);
			assertEquals(false, reject);
		}
	}
	
	/**
	 * Add your ij.plugin.filters class test here
	 */
	//@Test
	//public void testMyFilter() {
	//	assertEquals(true, true);
	//}
	
	// TODO: need to write a test for this?
	private ArrayList<double[]> getChannels(ImagePlus imp) {
		
		ArrayList<double[]> list = new ArrayList<double[]>();
		int size = imp.getWidth()*imp.getHeight();
		for (int i = 0; i < nChannels; i++){
			list.add(new double[size]);
		}
		
		//create channels
		int[] pixel;
		int index;
		for (int y = 0; y < imp.getHeight(); y++){
			for (int x = 0; x < imp.getWidth(); x++){
				pixel = imp.getPixel(x, y);
				index = y*imp.getWidth() + x;
				for (int i = 0; i < list.size(); i++){
					list.get(i)[index] = pixel[i];
				}
			}
		}
		
		return list;	
	}
}