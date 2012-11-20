package com.example.panic.button;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


	public class PanicButton extends ListActivity {
		
			VideoTriggerClient myClient;
			List<String> myValues;
			ArrayAdapter<remoteVideoCapturer> adapter ;
		  public void onCreate(Bundle icicle) {
			myClient = new VideoTriggerClient(this);
		    super.onCreate(icicle);
		    setContentView(R.layout.activity_panic_button);
		       final Button manualButton = (Button) findViewById(R.id.button1);
		         manualButton.setOnClickListener(new View.OnClickListener() {
		             public void onClick(View v) {
		               showAddNewHost();
		             }
		         });
		         
		         
		       final Button scanButton = (Button) findViewById(R.id.button2);
		         scanButton.setOnClickListener(new View.OnClickListener() {
		             public void onClick(View v) {
		               scanNetwork();
		             }
		         });
		         
		         
			       final Button triggerButton = (Button) findViewById(R.id.button3);
			       triggerButton.setOnClickListener(new View.OnClickListener() {
			             public void onClick(View v) {
			               trigger();
			             }
			         });



		
		    // Use your own layout
		    adapter = new ArrayAdapter<remoteVideoCapturer>(this,
		        R.layout.rowlayout, R.id.label, myClient.getCapturers());
		    setListAdapter(adapter);
		  }

		  @Override
		  protected void onListItemClick(ListView l, View v, int position, long id) {
		    String item = (String) getListAdapter().getItem(position);
		    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
		    myValues.add("Tank" + System.currentTimeMillis());
		    adapter.notifyDataSetChanged();
		  }
		  
		  private void showAddNewHost()
		  {
			  Intent intent = new Intent("com.example.panic.button.NewHostActivity");   
			  startActivityForResult(intent,1); 
			  
		  }
		  
		  protected void onActivityResult(int requestCode, int resultCode, Intent data) 
		  {

			  System.out.println("HOKA");
			  if (requestCode == 1) {

			       if(resultCode == RESULT_OK){

			        String hostname=data.getStringExtra("hostname");
			        String port=data.getStringExtra("port");			     
			        System.out.println(hostname + ":" + port);
			        myClient.addNewHost(hostname, Integer.parseInt(port));

			  }

			  if (resultCode == RESULT_CANCELED) {

			       //Write your code on no result return 

			  }
			  }
		
		  }
		  
		  private void scanNetwork()
		  {
			  myClient.scanNetwork();
			  adapter.notifyDataSetChanged();
		  }
		  
		  public void updateList()
		  {
			  adapter.notifyDataSetChanged();
		  }
		  
		  public void trigger()
		  {
			  myClient.trigger();
		  }
		  
		  
	}

