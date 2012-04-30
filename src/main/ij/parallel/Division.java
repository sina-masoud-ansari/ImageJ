package ij.parallel;

public class Division {

	public final int yIndex, numRows, yStart, yLimit, xEnd, xStart;
	
	
	public Division(int yIndex, int numRows, int yStart, int yLimit, int xEnd)
	{
		this.yIndex = yIndex;
		this.numRows = numRows;
		this.yStart = yStart;
		this.yLimit = yLimit;
		this.xEnd = xEnd;
		xStart = 0;
	
	}
	
	
	public Division(int yIndex, int numRows, int yStart, int yLimit, int xEnd,int xStart)
	{
		this.yIndex = yIndex;
		this.numRows = numRows;
		this.yStart = yStart;
		this.yLimit = yLimit;
		this.xEnd = xEnd;
		this.xStart=xStart;
		
		
		//3x3 kernel limits
		/*
		xMin = Math.max(xStart, 1);
		xMax = Math.min(xEnd - 1, width - 2);
		yMin = Math.max(yStart, 1);
		yMax = Math.min(yLimit - 1, height - 2);
		*/
		
		
	}		
	
}
