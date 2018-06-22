package edu.neu.csye6200.fd;

public class FluidFrameAvg extends RuleA{
	private int size;
	private double direction[][];
	private double magnitude[][];

	public FluidFrameAvg(int size) {
		direction = new double[size][size];
		magnitude = new double[size][size];
		this.size = size;
		resetArray();
	}

	
	public int getSize() {
		return size;
	}


	//Walk through the frame obtaining the average direction and magnitude
	public void setFluidFrame(FluidFrame inFrame) {

		int stepSize = inFrame.size / size;
		System.out.println("Step size is: " + stepSize);

		for (int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				inFrame.calcAvgRegion(y*stepSize, x*stepSize, stepSize);
				direction[y][x] = inFrame.getAvgDirection();
				magnitude[y][x] = inFrame.getAvgMagnitude();
			}
		}
	}
	

	public void setDirection(double[][] direction) {
		this.direction = direction;
	}

	public void setMagnitude(double[][] magnitude) {
		this.magnitude = magnitude;
	}

	public double getDirection(int x, int y) {
		return direction[x][y];
		
	}
	
	public double getMagnitude(int x, int y) {
		return magnitude[x][y];
		
	}

		private void resetArray() {
			// TODO Auto-generated method stub
			for(int x = 0; x < size; x++) {
				for(int y = 0; y < size; y++) {
					direction[y][x] = 0.0;
					magnitude[y][x] = 0.0;
				}
			}
			
		}
	}

				