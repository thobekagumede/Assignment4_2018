package treeGrow;

// Trees define a canopy which covers a square area of the landscape
public class Tree implements Comparable<Tree>{
	
private
	int xpos;	// x-coordinate of center of tree canopy
	int ypos;	// y-coorindate of center of tree canopy
	float ext;	// extent of canopy out in vertical and horizontal from center
	
	static float growfactor = 1000.0f; // divide average sun exposure by this amount to get growth in extent
	
public	
	Tree(int x, int y, float e){
		xpos=x; ypos=y; ext=e;
	}
	
	// return the x-position of the tree center
	int getX() {
		return xpos;
	}
	
	// return the y-position of the tree center
	int getY() {
		return ypos;
	}
	
	// return the extent of the tree
	float getExt() {
		return ext;
	}
	
	// set the extent of the tree to <e>
	void setExt(float e) {
		ext = e;
	}

	// return the average sunlight for the cells covered by the tree
	float sunexposure(Land land){
		// to do 
		return 0.0f; // not correct
	}
	
	// is the tree extent within the provided range [minr, maxr)
	boolean inrange(float minr, float maxr) {
		return (ext >= minr && ext < maxr);
	}
	
	// grow a tree according to its sun exposure
	void sungrow(Land land) {
		// newextent = extent + s / 1000.
		setExt(getExt() + growfactor / 1000); 
	}
	
	/*g.fillRect(forest[rt].getY() - (int) forest[rt].getExt(), forest[rt].getX() - (int) forest[rt].getExt(),
						   2*(int) forest[rt].getExt()+1,2*(int) forest[rt].getExt()+1);*/
	
	int getStartX()
	{
		int startX = getX() - (int) Math.ceil(getExt());
		return startX < 0 ? 0 : startX;
	}
	
	int getStartY()
	{
		int startY = getY() - (int) Math.ceil(getExt());
		return startY < 0 ? 0 : startY;
	}
	
	int getEndX(int dim)
	{
		int endX = getX() + (int) Math.ceil(getExt());
		return endX > dim ? dim : endX;
	}
	
	int getEndY(int dim)
	{
		int endY = getY() + (int) Math.ceil(getExt());
		return endY > dim ? dim : endY;
	}
	
	public int compareTo(Tree tree)
	{
	     return((int)this.getExt() - (int)tree.getExt());
	}
}