package videoStorage.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ConfigWindowListener implements ActionListener, ChangeListener{

	private ConfigWindowController myController;
	
	public ConfigWindowListener (ConfigWindowController control)
	{
		myController = control;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK"))
			myController.saveSettings();
		if (e.getActionCommand().equals("Cancel"))
			myController.closeSettings();
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e);
		myController.setSliderText();
	}

}
