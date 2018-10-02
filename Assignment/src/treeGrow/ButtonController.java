package treeGrow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonController implements ActionListener{
	String action;
	
	public ButtonController(String action) {
		this.action = action;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (action) {
		case "reset":
			System.out.println("reset");
			break;
		case "pause":
			System.out.println("pause");
			break;
		case "play":
			System.out.println("play");
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
