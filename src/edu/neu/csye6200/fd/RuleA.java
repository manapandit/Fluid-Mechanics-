/**
 * 
 */
package edu.neu.csye6200.fd;

import java.util.HashMap;

/**
 * @author manali
 *
 */
public class RuleA implements RuleI {

	/**
	 * 
	 */
	public RuleA() {
	}
	
	/* (non-Javadoc)
	 * @see edu.neu.csye6200.fluid.RuleI#createNextFrame(edu.neu.csye6200.fluid.FluidFrame)
	 */
	@Override
	public FluidFrame createNextFrame(FluidFrame inFrame) {
		FluidFrame nxtFrame = new FluidFrame(inFrame.getSize());
		int nextOutCelVal;
		for (int x = 0; x < inFrame.getSize(); x++) {
			for (int y = 0; y < inFrame.getSize(); y++) {
				int inboundVal = inFrame.getCellInValue(x, y); // Read all neighbors and create opposite inbound values from their outbound ones
				int var = FDCell.mainCollision(inboundVal);
				nextOutCelVal = createNextCell(var);
				nxtFrame.setCellOutValue(x, y, nextOutCelVal);
			}
		}
		return nxtFrame;
	}
	
	@Override
	public FluidFrame createNextFrame1(FluidFrame inFrame) {
	
		FluidFrame nxtFrame = new FluidFrame(inFrame.getSize());
		int nextOutCelVal;
		for (int x = 0; x < inFrame.getSize(); x++) {
			for (int y = 0; y < inFrame.getSize(); y++) {
				int inboundVal = inFrame.getCellInValue(x, y); // Read all neighbors and create opposite inbound values from their outbound ones
				//int var = createNext(x,y,inboundVal);
				nextOutCelVal = createNextCell(inboundVal);
				nxtFrame.setCellOutValue(x, y, nextOutCelVal);
			}
		}
		return nxtFrame;
	}


	/* A pass-through rule, no collisions
	 * (non-Javadoc)
	 * @see edu.neu.csye6200.fluid.RuleI#createNextCell(int)
	 */
	@Override
	public int createNextCell(int inVal) {
			int outVal = 0;
		
		for (int dir = 0; dir < 6; dir++) {
			if (FDCell.hasDirectionFlag(inVal, dir))
				outVal = FDCell.setFlag(outVal, FDCell.getOppositeDirection(dir)); // Just pass through in the opposite direction from where it came
				
		}
		return inVal;
	}

	@Override
		public int createNext(int var) {
			int outVal = 0;
			
			for (int dir = 0; dir < 6; dir++) {
				if (FDCell.hasDirectionFlag(var, dir))
					outVal = FDCell.setFlag(outVal, FDCell.getCollidedParticles(dir)); // Just pass through in the opposite direction from where it came
					
			}
			//return inVal;
			return var;
		}

	@Override
	public int createNext1(int x, int y, int var) {
		// TODO Auto-generated method stub
		return var;
	}

}
