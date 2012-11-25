package videoStorage.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatusWindowListener implements ActionListener{

	StatusWindowController myController;
	
	public StatusWindowListener(StatusWindowController control)
	{
		myController=control;
	}
	public void actionPerformed(ActionEvent e)
	{	
		myController.showConfigWindow();
	}

}
