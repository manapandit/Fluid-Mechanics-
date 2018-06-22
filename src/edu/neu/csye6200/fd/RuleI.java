
package edu.neu.csye6200.fd;

/**
 * A Rule defines how to generate a new FluidFrame from an existing one
 *
 */
public interface RuleI {

	public FluidFrame createNextFrame(FluidFrame inFrame);
	public int createNextCell(int inVal);
	public int createNext(int var);
	public int createNext1(int x, int y, int var);
	public FluidFrame createNextFrame1(FluidFrame inFrame);
	
}
