
package edu.neu.csye6200.fd;

/**
 * A class that hold a hexagonal fluid frame
 *
 */
public class FluidFrame  {


	int frame[][] = null;
	int size = 16; // the current grid size
	private double avgDirection;
	private double avgMagnitude;
	/**
	 * Constructor
	 */
	public FluidFrame(int size) {
		this.size = size;
		frame = new int[size][size]; // Dynamically build the grid based on the input size
	}


	/**
	 * How big is this frame
	 * @return the grid size (square)
	 */
	public int getSize() {
		return size;
	}




	/**
	 * get the outbound particle state (direction of eminating particles)
	 * @param x
	 * @param y
	 * @return a FluidCell integer value
	 */
	public int getCellOutValue(int x, int y) {

		if(x>=0 && x<size && y>=0 && y<size) {

		return frame[x][y];
		}
		return 0;
	}

	/**;'
	 * Set the outbound particle state after an evaluation
	 * @param x
	 * @param y
	 * @param val
	 */
	public void setCellOutValue(int x, int y, int val) {
		if (x < 0) return;
		if (x >= size) return;
		if (y < 0) return;
		if (y >= size) return;
		frame[x][y] = val;
	}

	/**
	 * For a specified direction, set the output flag, indicating an exiting particle
	 * @param x
	 * @param y
	 * @param direction
	 */
	public void addCellOutParticle(int x, int y, int direction) {
		int curVal = getCellOutValue(x,y);
		curVal = FDCell.setFlag(curVal, direction);
		setCellOutValue(x,y,curVal);
	}

	/**
	 * Calculate the inbound particles that are headed towards a cell
	 * Do this by evaluating all neighbor cells, and calculating the input vectors 
	 * @param x
	 * @param y
	 */
	public int getCellInValue(int x, int y) {

		// Walk through all surrounding cells, and get the input value (header towards us or not)
		// Add this to our value
		// return the summarized result
		int inVal = 0;
		//int collision = 0;
		// Walk through each direction
		for (int dir = 0; dir < 6; dir++) {
			// Get the cell in a given direction from our current cell
			int neighborOutCell = getNeighborCellOutValue(x, y, dir);
			// Does it have a particle in the opposite direction?
			if (FDCell.hasDirectionFlag(neighborOutCell, FDCell.getOppositeDirection(dir)))
				inVal = FDCell.setFlag(inVal, dir); // If so, then add that direction to our inValue
				//collision += inVal;
		}
		return inVal;
	}

	/**
	 * Based on a given position and direction, locate a neighbor cell using a supplied direction
	 * and return its outbound particle values
	 * @param x input frame x coordinate
	 * @param y input frame y coordinate
	 * @param direction the direction to look in
	 * @return the outbound particle value for the neighbor cell
	 */
	private int getNeighborCellOutValue(int x, int y, int direction) {
		if ((y & 1) == 0) { // y is even
			switch (direction) {
			default:
			case 0: return (getCellOutValue(x-1, y));   // Left
			case 1: return (getCellOutValue(x-1, y-1)); // UL
			case 2: return (getCellOutValue(x  , y-1)); // UR
			case 3: return (getCellOutValue(x+1, y));   // Right
			case 4: return (getCellOutValue(x  , y+1)); // LR
			case 5: return (getCellOutValue(x-1, y+1)); // LL	
			}
		}
		else { // y is odd
			switch (direction) {
			default:
			case 0: return (getCellOutValue(x-1, y));   // Left
			case 1: return (getCellOutValue(x+1, y-1)); // UL
			case 2: return (getCellOutValue(x+2, y-1)); // UR
			case 3: return (getCellOutValue(x+1, y));   // Right
			case 4: return (getCellOutValue(x+2, y+1)); // LR
			case 5: return (getCellOutValue(x+1, y+1));   // LL	
			}
		}
	}

	/**
	 * Fill up the current frame up a a specified percentage (i.e. 100% yields an average of one particle direction per cell)
	 * @param percent
	 */
	public void addRandomParticles(double percent) {
		if (percent > 1.0) percent = 1.0; // Don't allow us to fill all cells

		int total = size * size; // This is the maximum number of cells

		total *= percent;

		for (int i = 0; i < total; i++)
			addRandomParticle();
		
	}

