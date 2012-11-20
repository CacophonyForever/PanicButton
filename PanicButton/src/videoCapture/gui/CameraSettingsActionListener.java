package videoCapture.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CameraSettingsActionListener implements ActionListener, ListSelectionListener{
	
	CapturerController myController;
	
	public CameraSettingsActionListener(CapturerController controller)
	{
		myController=controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Use This Camera"))
			{
				selectedCameraUpdate();
			}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		System.out.println(e);
		if (e.getValueIsAdjusting() == false)
		{
			myController.updateCheckboxStatus();
		myController.previewSelectedCamera();
		}
	}

	private void selectedCameraUpdate()
	{
		myController.setSelectedCameraCheckd();
	}

}
