package ij.parallel.process;

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
public class ParallelImageProcessorTest {

	private final static String 
	
		// Example image paths for each image type
		P_COLOR_RGB = "/resources/images/tif/COLOR_RGB.tif",
		P_COLOR_256 = "/resources/images/tif/COLOR_256.tif",
		P_GRAY8 = "/resources/images/tif/GRAY8.tif",
		P_GRAY16 = "/resources/images/tif/GRAY16.tif",
		P_GRAY32 = "/resources/images/tif/GRAY32.tif";
	
	private ImagePlus img;
	private final URL url;

	public ParallelImageProcessorTest(String s) {	
		url = this.getClass().getResource(s);
	}

	@Parameters
	public static Collection<Object[]> testImages() {
		
		Object[][] images = new Object[][] { 
				{ P_COLOR_RGB }, 
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
	
	@Test
	public void testImageType(){
		
		String u = url.toString();	
		int type = img.getType();
		
		if (u.contains(P_COLOR_RGB)){
			assertEquals(ImagePlus.COLOR_RGB, type);
		} else if (u.contains(P_COLOR_256)){
			assertEquals(ImagePlus.GRAY8, type); // ImagePlus sets 8-bit color images to GRAY8
		} else if (u.contains(P_GRAY8)){
			assertEquals(ImagePlus.GRAY8, type);
		} else if (u.contains(P_GRAY16)){
			assertEquals(ImagePlus.GRAY16, type);
		} else if (u.contains(P_GRAY32)){
			assertEquals(ImagePlus.GRAY32, type);
		} else {
			fail("Bad image path");
		}
	}		

	@Test
	public void testImageBitDepth(){
		
		int type = img.getType();
		int bDepth = img.getBitDepth();
		
		switch (type){
		case ImagePlus.COLOR_RGB:
			assertEquals(24, bDepth);
			break;
		case ImagePlus.COLOR_256:
			assertEquals(8, bDepth);
			break;
		case ImagePlus.GRAY8:
			assertEquals(8, bDepth);
			break;
		case ImagePlus.GRAY16:
			assertEquals(16, bDepth);
			break;
		case ImagePlus.GRAY32:
			assertEquals(32, bDepth);
			break;		
		}
	}		

	@Test
	public void testImageProcessorType(){
		
		int type = img.getType();
		
		switch (type){
		case ImagePlus.COLOR_RGB:
			assertTrue(img.getProcessor() instanceof ColorProcessor);
			break;
		case ImagePlus.COLOR_256:
			assertTrue(img.getProcessor() instanceof ColorProcessor);
			break;
		case ImagePlus.GRAY8:
			assertTrue(img.getProcessor() instanceof ByteProcessor);
			break;
		case ImagePlus.GRAY16:
			assertTrue(img.getProcessor() instanceof ShortProcessor);
			break;
		case ImagePlus.GRAY32:
			assertTrue(img.getProcessor() instanceof FloatProcessor);
			break;		
		}
	}		
}