package videoCapture.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CapturerActionListener implements ActionListener {
	
	private CapturerController myController;
	
	public CapturerActionListener(CapturerController control)
	{
		myController=control;
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand().equals("Configure"))
		{
			showSetup();
		}
	}
	
	private void showSetup()
	{
		myController.showConfigWindow();
	}
	

}
