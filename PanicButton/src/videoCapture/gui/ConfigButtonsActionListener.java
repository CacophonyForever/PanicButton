package videoCapture.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigButtonsActionListener implements ActionListener {

	private CapturerController myController;
	
	public ConfigButtonsActionListener(CapturerController cont)
	{
		myController = cont; 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel"))
		{
			cancelButtonAction();
		}
		
		if (e.getActionCommand().equals("OK"))
		{
			OkButtonAction();
		}
	}
	
	public void cancelButtonAction()
	{
		myController.cancelConfigWindow();
	}
	
	public void OkButtonAction()
	{
		myController.okConfigWindow();
	}


}
