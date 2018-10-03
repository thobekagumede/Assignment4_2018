package treeGrow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonController implements ActionListener{
	String action;
	ForestSimulation simulation;
	
	public ButtonController(String action) {
		this.action = action;
	}

	public ButtonController(String action, ForestSimulation simulation) {
		this(action);
		this.simulation = simulation;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (action) {
		case "reset":
			simulation.resetSimulation();
			System.out.println("reset");
			break;
		case "pause":
			System.out.println("pause");
			simulation.pauseSimulation();
			break;
		case "play":
			System.out.println("play");
			simulation.playSimulation();
			break;
		case "end":
			System.out.println("end");
			System.exit(0);
			break;

		default:
			break;
		}
		
	}

}