	/**
	 * Add a single random particle - give it a direction value
	 */
	private void addRandomParticle() {
		double maxSize = size + .99; // Well want a range from 0.00 to size.99. When integerized we'll get 0, 1, 2, ... size
		// Create a random X
		int x = (int) (Math.random() * maxSize);
		int y = (int) (Math.random() * maxSize);
		// Create a random y
		// Create a random direction
		int direction = (int) (Math.random() * 6.99);
		//direction = 3; // Test - force all particle to move right

		// Add the CellOutParticle
		addCellOutParticle(x,y,direction); // add it, or if the particle already exists, just overlay it

	}

	
	//Boundary conditions
	public void edge_conditions() {
		System.out.println("Resolving Edge Condition:");

		int temp_dir = 0;
		for (int y = 0; y <= getSize(); y++) {
			for (int x = 0; x <= getSize(); x++) {
				if (y == 0) {
					if (x == 0) {
						temp_dir = getCellOutValue(x, y);

						if( (FDCell.hasDirectionFlag(temp_dir, 0)) 		//resolves top-left most corner particles
								|| (FDCell.hasDirectionFlag(temp_dir, 1)) 
								|| (FDCell.hasDirectionFlag(temp_dir, 2)) 
								|| (FDCell.hasDirectionFlag(temp_dir, 5)))
						{
							setCellOutValue(x, y, 16);	

						}

					}
					else if (x == getSize() - 1) {
						temp_dir = getCellOutValue(x, y);

						if( (FDCell.hasDirectionFlag(temp_dir, 1)) 		//resolves top-right most corner particles
								|| (FDCell.hasDirectionFlag(temp_dir, 2)) 
								|| (FDCell.hasDirectionFlag(temp_dir, 3)) 
								|| (FDCell.hasDirectionFlag(temp_dir, 4)))
						{
							setCellOutValue(x, y, 32);	
						}

					}


					else {
						temp_dir = getCellOutValue(x, y);		//resolves top-mid particles of the frame
						if( (FDCell.hasDirectionFlag(temp_dir, 1)) 
								|| (FDCell.hasDirectionFlag(temp_dir, 2)) )

						{  setCellOutValue(x, y, 16); }	

					}
				}		


				if (y == getSize() - 1) {		//resolves bottom left corner particles of the frame
					if (x == 0) {
						temp_dir = getCellOutValue(x, y);

						if( (FDCell.hasDirectionFlag(temp_dir, 0)) 
								|| (FDCell.hasDirectionFlag(temp_dir, 1)) 
								|| (FDCell.hasDirectionFlag(temp_dir, 4)) 
								|| (FDCell.hasDirectionFlag(temp_dir, 5)))

						{ setCellOutValue(x, y, 4);	}



					}
					else if (x == getSize() - 1) {		//resolves bottom-right corner particles of the frame
						temp_dir = getCellOutValue(x, y);

						if( (FDCell.hasDirectionFlag(temp_dir, 2)) 
								|| (FDCell.hasDirectionFlag(temp_dir, 3)) 
								|| (FDCell.hasDirectionFlag(temp_dir, 4))
								|| (FDCell.hasDirectionFlag(temp_dir, 5)))

						{ setCellOutValue(x, y, 1);	}

					}


					else {
						temp_dir = getCellOutValue(x, y);		//resolves bottom-mid particles of the frame
						if ((FDCell.hasDirectionFlag(temp_dir, 4)) 


								|| (FDCell.hasDirectionFlag(temp_dir, 5)) )

						{   setCellOutValue(x, y, 4);	}

					}

				}


				//System.out.println("Resolving Edge COndition:");


			}
		}

		for( int y=1; y<=getSize()-2; y++) {		//resolves right-mid particles of the frame

			{
				int x =0;
				temp_dir = getCellOutValue(x, y);
				if( (FDCell.hasDirectionFlag(temp_dir, 0)) 
						|| (FDCell.hasDirectionFlag(temp_dir, 1)) 
						|| (FDCell.hasDirectionFlag(temp_dir, 5)))
				

				{ setCellOutValue(x, y, 8);	}

			}
		}

		for ( int y=1; y<=getSize()-2; y++) {		//resolves left-mid particles of the frame
			{
				int x = getSize()-1;
				temp_dir = getCellOutValue(x, y);

				if( (FDCell.hasDirectionFlag(temp_dir, 2)) 
						|| (FDCell.hasDirectionFlag(temp_dir, 3)) 
						|| (FDCell.hasDirectionFlag(temp_dir, 4)))
					

				{ setCellOutValue(x, y, 32);	}

			}
		}


	}


