package ij.plugin.filter;

import static org.junit.Assert.*;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import java.net.URL;

import org.apache.commons.math3.stat.inference.TTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class FiltersTest {

	private final static double RANGE = 25;
	private final static double ALPHA = 0.05;
	
	private final ImagePlus imgA, imgB;
	private final int nChannels;


	public FiltersTest(String s, int n) {	
		URL url = this.getClass().getResource(s);
		imgA = new ImagePlus(url.getPath());
		imgB = new ImagePlus(url.getPath());
		nChannels = n;
	}

	//TODO: javadoc
	/**
	 * Image paths and the number of channels
	 * 
	 * @return
	 */
	@Parameters
	public static Collection<Object[]> testImages() {
		
		Object[][] images = new Object[][] { 
				{ "/resources/images/COLOR_RGB.jpg", 3 }, 
				{ "/resources/images/COLOR_256.jpg", 3 }, 
				{ "/resources/images/GRAY8.jpg", 1 },
				{ "/resources/images/GRAY16.jpg", 1 },
				{ "/resources/images/GRAY32.jpg", 1 }
		};
		return Arrays.asList(images);
	}

	@Test
	public void testAddNoiseSERIAL() {
		
		System.out.println("Image: "+imgA.getTitle());
		
		imgA.getProcessor().noise(RANGE, ImageProcessor.P_NONE);
		imgB.getProcessor().noise(RANGE, ImageProcessor.P_SERIAL);
		
		ArrayList<double[]> aChannles = getChannels(imgA, nChannels);
		ArrayList<double[]> bChannles = getChannels(imgB, nChannels);
		
		TTest test = new TTest();
		boolean reject;
		for (int i = 0; i < nChannels; i++){	
			//double pValue = test.pairedTTest(aChannles.get(i), bChannles.get(i));
			//System.out.println("Channel: "+i+", p-value: "+pValue);
			reject = test.pairedTTest(aChannles.get(i), bChannles.get(i), ALPHA);
			assertEquals(false, reject);
		}
	}
	
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