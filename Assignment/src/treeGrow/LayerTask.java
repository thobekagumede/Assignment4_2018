package treeGrow;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class LayerTask extends RecursiveAction{
	static final int SEQUENTIAL_CUTOFF=10;
	Land land;
	List<Tree> treeArray;
	int dimSize; 
	int lo; // arguments
	int hi;
	
	public LayerTask(Land land, List<Tree> treeArray, int dimSize) {
		super();
		this.land = land;
		this.treeArray = treeArray;
		this.dimSize = dimSize;
		lo = 0;
		hi = treeArray.size();
	}
	
	public LayerTask(Land land, List<Tree> treeArray, int dimSize, int lo, int hi) {
		this(land, treeArray, dimSize);
		this.lo = lo;
		this.hi = hi;
	}

	@Override
	protected void compute() {
		if((hi - lo)< SEQUENTIAL_CUTOFF){
			   for(int i= lo;i<hi;i++){
				   treeArray.get(i).simulateOnce(land);
				   return;
			   }
		   }else {
			 int mid = (lo + hi)/2;
			 LayerTask left = new LayerTask(land, treeArray, dimSize, lo, mid); 
			 LayerTask right = new LayerTask(land, treeArray, dimSize, mid, hi);
			 left.fork();
			 right.fork();
			 left.join();
			 right.join();
		   }
	}
}
