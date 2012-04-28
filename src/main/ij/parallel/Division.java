package ij.parallel;

public class Division {

	public final int yIndex, numRows, yStart, yLimit, xStart, xEnd;
	
	public Division(int yIndex, int numRows, int yStart, int yLimit, int xStart, int xEnd){
		this.yIndex = yIndex;
		this.numRows = numRows;
		this.yStart = yStart;
		this.yLimit = yLimit;
		this.xStart = xStart;
		this.xEnd = xEnd;
	}	
	
}
