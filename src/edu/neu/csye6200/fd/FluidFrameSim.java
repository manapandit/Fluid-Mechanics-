
package edu.neu.csye6200.fd;

import java.util.ArrayList;
import java.util.Observable;
import static edu.neu.csye6200.fd.WolfApp.pauseValue;
import static edu.neu.csye6200.fd.WolfApp.stopValue;
import static edu.neu.csye6200.fd.WolfApp.genNum;
import static edu.neu.csye6200.fd.WolfApp.no;

/**
 * A single frame of a CA Fluid Frame
 * 
 */
@SuppressWarnings("deprecation")
public class FluidFrameSim extends Observable implements Runnable {
	
	private ArrayList<FluidFrame> genList = new ArrayList<FluidFrame>();
	private boolean done = false; // Set true to exit (i.e. stop) the simulation
	
	
    private static int MAX_FRAME_SIZE = 256; // How big is the simulation frame
	private int MAX_GENERATION = genNum; // How many generations will we calculate before we're through?
	private int genCount = 0; // the count of the most recent generation

	private FluidFrame currentFrame = null;
	private FluidFrameAvg ffa = null;
	FluidFrame nextFrame = null;
	private RuleI rule = null;
	
	private static boolean paused = false; // Should we pause execution of our
	
	/**
	 * 
	 */
	public FluidFrameSim() {
		initGen();
		
	}

	public void initGen() {
		System.out.println("Inside FluidFrameSim" );
		currentFrame = new FluidFrame(MAX_FRAME_SIZE);
		currentFrame.addRandomParticles(0.75); // Only 75% of the cells should have a particle
		rule = new FDRule();
		ffa = new FluidFrameAvg(MAX_FRAME_SIZE/10);
		currentFrame.edge_conditions();
	}
	
	@Override
	public void run() {
		
		
		while(!done) {
			
			// Move target if needed
			if(pauseValue){ 
				
				
				try {
					try {
						wait();
					} catch (Exception e) {
					}
				} catch (IllegalMonitorStateException e) {
				}
			notifyAll();
			}
			
			if( no == 1) {
				nextFrame = rule.createNextFrame(currentFrame);
			}
			else if( no == 2) {
				nextFrame = rule.createNextFrame1(currentFrame);
			}
			
			// Average the results to create a lower-res display frame
			
			// Store the low-res picture and make it available for display
			
			// Advertise that we have a display displayable average FluidFrame and let it be drawn
			
			
			ffa.setFluidFrame(nextFrame);
			// Keep track of how many frames have been calculated
			System.out.println("\nFluidFrame: " + genCount);
			nextFrame.edge_conditions();
			nextFrame.drawFrameToConsole();
			
			genCount++; 
			currentFrame = nextFrame;
			setChanged();
	        notifyObservers(ffa); //notify canvas here
			
			if (genCount > MAX_GENERATION) done = true;
			
			

	           try {
	               Thread.sleep(1000); 			// Thread sleeps (i.e do no work)
	           } catch (InterruptedException ex) {
	           }
	           
	           if(stopValue) {
        		   try {
		               Thread.sleep(1000000);	// Thread sleeps (i.e do no work)
		           } catch (InterruptedException e) {
        	   }
        		   
           }		
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void run1(String[] args) {
		FluidFrameSim ffSim = new FluidFrameSim();
		
	}



}
