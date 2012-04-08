SOFTENG 751 Assignment 
=======================

*Improve performance of ImageJ on large images using multithreading*

ImageJ uses multiple threads to process images where they can be treated as stacks of independent images.
A process or filter is applied to each image in the stack in parallel using a group of threads.

*Existing support*

ImageJ also has direct multi-threading support for some functions where the image can be treated as independent components.
The RankFilter group (Maximum, Minimum, Median etc) have already been parallelised by dividing the image into lines,
with a group of threads processing the lines (see ij.plugin.filter.RankFilter). 
This is done by determining how many threads can be used, then launching a set of threads,
each with a group of lines to process. For colour images it seems this is done for one channel at a time.

*Where multithreading can be helpful*

Not all functions have been multithreaded and there may be room to imporve the existing approach. 
The "Add Noise" process is not multithreaded and takes a considerable amount of time to process. 
This would require modifying the ij.process.ColourProcessor which calls ij.process.ByteProcessor.noise 
for each R, G, B channel in a colour image. It does not divide the image into lines and use multiple threads 
to process them nor does it use multiple threads to process each channel.
