package ij.parallel.plugin.filter;

import ij.IJ;
import ij.plugin.filter.Shadows;
import ij.process.ImageProcessor;

public class ParallelShadows extends Shadows
{
	@Override
	public void run(ImageProcessor ip)
	{
	
		if (arg.equals("demo")) 
		{
			IJ.resetEscape();
			while (!IJ.escapePressed()) 
			{
				north(ip); imp.updateAndDraw(); ip.reset();
				northeast(ip); imp.updateAndDraw(); ip.reset();
				east(ip); imp.updateAndDraw(); ip.reset();
				southeast(ip); imp.updateAndDraw(); ip.reset();
				south(ip); imp.updateAndDraw(); ip.reset();
				southwest(ip); imp.updateAndDraw(); ip.reset();
				west(ip); imp.updateAndDraw(); ip.reset();
				northwest(ip); imp.updateAndDraw(); ip.reset();
			}
		}
		else if (arg.equals("north")) north(ip);
		else if (arg.equals("northSimple")) northSimple(ip);
		else if (arg.equals("northSerial")) northSerial(ip);
		else if (arg.equals("northExecutor")) northExecutor(ip);
		else if (arg.equals("northeast")) northeast(ip);
		else if (arg.equals("east")) east(ip);
		else if (arg.equals("southeast")) southeast(ip);
		else if (arg.equals("south")) south(ip);
		else if (arg.equals("southwest")) southwest(ip);
		else if (arg.equals("west")) west(ip);
		else if (arg.equals("northwest")) northwest(ip);

		
	}
	
	public void northExecutor(ImageProcessor ip) {
		
		int[] kernel = {1,2,1, 0,1,0,  -1,-2,-1};
		ip.convolve3x3_executor(kernel);
	}

	public void northSerial(ImageProcessor ip) 
	{
		int[] kernel = {1,2,1, 0,1,0,  -1,-2,-1};
		ip.convolve3x3_serial(kernel);
	}

	public void northSimple(ImageProcessor ip)
	{
		int[] kernel = {1,2,1, 0,1,0,  -1,-2,-1};
		ip.convolve3x3_simple(kernel);
	}
}
