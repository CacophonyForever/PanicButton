package videoCapture.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StorageSettingsActionListener implements ActionListener, ListSelectionListener, KeyListener{
	
	CapturerController myController;
	
	public StorageSettingsActionListener(CapturerController controller)
	{
		myController=controller;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println(e);
		if (e.getActionCommand().equals("Add"))
		{
			addNewStorageServer();
		}
		
		if (e.getActionCommand().equals("Delete"))
		{
			deleteStorageServer();
		}
		
		if (e.getActionCommand().equals("Test Server"))
		{
			testServer();
		}
		
	}
	

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false)
		{
		myController.populateFieldsFromSelectedStorage();
		}
	}
	
	public void testServer()
	{
		myController.testEnteredServer();
	}
	
	public void addNewStorageServer()
	{
		myController.addStorage();
	}
	
	public void deleteStorageServer()
	{
		myController.removeServer();
	}
	
	
	public void updateEditedStorage()
	{
		myController.updateEditedStorage();
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("SAVING");
		myController.updateEditedStorage();
		
	}
	@Override
	public void keyTyped(KeyEvent e) {

	}
}
