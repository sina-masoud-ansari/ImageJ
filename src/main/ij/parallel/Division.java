package ij.parallel;
/**
 * This class defines a section of an image to be processed by a filter in an Image Processor
 * @author Sina Masoud-Ansari (s.ansari@auckland.ac.nz)
 *
 */
public class Division {

	public final int numRows, yStart, yLimit, xStart, xEnd;	
	
	public Division(int numRows, int yStart, int yLimit, int xStart, int xEnd)
	{
		this.numRows = numRows;
		this.yStart = yStart;
		this.yLimit = yLimit;
		this.xStart = xStart;
		this.xEnd = xEnd;
	}	
	
}
