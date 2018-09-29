package treeGrow;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
			System.out.println(year);
			float minh = 18.0f;
			float maxh = 20.0f;
			int treeIndex = 0;
			for(int layer = 0; layer <= 10; layer++) {
				for (; treeIndex < numberOfTrees; treeIndex++)
				{
					Tree thisTree = forest.get(treeIndex);
					if(!isBetween(maxh, minh, thisTree.getExt()))
						break;
					double average = sundata.sunmap.calcTreeAverage(thisTree);
					System.out.println(average);
					sundata.sunmap.shadow(thisTree);
					thisTree.sungrow(sundata.sunmap);
				}
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
