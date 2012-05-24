package ij.parallel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.media.jai.JAI;


public class SampleImageCreator {

    private static int HEIGHT;
    private static int WIDTH;
    private static String OUTDIR;

    /**
*
* @param args
* arg[0] is the width and height
* @throws IOException
*/
    public static void main(String[] args) throws IOException {
     if (args.length != 2){
     System.err.println("Usage: size dest");
     System.exit(127);
     }
     WIDTH = HEIGHT = Integer.parseInt(args[0]);
     OUTDIR = args[1];
        double[][] data = new double[WIDTH][HEIGHT];
        Random r = new Random();
        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++) {
                data[i][j] = r.nextDouble();
            }
        }

        /**
* Create image
*/
        final BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D)img.getGraphics();
        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++) {
                float c = (float) data[i][j];
                g.setColor(new Color(c, c, c));
                g.fillRect(i, j, 1, 1);
                data[i][j] = r.nextDouble();
            }
        }

        /**
* Show in a frame
*/
        /*
JFrame frame = new JFrame("Image test");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
JPanel panel = new JPanel() {
@Override
protected void paintComponent(Graphics g) {
Graphics2D g2d = (Graphics2D)g;
g2d.clearRect(0, 0, getWidth(), getHeight());
g2d.setRenderingHint(
RenderingHints.KEY_INTERPOLATION,
RenderingHints.VALUE_INTERPOLATION_BILINEAR);
// Or _BICUBIC
g2d.scale(2, 2);
g2d.drawImage(img, 0, 0, this);
}
};
panel.setPreferredSize(new Dimension(WIDTH*2, HEIGHT*2));
frame.getContentPane().add(panel);
frame.pack();
frame.setVisible(true);
*/
        
        /**
* Save image as .tif file
*/
        String imageName = "image" + HEIGHT + "x" + WIDTH + ".tif";
        System.out.println("Writing file: "+OUTDIR +"/"+ imageName);
        com.sun.media.jai.codec.TIFFEncodeParam params = new com.sun.media.jai.codec.TIFFEncodeParam();
        FileOutputStream os = new FileOutputStream(OUTDIR +"/"+ imageName);
        JAI.create("encode", img, os, "TIFF", params);
    }
}