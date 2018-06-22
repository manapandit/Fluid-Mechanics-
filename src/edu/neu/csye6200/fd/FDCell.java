package edu.neu.csye6200.fd;

//         
//    2/1      4/2 
//      \   /
//       \ /
// 1/0 ---+---- 8/3
//       / \
//      /   \
//    32/5      16/4

/**
 * A helper class for working the particle cell values
 * @author
 */
public class FDCell {

	// Binary flag values - these may be overlaid in the same integer
	public static final int DIR_0 = 0b000001; //  1 - Left vector
	public static final int DIR_1 = 0b000010; //  2 - Up/Left vector
	public static final int DIR_2 = 0b000100; //  4 - Up/Right vector
	public static final int DIR_3 = 0b001000; //  8 - Right vector
	public static final int DIR_4 = 0b010000; // 16 - Down/Right vector
	public static final int DIR_5 = 0b100000; // 32 - Down/Left vector

	/**
	 * Determine if a cellValue has a particle moving in a particular direction
	 * @param cellVal the current cell particle value
	 * @param direction a direction to check against
	 * @return true if the cell has a particle moving int the indicated direction
	 */
	public static boolean hasDirectionFlag(int cellVal, int direction) {
		
		switch (direction) {
		default:
		case 0: return ((cellVal & DIR_0) > 0); // For direction 0, check to see if the direction 0 flag is set
		case 1: return ((cellVal & DIR_1) > 0); // "
		case 2: return ((cellVal & DIR_2) > 0);
		case 3: return ((cellVal & DIR_3) > 0);
		case 4: return ((cellVal & DIR_4) > 0);
		case 5: return ((cellVal & DIR_5) > 0);
		}
	}
	
	/**
	 * Add a direction flag to the supplied cell value
	 * @param cellVal the current cell particle state
	 * @param direction a new particle (with direction) to add
	 * @return the current cell with a new particle overlay
	 */
	public static int setFlag(int cellVal, int direction) {
		
		switch (direction) {
		default:
		case 0: cellVal = setDirectionFlag(cellVal, DIR_0); break;
		case 1: cellVal = setDirectionFlag(cellVal, DIR_1); break;
		case 2: cellVal = setDirectionFlag(cellVal, DIR_2); break;
		case 3: cellVal = setDirectionFlag(cellVal, DIR_3); break;
		case 4: cellVal = setDirectionFlag(cellVal, DIR_4); break;
		case 5: cellVal = setDirectionFlag(cellVal, DIR_5); break;
		}
		return cellVal;
	}
	
	/**
	 * Add a direction flag to the supplied cell value
	 * @param cellVal the current cell particle state
	 * @param direction a new particle (with direction) to add
	 * @return the current cell with a new particle overlay
	 */
	public static int setDirectionFlag(int cellVal, int directionFlag) {
		cellVal |= directionFlag; // Turn on the corresponding bits
		//System.out.println("---"+directionFlag);
		//System.out.println("cellval---"+cellVal);
		return cellVal; // return the result
	}
	
	/**
	 * For a given direction (i.e.0 through 5), return the opposite direction
	 * @param direction the input direction
	 * @return the opposite direction number
	 */
	public static int getOppositeDirection(int direction) {
		switch (direction) {
		default:
		case 0: return 3;
		case 1: return 4;
		case 2: return 5;
		case 3: return 0;
		case 4: return 1;
		case 5: return 2;
		}
	}
	
	public static int getCollidedParticles(int inVal) {
		switch (inVal) {
		default: return inVal;
		//case 9 : return 36; 
		case 18: return 9;
		case 19: return 37;
		case 37: return 19;
		case 21:  return 42;
		case 54: return 27;
	
		}
	}


	public static int mainCollision(int inVal) {
		if (inVal == 0 || inVal == 2|| inVal == 4 || inVal == 8 || inVal == 16 || inVal == 32 ) {
			return inVal;
			
		}
		else {
			inVal = FDCell.getCollidedParticles(inVal);
			return inVal;
		}
		//return inVal;
}
		
	
}
