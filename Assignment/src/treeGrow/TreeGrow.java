package treeGrow;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TreeGrow {
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static ForestPanel fp;

	// start timer
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	public static void setupGUI(int frameX,int frameY,Tree [] trees) {
		Dimension fsize = new Dimension(800, 800);
		// Frame init and dimensions
    	JFrame frame = new JFrame("Photosynthesis"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setPreferredSize(fsize);
    	frame.setSize(800, 800);
    	
      	JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
      	g.setPreferredSize(fsize);
      	
      	JPanel controls = new JPanel();
      	JButton resetButton = new JButton("Reset");
      	resetButton.addActionListener(new ButtonController("reset"));
      	JButton pauseButton = new JButton("Pause");
      	pauseButton.addActionListener(new ButtonController("pause"));
      	JButton playButton = new JButton("Play");
      	playButton.addActionListener(new ButtonController("play"));
      	JButton endButton = new JButton("End");
      	endButton.addActionListener(new ButtonController("end"));
 
		fp = new ForestPanel(trees);
		fp.setPreferredSize(new Dimension(frameX,frameY));
		JScrollPane scrollFrame = new JScrollPane(fp);
		fp.setAutoscrolls(true);
		scrollFrame.setPreferredSize(fsize);
	    g.add(scrollFrame);
	    
	    controls.add(resetButton);
	    controls.add(pauseButton);
	    controls.add(playButton);
	    controls.add(endButton);
    	
      	frame.setLocationRelativeTo(null);  // Center window on screen.
      	frame.add(g); //add contents to window
        frame.setContentPane(g);     
        frame.add(controls);
        frame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
	}
	
		
	public static void main(String[] args) {
		SunData sundata = new SunData();
		
		// check that number of command line arguments is correct
		if(args.length != 1)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java treeGrow.java intputfilename");
			System.exit(0);
		}
				
		// read in forest and landscape information from file supplied as argument
		sundata.readData(args[0]);
		System.out.println("Data loaded");
		
		frameX = sundata.sunmap.getDimX();
		frameY = sundata.sunmap.getDimY();
		setupGUI(frameX, frameY, sundata.trees);
		
		// create and start simulation loop here as separate thread
		ForestSimulation simulation = new ForestSimulation(sundata);
		Thread fpt = new Thread(simulation);
        fpt.start();
	}
}