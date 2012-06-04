<h1><span lang=EN-US>Image Processing with ImageJ: An investigation using
parallel processing techniques</span></h1>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US>Arvie Carpio, Rucha Pandav and Sina
Masoud-Ansari</span></p>

<p class=MsoNormal><span lang=EN-US>Department of Software Engineering</span></p>

<p class=MsoNormal><span lang=EN-US>The University of Auckland</span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US>ImageJ is widely used open source image
processing toolkit developed by the <a href="http://www.nih.gov/">National
Institutes of Health</a> (NIH). It has a plugin architecture which allows
communities to develop workflows suitable for a wide range of research
applications and leverage tools developed by others. </span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US>Image processing tasks can be
computationally intensive, especially on large data sets. ImageJ provides
high-level parallelisation for image stacks, which are images can be thought of
as layers or frames. When image processing tasks such as filters are applied to
an image stack, ImageJ will allocate slices in the stack to a group of threads
so that the overall processing time is much less.</span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US>In some cases, ImageJ also provides
image-level parallelisation where the pixels in an image are divided amongst a
group of threads for more efficient processing. Our project aimed to show how
parallelisation frameworks such as the Java Executor Service, Fork/Join and
Parallel Task can be used to improve the performance of standard ImageJ filters
such as Gaussian Noise, Shadows and Salt and Pepper. The results showed that
those filters can be modified to make use of a common parallelisation interface
called an ImageDivision, with improvements in the performance time.</span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US>Using ImageDivision is relatively straight
forward. In the ImageProcessor subclasses, implement a method that creates an
ImageDivision and a method that returns a Runnable that can process a Division.
A Division is a block of rows in an image.</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
12.0pt'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#646464'>@Override</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#7F0055'>public</span></b><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'> </span><b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#7F0055'>void</span></b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:black'> filter_P_EXECUTOR(){</span><span lang=EN-US style='font-size:
8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>ImageDivision
div = </span><b><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:#7F0055'>new</span></b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:black'> ImageDivision(</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#0000C0'>roiX</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'>, </span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#0000C0'>roiY</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'>, </span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#0000C0'>roiWidth</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'>, </span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#0000C0'>roiHeight</span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:black'>);</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>Collection&lt;Future&lt;?&gt;&gt;
futures = </span><b><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:#7F0055'>new</span></b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:black'> LinkedList&lt;Future&lt;?&gt;&gt;();</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#7F0055'>for</span></b><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'> (Division d : div.getDivisions()){</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>futures.add(</span><i><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#0000C0'>executor</span></i><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'>.submit(getFilterRunnable(d)));</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>}</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#3F7F5F'>// start tasks and wait for for them
to finish</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>div.processFutures(futures);<span
style='mso-tab-count:1'> </span></span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>}<o:p></o:p></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><b><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:#7F0055'>public</span></b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:black'> Runnable getFilterRunnable(</span><b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#7F0055'>final</span></b><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'> Division div){</span><span lang=EN-US style='font-size:
8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
style="mso-spacerun: yes">&nbsp;</span><span style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp; </span><span style='mso-tab-count:
1'>&nbsp;&nbsp; </span></span><b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#7F0055'>return</span></b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:black'> </span><b><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:#7F0055'>new</span></b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:black'> Runnable(){</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#646464'>@Override</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#7F0055'>public</span></b><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'> </span><b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#7F0055'>void</span></b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:black'> run() {</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#3F7F5F'>// for each row</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#7F0055'>for</span></b><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'> (</span><b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#7F0055'>int</span></b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:black'> y = div.</span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#0000C0'>yStart</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'>; y &lt;
div.</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;
font-family:Monaco;mso-bidi-font-family:Monaco;color:#0000C0'>yLimit</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:black'>; y++){<span style='mso-tab-count:
1'>&nbsp;&nbsp;&nbsp; </span></span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:5'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#3F7F5F'>// process pixels in ROI</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:5'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#7F0055'>for</span></b><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'> (</span><b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#7F0055'>int</span></b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:black'> x = div.</span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#0000C0'>xStart</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'>; x &lt;
div.</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;
font-family:Monaco;mso-bidi-font-family:Monaco;color:#0000C0'>xEnd</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:black'>; x++){</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:6'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#3F7F5F'>// pixels is a 1D array so need to
map to it</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:6'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>p
= y * </span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;
font-family:Monaco;mso-bidi-font-family:Monaco;color:#0000C0'>roiWidth</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:black'> + div.</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:#0000C0'>xStart</span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:black'> + x;<o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:6'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>pixels[p]
= 1; </span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;
font-family:Monaco;mso-bidi-font-family:Monaco;color:#3F7F5F'>// replace with
your filter code</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:5'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>}
</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;
font-family:Monaco;mso-bidi-font-family:Monaco;color:#3F7F5F'>// end x loop</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#7F0055'>if</span></b><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'> (y%20==0) {</span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:6'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>showProgress((</span><b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#7F0055'>double</span></b><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'>)(y-</span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#0000C0'>roiY</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'>)/</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#0000C0'>roiHeight</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'>);</span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:5'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>}</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>}
</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;
font-family:Monaco;mso-bidi-font-family:Monaco;color:#3F7F5F'>// end y loop</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>}
<span style='mso-tab-count:4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>};
<span style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp; </span><span style='mso-tab-count:
1'>&nbsp;&nbsp; </span></span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp; </span>}<span style='mso-tab-count:
1'>&nbsp; </span></span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
12.0pt'><o:p></o:p></span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US>Once you have a Runnable you can call other
methods such as Parallel Task:</span></p>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#646464'>@Override</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#7F0055'>public</span></b><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'> </span><b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#7F0055'>void</span></b><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:black'> filter_P_PARATASK(){</span><span lang=EN-US style='font-size:
8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>ImageDivision
div = </span><b><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:#7F0055'>new</span></b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:black'> ImageDivision(</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#0000C0'>roiX</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'>, </span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#0000C0'>roiY</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'>, </span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#0000C0'>roiWidth</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'>, </span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:#0000C0'>roiHeight</span><span lang=EN-US style='font-size:8.0pt;
mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;
color:black'>);</span><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>ConcurrentLinkedQueue&lt;Runnable&gt;
tasks = </span><b><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:#7F0055'>new</span></b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:black'>
ConcurrentLinkedQueue&lt;Runnable&gt;();</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><b><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco;color:#7F0055'>for</span></b><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco;color:black'> (Division d : div.getDivisions()){</span><span lang=EN-US
style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;mso-bidi-font-family:
Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>tasks.add(getNoiseRunnable(d));</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>}</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal style='mso-pagination:none;mso-layout-grid-align:none;
text-autospace:none'><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:2'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>div.processTasks(tasks);</span><span
lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:11.0pt;font-family:Monaco;
mso-bidi-font-family:Monaco'><o:p></o:p></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco;color:black'><span
style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>}<span
style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><o:p></o:p></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt;mso-bidi-font-size:
11.0pt;font-family:Monaco;mso-bidi-font-family:Monaco'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal style='tab-stops:90.0pt'><span lang=EN-US>For more
information on these classes, investigate the package ij.parallel in our <a
href="https://github.com/smas036/ImageJ">source repository</a>.</span></p>

<p class=MsoNormal style='tab-stops:90.0pt'><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<p class=MsoNormal style='tab-stops:90.0pt'><span lang=EN-US>For more details
on our project, see our <a href="http://where/ever/they/are">presentation
slides</a>.</span></p>

<p class=MsoNormal style='tab-stops:90.0pt'><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

</div>

</body>

</html>

