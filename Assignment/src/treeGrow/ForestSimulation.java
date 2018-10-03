package treeGrow;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ForestSimulation implements Runnable{
	SunData sundata;
	StringBuffer isPausedString = new StringBuffer("PAUSED");
	public AtomicInteger year = new AtomicInteger(0);
	public static ForkJoinPool pool = new ForkJoinPool(2);
	AtomicBoolean reset = new AtomicBoolean(false);
	public ForestSimulation(SunData sundata)
	{
		this.sundata = sundata;
	}
	@Override
	public void run() {
		List<Tree> forest = Arrays.asList(sundata.trees);
		Collections.sort((List) forest);
		Collections.reverse(forest);
		while(true && !forest.isEmpty())
		{
			if(reset.get())
			{
				year.set(0);
				TreeGrow.yearText.setText(String.valueOf(year));
				sundata.resetTreeExtents();
				reset.set(false);
				pauseSimulation();
			}
			while(isPausedString.toString().equals("PAUSED"));
			{
				if(reset.get())
				{
					year.set(0);
					TreeGrow.yearText.setText(String.valueOf(year));
					sundata.resetTreeExtents();
					reset.set(false);
				}
			}
			System.out.println(year);
			int treeIndex = 0;
			int layerNumber = getStartingLayer(forest);
			// for each layer e.g. (18,20]; (16,18]...
			Layer: for(int layer = layerNumber; layer >= 0; layer--) {
				int layerStartIndex = treeIndex;
				int layerCount = 0;
				Trees: while(treeIndex < forest.size())
				{
					if(isTreeInCurrentLayer(layer, forest.get(treeIndex).getExt()))
				{
						layerCount++;
						treeIndex++;
						continue Trees;
					}
					else
						break Trees;
				}
				// Simulate
				LayerTask layerTask = new LayerTask(sundata.sunmap, forest, sundata.sunmap.getDimX(), layerStartIndex, layerStartIndex+layerCount);
				pool.invoke(layerTask);
				System.out.println("Working on layer: " + layer*2 + " - " + (layer*2+2));
				while(!layerTask.isDone());
				treeIndex++;
			}
			try {
				Thread.sleep(30);
				year.set(year.get()+1);
				TreeGrow.yearText.setText(String.valueOf(year.get()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
			sundata.sunmap.resetShade();
		}
	}
	
	private int getStartingLayer(List<Tree> forest) {
		int firstExtent = (int) forest.get(0).getExt();
		return firstExtent/2;
	}
	boolean isTreeInCurrentLayer(int layer, float value)
	{
		return value < (layer*2.0f +2) && value >= (layer*2.0f);
	}
	
	public synchronized void playSimulation()
	{
		isPausedString.replace(0, isPausedString.length(), "PLAY");
	}
	
	public synchronized void pauseSimulation()
	{
		isPausedString.replace(0, isPausedString.length(), "PAUSED");
	}
	
	public synchronized void resetSimulation()
	{
		reset.set(true);
	}

}
