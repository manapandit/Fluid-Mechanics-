package edu.neu.csye6200.fd;

//import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import edu.neu.csye6200.fd.FluidFrame;
import static edu.neu.csye6200.fd.WolfApp.genNum;
import static edu.neu.csye6200.fd.WolfApp.no;


/**
 * A sample canvas that draws a rainbow of lines
 * @author
 */
@SuppressWarnings("deprecation")
public class FDCanvas extends JPanel implements Observer{

	private boolean done = false; // Set true to exit (i.e. stop) the simulation
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(FDCanvas.class.getName());
    private int lineSize = 20;		//how big is the line size
    private Color col = null;		//value of color is initialized to null
    private long counter = 0L;		//length of counter
    public int gnum = genNum;		//value of gnum is initialized to geNum
    private RuleA rule = null;		//RuleA is initially set to null
	private int MAX_FRAME_SIZE = 8; // How big is the simulation frame
	private int MAX_GENERATION = genNum; // How many generations will we calculate before we're through?
	private int genCount = 0; // the count of the most recent generation
	private FluidFrame currentFrame = null;
	private FluidFrameAvg currentFrameffa = null;
	private int x=0;
	private int radius=1;
	
	
    /**
     * CellAutCanvas constructor
     */
	public FDCanvas() {
		col = Color.WHITE;
		currentFrame = new FluidFrame(MAX_FRAME_SIZE);
	}
	
	FluidFrameSim set = new FluidFrameSim();


	/**
	 * The UI thread calls this method when the screen changes, or in response
	 * to a user initiated call to repaint();
	 */
	public void paint(Graphics g) {
		if (currentFrameffa== null)
			drawBG(g);
		else
		    drawFD(g); // Our Added-on drawing
    }
	
	
	
	//ArrayList<FluidFrame> fFrame = fSim.getList();
	/**
	 * Draw the CA graphics panel
	 * @param g
	 */
	public void drawBG(Graphics g) {
		
	Graphics2D g2d = (Graphics2D) g;
	Dimension size = getSize();
	
	g2d.setColor(Color.BLACK);
	g2d.fillRect(0, 0, size.width, size.height);
	
	int maxRows = size.height / lineSize;
	int maxCols = size.width / lineSize;
	
	for (int j = 0; j < maxRows; j++) {
		   for (int i = 0; i < maxCols; i++) {
			   int redVal = validColor(i*5);
			   int greenVal = validColor(255-j*5);
			   int blueVal = validColor((j*5)-(i*2));
			  
			   col = new Color(redVal, greenVal, blueVal);
			   // Draw box, one pixel less to create a black outline
			   int startx = i*lineSize;
			   int starty = j*lineSize;
			   int endx = startx + 15;
			   
			   
			   int endy = starty + 15;
			   paintLine( g2d, x , startx, starty, endx, endy, col); 
		   }
		   
		   System.out.println(" " );
		   
		}
	
	//currentFrameffa = currentFrameffa;
	
	char dispChar = ' ';
		int i=100;
		int j =100;
		double dir = 0.0;
		double mag = 0.0;
		for (int y = 0; y <4; y++) {
			
			if ((y & 1) > 0) // y is odd if true
				//g2d.drawString("  ", i+10, j+10);
			for (int x = 0; x < 4; x++) {
				
		   g2d.setColor(Color.RED);
		   
			   dir = currentFrameffa.getDirection(x, y);
		   
			   mag = currentFrameffa.getMagnitude(x, y);
		   if(mag == 0)
			   g2d.drawString("Flow Not possible", 200, 200);
		   
		 
			   int endx = i + (int) (mag * 15.0 * Math.cos(dir));
			   int endy = j + (int) (mag * 15.0 * Math.cos(dir));
			   paintLine(g2d, x , i, j, endx, endy, col);
			   System.out.print(dispChar + " ");
		       
			}
			System.out.println( " ");
			
		
	}
		
	}
	
	public void drawFD(Graphics g) {
		log.info("Drawing FD " + counter++);
		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();
		
		g2d.setColor(Color.BLACK);		//Background color
		g2d.fillRect(0, 0, size.width, size.height);
		
		g2d.setColor(Color.RED);
		g2d.drawString("MANALI", 10, 15);
		g2d.drawString("Moving lines of Fluid", 10, 30);
		
		g2d.setColor(Color.RED);
		g2d.drawString("Moving waves of Fluid", 550, 15);
		
		
		int maxRows = size.height / lineSize;
		int maxCols = size.width / lineSize;
		
		int ffaSize = currentFrameffa.getSize();
		if (maxRows > ffaSize) maxRows = ffaSize;
		if (maxCols > ffaSize) maxCols = ffaSize;
		
		
		for (int j = 0; j < maxRows; j++) {
		   for (int i = 0; i < maxCols; i++) {
			   int redVal = validColor(i*4);
			   int greenVal = validColor(100-j*8);
			   int blueVal = validColor((j*35)-(i*2));
			   
			   double dirVal = currentFrameffa.getDirection(j, i);
			  
			   
			   
			   col = new Color(redVal, greenVal, blueVal);
			   // Draw box, one pixel less to create a black outline
			   int startx = i*lineSize;
			   int starty = j*lineSize;
			   
			   double magVal = 1.0;
			   
			   int endx = startx + (int) (magVal * 15.0 * Math.sin(dirVal));
			   
			   int endy = starty + (int) (magVal * 15.0 * Math.cos(dirVal));
			   
			   int endx1 = startx + (int) (magVal * 5.0 * Math.sin(1.047));
			   
			   int endy1 = starty + (int) (magVal * 5.0 * Math.cos(1.047));
			   
			   int endx2 = startx + (int) (magVal * 5.0 * Math.sin(1.047));
			   
			   int endy2 = starty + (int) (magVal * 5.0 * Math.cos(1.047));
			   paintLine( g2d, x , startx, starty+30, endx, endy+30, col); 
		   }
		   
		   //System.out.println(" " );
		   
		}
	}
	
	/*
	 * A local routine to ensure that the color value is in the 0 to 255 range.
	 */
	private int validColor(int colorVal) {
		if (colorVal > 255)
			colorVal = 255;
		if (colorVal < 0)
			colorVal = 0;
		return colorVal;
	}
	

	/**
	 * A convenience routine to set the color and draw a line
	 * @param g2d the 2D Graphics context
	 * @param startx the line start position on the x-Axis
	 * @param starty the line start position on the y-Axis
	 * @param endx the line end position on the x-Axis
	 * @param endy the line end position on the y-Axis
	 * @param color the line color
	 */
	private void paintLine(Graphics2D g2d, int x, int startx, int starty, int endx, int endy, Color color) {
		g2d.setColor(color);
		g2d.drawLine(startx, starty, endx, endy);
		g2d.fillRoundRect(endx+550, endy, 45, 6, radius, radius);
	
	}
	
	
	
	@Override
    public void update(Observable o, Object arg) {		/*FDcanvas is the observer class, it will execute the changes notified by the FluidFrameSim*/
      
		 this.repaint();
         this.currentFrameffa = (FluidFrameAvg) arg; //set the value changed by the observable class
            repaint(); // repaint the entire canvas screen
    }
}

