package ij.plugin.filter;

import java.net.URL;
import java.util.ArrayList;

import ij.Executer;
import ij.ImageListener;
import ij.ImagePlus;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import junit.framework.TestCase;

import org.apache.commons.math3.stat.inference.TTest;
import org.junit.*;

public class FiltersTest extends TestCase {
	
	protected URL url; // will need to be a collection
	protected final static double RANGE = 25;
	protected final static double ALPHA = 0.05;
	
	@Before
	public void setUp() {	
		// TODO: need a collection of images for all iamge types 0-4
		url = this.getClass().getResource("/resources/images/eso0650a.jpg");
	}
	
	@Test
	public void testAddNoiseSERIAL() {
		ImagePlus a = new ImagePlus(url.getPath());
		// iterate through collection
		ImagePlus b = new ImagePlus(url.getPath());	
		a.getProcessor().noise(RANGE, ImageProcessor.P_NONE);
		b.getProcessor().noise(RANGE, ImageProcessor.P_SERIAL);
	
		
		// TODO: This looks like a bug??
		//System.out.println("Type: "+a.getType()+" Total Channels: "+a.getNChannels()); //problem here, returns 1 channel for RGB
		int nChannels;
		if (a.getType() == ImagePlus.COLOR_256 || a.getType() == ImagePlus.COLOR_RGB) {
			nChannels = 3;
		} else {
			nChannels = 1;
		}
		
		ArrayList<double[]> aChannles = getChannels(a, nChannels);
		ArrayList<double[]> bChannles = getChannels(b, nChannels);
		
		TTest test = new TTest();
		boolean reject;
		for (int i = 0; i < nChannels; i++){	
			//double pValue = test.pairedTTest(aChannles.get(i), bChannles.get(i));
			//System.out.println("Channel: "+i+", p-value: "+pValue);
			reject = test.pairedTTest(aChannles.get(i), bChannles.get(i), ALPHA);
			assertEquals(reject, false);
		}
	}
	
	// need to write a test for this
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