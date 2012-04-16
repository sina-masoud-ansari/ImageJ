package ij.plugin.filter;

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

@RunWith(value = Parameterized.class)
public class FiltersTest {

	private final static int
	
		// Parallelisation approaches
		P_NONE = ImageProcessor.P_NONE,
		P_SERIAL = ImageProcessor.P_SERIAL,
		P_SIMPLE = ImageProcessor.P_SIMPLE,
		
		//Number of channels per image type
		CH_COLOR_RGB = 3,
		CH_COLOR_256 = 3,
		CH_GRAY8 = 1,
		CH_GRAY16 = 1,
		CH_GRAY32 = 1;
	
	private final static double
	
		// Statistical parameters
		RANGE = 25,
		ALPHA = 0.05;

	private final static String 
	
		// Example images of each image type
		COLOR_RGB = "/resources/images/COLOR_RGB.jpg",
		COLOR_256 = "/resources/images/COLOR_256.jpg",
		GRAY8 = "/resources/images/GRAY8.jpg",
		GRAY16 = "/resources/images/GRAY8.jpg",
		GRAY32 = "/resources/images/GRAY8.jpg";
	
	private final static DecimalFormat DP3 = new DecimalFormat("#.###");
	
	private ImagePlus imgA, imgB;
	private final int nChannels, mode;
	private final URL url;

	public FiltersTest(String s, int n, int m) {	
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
				{ COLOR_RGB, CH_COLOR_RGB, P_NONE }, { COLOR_RGB, CH_COLOR_RGB, P_SERIAL }, { COLOR_RGB, CH_COLOR_RGB, P_SIMPLE },
				{ COLOR_256, CH_COLOR_256, P_NONE }, { COLOR_256, CH_COLOR_256, P_SERIAL }, { COLOR_256, CH_COLOR_256, P_SIMPLE },
				{ GRAY8, CH_GRAY8, P_NONE }, { GRAY8, CH_GRAY8, P_SERIAL }, { GRAY8, CH_GRAY8, P_SIMPLE },
				{ GRAY16, CH_GRAY16 , P_NONE }, { GRAY16, CH_GRAY16 , P_SERIAL }, { GRAY16, CH_GRAY16 , P_SIMPLE },
				{ GRAY32, CH_GRAY32, P_NONE }, { GRAY32, CH_GRAY32, P_SERIAL }, { GRAY32, CH_GRAY32, P_SIMPLE }
		};
		return Arrays.asList(images);
	}
	
	@Before
	public void setUp(){
		imgA = new ImagePlus(url.getPath());
		imgB = new ImagePlus(url.getPath());		
	}

	@Test
	public void testAddNoise() {
		
		System.out.println("Image: "+imgA.getTitle()+", mode: "+mode);
		
		imgA.getProcessor().noise(RANGE, ImageProcessor.P_NONE);
		imgB.getProcessor().noise(RANGE, mode);
		
		ArrayList<double[]> aChannles = getChannels(imgA, nChannels);
		ArrayList<double[]> bChannles = getChannels(imgB, nChannels);
		
		TTest test = new TTest();
		boolean reject;
		for (int i = 0; i < nChannels; i++){
			double pValue = test.homoscedasticTTest(aChannles.get(i), bChannles.get(i));
			System.out.println("Channel: "+i+", p-value: "+Double.valueOf(DP3.format(pValue))+", alpha: "+ALPHA);
			reject = test.homoscedasticTTest(aChannles.get(i), bChannles.get(i), ALPHA);
			if (reject){			
				System.out.println("\tRejected with confidence: " + (1 - ALPHA));			
			}
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
	private ArrayList<double[]> getChannels(ImagePlus imp, int nChannels) {
		
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