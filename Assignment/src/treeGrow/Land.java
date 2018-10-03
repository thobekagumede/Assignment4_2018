package treeGrow;

import java.util.Arrays;

public class Land{
	
	int dimX;
	int dimY;
	// sun exposure data here
	float [] sunExposureValues;
	float [] shadeExposureValues;

	static float shadefraction = 0.1f; // only this fraction of light is transmitted by a tree

	Land(int dx, int dy) {
		dimX = dx;
		dimY = dy;
		sunExposureValues = new float[dimX * dimY];
		shadeExposureValues = new float[dimX * dimY];
	}

	// return the number of landscape cells in the x dimension
	int getDimX() {
		return dimX; // incorrect value
	}
	
	// return the number of landscape cells in the y dimension
	int getDimY() {
		return dimY; // incorrect value
	}
	
	// Reset the shaded landscape to the same as the initial sun exposed landscape
	// Needs to be done after each growth pass of the simulator
	void resetShade() {
		shadeExposureValues = Arrays.copyOf(sunExposureValues, sunExposureValues.length);
	}
	
	// return the sun exposure of the initial unshaded landscape at position <x,y?
	float getFull(int x, int y) {
		return sunExposureValues[y*dimX + x]; // incorrect value
	}
	
	// set the sun exposure of the initial unshaded landscape at position <x,y> to <val>
	void setFull(int x, int y, float val) {
		sunExposureValues[y*dimX + x] = val;
	}
	
	// return the current sun exposure of the shaded landscape at position <x,y>
	float getShade(int x, int y) {
		return shadeExposureValues[y*dimX + x]; // incorrect value
	}
	
	// set the sun exposure of the shaded landscape at position <x,y> to <val>
	void setShade(int x, int y, float val){
		shadeExposureValues[y*dimX + x] = val;
	}
	
	// reduce the sun exposure of the shaded landscape to 10% of the original
	// within the extent of <tree>
	void shadow(Tree tree){
		int startX = tree.getStartX();
		int startY = tree.getStartY();
		int endX = tree.getEndX(dimX);
		int endY = tree.getEndY(dimY);
		
		for (int i = startX; i < endX; i++)
		{
			for (int j = startY; j < endY; j++)
			{
				synchronized(this) {
					setShade(i, j, getShade(i, j) * shadefraction);
				}
			}
		}
	}
}