	/**
	 * Draw the current frame to the console
	 */
	public void drawFrameToConsole() {
		char dispChar = ' ';
		for (int y = 0; y <getSize(); y++) {

//			if ((y & 1) > 0) // y is odd if true
//				System.out.print(" ");
			for (int x = 0; x < getSize(); x++) {

				int cel = getCellOutValue(x, y);
				if (FDCell.hasDirectionFlag(cel, 0)) dispChar = '0';
				else if (FDCell.hasDirectionFlag(cel, 1)) dispChar = '1';
				else if (FDCell.hasDirectionFlag(cel, 2)) dispChar = '2';
				else if (FDCell.hasDirectionFlag(cel, 3)) dispChar = '3';
				else if (FDCell.hasDirectionFlag(cel, 4)) dispChar = '4';
				else if (FDCell.hasDirectionFlag(cel, 5)) dispChar = '5';
				else dispChar = '-';

				System.out.print(dispChar + " ");


			}

			System.out.print("    ");


			for (int x = 0; x < getSize(); x++) {

				int cel = getCellOutValue(x, y);
				if (FDCell.hasDirectionFlag(cel, 0)) dispChar = '\u2190';
				else if (FDCell.hasDirectionFlag(cel, 1)) dispChar = '\u2196';
				else if (FDCell.hasDirectionFlag(cel, 2)) dispChar = '\u2197';
				else if (FDCell.hasDirectionFlag(cel, 3)) dispChar = '\u2192';
				else if (FDCell.hasDirectionFlag(cel, 4)) dispChar = '\u2198';
				else if (FDCell.hasDirectionFlag(cel, 5)) dispChar = '\u2199';
				else dispChar = '-';

				System.out.print(dispChar + " ");
			}
			System.out.println(""); // Carriage return
		}
	}
	


	public void calcAvgRegion(int yBegin, int xBegin, int stepSize) {
		double sumX = 0.0;
		double sumY = 0.0;
		int yEnd = yBegin + stepSize;
		int xEnd = xBegin + stepSize;
		for(int y = yBegin; y < yEnd; y++) {
			for(int x = xBegin; x < xEnd; x++) {
				int cellVal = getCellInValue(x,y);
				//From the value add to the sums

				if(FDCell.hasDirectionFlag(cellVal, 0)) { //Left
					sumX += -1.0;
				}

				if(FDCell.hasDirectionFlag(cellVal, 1)) { //Upper-Left
					sumX += -0.5;
					sumY += -0.87;
				}

				if(FDCell.hasDirectionFlag(cellVal, 2)) { //Upper-Right
					sumX += 0.5;
					sumY += -0.87;
				}

				if(FDCell.hasDirectionFlag(cellVal, 3)) { //Right
					sumX += 1.0;
				}

				if(FDCell.hasDirectionFlag(cellVal, 4)) { //Lower-Right
					sumX += 0.5;
					sumY += 0.87;
				}

				if(FDCell.hasDirectionFlag(cellVal, 4)) { //Lower-Left
					sumX += -0.5;
					sumY += 0.87;
				}
			}
		}

		//cal direction and magnitude
		double divisor = stepSize*stepSize * 3.0; //Assume a 50% saturation(3 out of 6 particles)

		double avgX = sumX / divisor;
		double avgY = sumY / divisor;

		avgMagnitude = Math.sqrt(avgX*avgX + avgY*avgY); //mag = sqrt(x^2 + y^2)
		avgDirection = Math.atan(avgX / avgY);
		
	}

	
	public void setAvgDirection(double avgDirection) {		
		this.avgDirection = avgDirection;
	}


	public void setAvgMagnitude(double avgMagnitude) {
		this.avgMagnitude = avgMagnitude;
	}


	public double getAvgDirection() {
		return avgDirection;
	}

	public double getAvgMagnitude() {
		return avgMagnitude;
	}
}





