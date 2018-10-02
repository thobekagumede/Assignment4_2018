package treeGrow;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class ForestSimulation implements Runnable{
	SunData sundata;
	
	public ForestSimulation(SunData sundata)
	{
		this.sundata = sundata;
	}
	@Override
	public void run() {
		int numberOfTrees = sundata.trees.length;
		List<Tree> forest = Arrays.asList(sundata.trees);
		Collections.sort((List) forest);
		Collections.reverse(forest);
		int year = 0;
		while(true)
		{
			//System.out.println(year);
			float minh = 18.0f;
			float maxh = 20.0f;
			int treeIndex = 0;
			// for each layer e.g. (18,20]; (16,18]...
			for(int layer = 0; layer <= 10; layer++) {
				int layerStartIndex = treeIndex;
				for (; treeIndex < numberOfTrees; treeIndex++)
				{
					Tree thisTree = forest.get(treeIndex);
					// Check if new layer is starting
					if(!isBetween(maxh, minh, thisTree.getExt()))
					{
						if(treeIndex - layerStartIndex == 0)
							break;
						treeIndex--;
						int layerEndIndex = treeIndex;
						ForkJoinPool pool = new ForkJoinPool(2);
						LayerTask layerTask = new LayerTask(sundata.sunmap, forest, sundata.sunmap.getDimX());
						pool.invoke(layerTask);
						break;
					}
					//thisTree.simulateOnce(sundata.sunmap);
				}
				// extent values for next layer
				maxh = minh;
				minh -= 2.0f; // next band of trees
			}
			try {
				Thread.sleep(20);
				year++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
			sundata.sunmap.resetShade();
		}
	}
	
	boolean isBetween(float max, float min, float value)
	{
		return value < max && value >= min;
	}

}
