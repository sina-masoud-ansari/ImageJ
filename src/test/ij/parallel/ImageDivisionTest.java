package ij.parallel;

import static org.junit.Assert.*;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ShortProcessor;

import java.util.Arrays;
import java.util.Collection;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ImageDivisionTest {

	private final static String 
	
		// Example image paths for each image type
		P_COLOR_RGB = "/resources/images/tif/COLOR_RGB.tif",
		P_COLOR_256 = "/resources/images/tif/COLOR_256.tif",
		P_GRAY8 = "/resources/images/tif/GRAY8.tif",
		P_GRAY16 = "/resources/images/tif/GRAY16.tif",
		P_GRAY32 = "/resources/images/tif/GRAY32.tif";
		// TODO: need image with lines = threads (power 2 up to 16)
	
	private ImagePlus img;
	private final URL url;

	public ImageDivisionTest(String s) {	
		url = this.getClass().getResource(s);
	}

	@Parameters
	public static Collection<Object[]> testImages() {
		
		Object[][] images = new Object[][] { 
				{ P_COLOR_RGB }, { P_COLOR_RGB },
				{ P_COLOR_256 }, 
				{ P_GRAY8 }, 
				{ P_GRAY16 },
				{ P_GRAY32 }, 
		};
		return Arrays.asList(images);
	}
	
	@Before
	public void setUp(){
		img = new ImagePlus(url.getPath());	
	}
			
		
}