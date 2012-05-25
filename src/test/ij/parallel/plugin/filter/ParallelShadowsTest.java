package ij.parallel.plugin.filter;

import static org.junit.Assert.*;
import ij.ImagePlus;
import ij.plugin.filter.Shadows;
import ij.process.ImageProcessor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.math3.stat.inference.TTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ParallelShadowsTest {

	private final static int
		
		//Number of channels per image type
		CH_COLOR_RGB = 3,
		CH_COLOR_256 = 3,
		CH_GRAY8 = 1,
		CH_GRAY16 = 1,
		CH_GRAY32 = 1;
	
	/*private final static double
	
		// Statistical parameters
		RANGE = 25,
		ALPHA = 0.05;*
*/
	private final static String 
	P_GRAY8 = "file://C:/Users/Rucha/git/ImageJ/resources/test/images/tif/GRAY8.tif",
	P_GRAY16 = "file://C:/Users/Rucha/git/ImageJ/resources/test/images/tif/GRAY16.tif",
	P_GRAY32 = "file://C:/Users/Rucha/git/ImageJ/resources/test/images/tif/GRAY32.tif",
	P_COLOR_256 = "file://C:/Users/Rucha/git/ImageJ/resources/test/images/tif/COLOR_256.tif",
	P_COLOR_RGB = "file://C:/Users/Rucha/git/ImageJ/resources/test/images/tif/COLOR_RGB.tif";
		 //Example image paths for each image type
		/*P_COLOR_RGB = "/resources/images/tif/COLOR_RGB.tif",
		P_COLOR_256 = "/resources/images/tif/COLOR_256.tif",
		P_GRAY8 = "/resources/images/tif/GRAY8.tif",
		P_GRAY16 = "/resources/images/tif/GRAY16.tif",
		P_GRAY32 = "/resources/images/tif/GRAY32.tif";*/
	
	private ImagePlus imgA, imgB,imgC,imgD,imgE;
	private final int nChannels, mode;
	private URL url;

	public ParallelShadowsTest(String s, int n, int m) 
	{	
		//url = this.getClass().getResource(s);
		url = null;
		try {
			url = new URL(s);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		nChannels = n;
		mode = m;
	}

	//TODO: javadoc
	/**
	 * Image paths, number of channels and mode
	 * 
	 * @return
	 */
	/*@Parameters
	public static Collection<Object[]> testImages() {
		
		Object[][] images = new Object[][] { 
				{ P_COLOR_RGB, CH_COLOR_RGB, ImageProcessor.P_NONE }, { P_COLOR_RGB, CH_COLOR_RGB, ImageProcessor.P_SERIAL }, { P_COLOR_RGB, CH_COLOR_RGB, ImageProcessor.P_SIMPLE },
				{ P_COLOR_256, CH_COLOR_256, ImageProcessor.P_NONE }, { P_COLOR_256, CH_COLOR_256, ImageProcessor.P_SERIAL }, { P_COLOR_256, CH_COLOR_256, ImageProcessor.P_SIMPLE },
				{ P_GRAY8, CH_GRAY8, ImageProcessor.P_NONE }, { P_GRAY8, CH_GRAY8, ImageProcessor.P_SERIAL }, { P_GRAY8, CH_GRAY8, ImageProcessor.P_SIMPLE },
				{ P_GRAY16, CH_GRAY16 , ImageProcessor.P_NONE }, { P_GRAY16, CH_GRAY16 , ImageProcessor.P_SERIAL }, { P_GRAY16, CH_GRAY16 , ImageProcessor.P_SIMPLE },
				{ P_GRAY32, CH_GRAY32, ImageProcessor.P_NONE }, { P_GRAY32, CH_GRAY32, ImageProcessor.P_SERIAL }, { P_GRAY32, CH_GRAY32, ImageProcessor.P_SIMPLE }
		};
		return Arrays.asList(images);
	}*/
	
	@Parameters
	public static Collection<Object[]> testImages() {
		
		Object[][] images = new Object[][] { {P_GRAY8 , CH_GRAY8, ImageProcessor.P_NONE },{ P_GRAY8, CH_GRAY8, ImageProcessor.P_SERIAL }, { P_GRAY8, CH_GRAY8, ImageProcessor.P_SIMPLE },
				{ P_COLOR_RGB, CH_COLOR_RGB, ImageProcessor.P_NONE }, { P_COLOR_RGB, CH_COLOR_RGB, ImageProcessor.P_SERIAL }, { P_COLOR_RGB, CH_COLOR_RGB, ImageProcessor.P_SIMPLE },
				{ P_COLOR_256, CH_COLOR_256, ImageProcessor.P_NONE }, { P_COLOR_256, CH_COLOR_256, ImageProcessor.P_SERIAL }, { P_COLOR_256, CH_COLOR_256, ImageProcessor.P_SIMPLE },
				{ P_GRAY16, CH_GRAY16 , ImageProcessor.P_NONE }, { P_GRAY16, CH_GRAY16 , ImageProcessor.P_SERIAL }, { P_GRAY16, CH_GRAY16 , ImageProcessor.P_SIMPLE },
				{ P_GRAY32, CH_GRAY32, ImageProcessor.P_NONE }, { P_GRAY32, CH_GRAY32, ImageProcessor.P_SERIAL }, { P_GRAY32, CH_GRAY32, ImageProcessor.P_SIMPLE }
				
		};
		return Arrays.asList(images);
	}
	
	@Before
	public void setUp(){
		imgA = new ImagePlus(url.getPath());
        imgB = new ImagePlus(url.getPath());
	    imgC = new ImagePlus(url.getPath());
	    imgD = new ImagePlus(url.getPath());
	    imgE = new ImagePlus(url.getPath());
		//System.out.println(url.getPath());
	}

	
	@Test
	public void testNorth() 
	{	
			Shadows ob = new Shadows();
			ParallelShadows ob1 = new ParallelShadows();
			ParallelShadows ob2 = new ParallelShadows();
			ParallelShadows ob3 = new ParallelShadows();
			ParallelShadows ob4 = new ParallelShadows();
			ob.north(imgA.getProcessor()); //P_NONE
			//ImageProcessor ipB = imgB.getProcessor();
			ob1.northSimple(imgB.getProcessor()); //P_SIMPLE
			ob2.northSerial(imgC.getProcessor());
			ob3.northExecutor(imgD.getProcessor());
			ob3.northParatask(imgE.getProcessor());
			
			int[] pixel1;
			int[] pixel2;
			int[] pixel3;
			int[] pixel4;
			int[] pixel5;
			
			//System.out.println( imgA.getWidth());
			
			for (int y = 0; y < imgA.getHeight(); y++)
			{
				for (int x = 0; x < imgA.getWidth(); x++)
				{
					pixel1 = imgA.getPixel(x, y);
					pixel2 = imgB.getPixel(x, y);
				    pixel3 = imgC.getPixel(x, y);
				    pixel4 = imgD.getPixel(x, y);
				    pixel5 = imgD.getPixel(x, y);
					
					for(int cnt=0;cnt<pixel1.length;cnt++)
					{ 
						int p1 = pixel1[cnt];
						int p2 = pixel2[cnt];
						int p3 = pixel3[cnt];
						int p4 = pixel4[cnt];
						int p5 = pixel5[cnt];
						
						assertEquals(p1,p2);
						assertEquals(p1,p3);
						assertEquals(p1,p4);
						assertEquals(p1,p5);
						
					}
					
				}
			}					
	}
		
}