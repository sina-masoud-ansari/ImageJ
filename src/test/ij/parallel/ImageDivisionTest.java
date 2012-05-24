package ij.parallel;

import ij.ImagePlus;
import ij.Prefs;
import java.util.Arrays;
import java.util.Collection;
import java.awt.Rectangle;
import java.net.URL;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ij.parallel.Division;


@RunWith(value = Parameterized.class)
public class ImageDivisionTest {

	private final static String 
	
		// Example image paths for each image type
		P_COLOR_RGB = "/resources/images/tif/COLOR_RGB.tif";
	
	private ImagePlus img;
	private Rectangle roi;
	private int threads;
	private final URL url;
	private ImageDivision idiv;
	
	public ImageDivisionTest(String s, Rectangle r, int t) {	
		url = this.getClass().getResource(s);
		roi = r;
		threads = t==0 ? Prefs.getThreads() : t;
		
	}

	@Parameters
	public static Collection<Object[]> testImages() {
		
		Rectangle r1 = new Rectangle(50, 75, 100, 127);
		Rectangle r2 = new Rectangle(0, 0, 100, 19);
		
		Object[][] images = new Object[][] { 
				{ P_COLOR_RGB, null, 0 }, { P_COLOR_RGB, null, 1 }, { P_COLOR_RGB, null, 2 }, { P_COLOR_RGB, null, 4}, { P_COLOR_RGB, null, 8}, { P_COLOR_RGB, null, 16},
				{ P_COLOR_RGB, r1, 0 }, { P_COLOR_RGB, r1, 1 }, { P_COLOR_RGB, r1, 2 }, { P_COLOR_RGB, r1, 4}, { P_COLOR_RGB, r1, 8}, { P_COLOR_RGB, r1, 16},
				{ P_COLOR_RGB, r2, 5 }
		};
		return Arrays.asList(images);
	}
	
	@Before
	public void setUp(){
		
		img = new ImagePlus(url.getPath());	
		if (roi == null){
			roi = new Rectangle(0,0, img.getWidth(), img.getHeight());
		} else {
			img.setRoi(roi);
		}
		
		idiv = new ImageDivision(roi.x, roi.y, roi.width, roi.height, threads);
	}
	
	@Test
	public void testPrefsGetThreads(){
		int n = Runtime.getRuntime().availableProcessors();
		assertEquals(n, Prefs.getThreads());
	}	
	
	@Test
	public void testNumThreads(){
		assertEquals(threads, idiv.numThreads);
	}
	
	@Test
	public void testNumDivs(){
		assertEquals(idiv.numThreads, idiv.getDivisions().length);
	}
	
	@Test
	public void testNumRows(){
		int sum = 0;
		for (Division d : idiv.getDivisions()){
			sum += d.numRows;
		}
		assertEquals(roi.height, sum);
	}
	
	@Test
	public void testYLimit(){
		int limit = idiv.getDivision(idiv.numThreads-1).yLimit;
		assertEquals(roi.height + roi.y, limit);
	}
	
	
	public void prefsTest(){
		Prefs.setThreads(threads);
		ImageDivision idiv2 = new ImageDivision(roi.x, roi.y, roi.width, roi.height);
		assertEquals(threads, idiv2.numThreads);
		Prefs.setThreads(0);
	}
			
		
}