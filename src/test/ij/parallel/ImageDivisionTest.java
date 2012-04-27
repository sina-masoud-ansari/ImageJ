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


@RunWith(value = Parameterized.class)
public class ImageDivisionTest {

	private final static String 
	
		// Example image paths for each image type
		P_COLOR_RGB = "/resources/images/tif/COLOR_RGB.tif";
	
	private ImagePlus img;
	private Rectangle roi;
	private int maxThreads, minThreads;
	private final URL url;
	private ImageDivision idiv;

	public ImageDivisionTest(String s, Rectangle r, int mint, int maxt) {	
		url = this.getClass().getResource(s);
		roi = r;
		minThreads = mint;
		maxThreads = maxt;
		
	}

	@Parameters
	public static Collection<Object[]> testImages() {
		
		Rectangle r1 = new Rectangle(50, 75, 100, 127);
		Rectangle r2 = new Rectangle(0, 0, 100, 19);
		
		Object[][] images = new Object[][] { 
				{ P_COLOR_RGB, null, Prefs.getThreads(), 0 }, { P_COLOR_RGB, null, Prefs.getThreads(), 1 }, { P_COLOR_RGB, null, Prefs.getThreads(), 4 }, { P_COLOR_RGB, null, Prefs.getThreads(), 8 }, { P_COLOR_RGB, null, Prefs.getThreads(), 16 },
				{ P_COLOR_RGB, r1, Prefs.getThreads(), 0 }, { P_COLOR_RGB, r1, Prefs.getThreads(), 1 }, { P_COLOR_RGB, r1, Prefs.getThreads(), 4 }, { P_COLOR_RGB, r1, Prefs.getThreads(), 8 }, { P_COLOR_RGB, r1, Prefs.getThreads(), 16 },
				{ P_COLOR_RGB, r2, 5, r2.height }
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
		
		if (minThreads == 0){
			minThreads = Prefs.getThreads();
		}		
		
		if (maxThreads == 0){
			maxThreads = roi.height;
		}
		idiv = new ImageDivision(roi.x, roi.y, roi.width, roi.height, minThreads, maxThreads);
	}
	
	@Test
	public void testPrefsGetThreads(){
		int n = Runtime.getRuntime().availableProcessors();
		assertEquals(n, Prefs.getThreads());
	}	
	
	@Test
	public void testNumThreads(){
		int n = Math.min(minThreads, maxThreads);
		assertEquals(n, idiv.numThreads);
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
			
		
}