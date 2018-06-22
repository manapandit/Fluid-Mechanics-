/**
 * 
 */
package edu.neu.csye6200.fd;

/**
 * @author 
 *
 */
public class FDRule extends RuleA {

	/**
	 * 
	 */
	public FDRule() {
	}


	/* This is a steady state rule - nothing changes
	 * (non-Javadoc)
	 * @see edu.neu.csye6200.fluid.RuleI#createNextCell(int)
	 */
	@Override
	public int createNextCell(int value) {
		
		int outVal = 0;
		
		for (int dir = 0; dir < 6; dir++) {
			if (FDCell.hasDirectionFlag(value, FDCell.getOppositeDirection(dir)))
				outVal = FDCell.setFlag(outVal, dir); // Just pass through in the opposite direction from where it came
		
		}
		
		return outVal;
	}
	
	
	public int createNextCell1(int value) {
		
		int outVal = 0;
		
		for (int dir = 0; dir < 6; dir++) {
			if (FDCell.hasDirectionFlag(value, FDCell.getCollidedParticles(dir)))
				outVal = FDCell.setFlag(outVal, dir); // Just pass through in the opposite direction from where it came
				
		}
		
		return outVal;
	}
	
	
	public int mainCollision(int inVal) {		//Conditions to check whether the collisions is taking place 
		if (inVal == 0 || inVal == 2|| inVal == 4 || inVal == 8 || inVal == 16 || inVal == 32 ) {		//condition when no collision is taking place
			return inVal;
			
		}
		else {
			inVal = FDCell.getCollidedParticles(inVal);
			//return inVal;
		}
		return inVal;
		
	}
	
}