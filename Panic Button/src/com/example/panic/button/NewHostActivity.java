package com.example.panic.button;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewHostActivity extends Activity{

	 public void onCreate(Bundle icicle) {
		 this.setContentView(R.layout.manualadd);
		 super.onCreate(icicle);
		 
		   final Button okButton = (Button) findViewById(R.id.button1);
		   okButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	               Ok();
	             }
	         });
	         
		   final Button cacelButton = (Button) findViewById(R.id.button2);
		   cacelButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	               Cancel();
	             }
	         });


	 }
	 
	 public void Ok()
	 {
		 Intent returnIntent = new Intent();
		 EditText et = (EditText)findViewById(R.id.editText2);
		 String host = et.getText().toString();
		 
		 EditText et2 = (EditText)findViewById(R.id.editText1);
		 String port = et2.getText().toString();
		 
		 
		 returnIntent.putExtra("hostname",host);
		 returnIntent.putExtra("port",port);
		 setResult(RESULT_OK,returnIntent);     
		 finish();
	 }
	 
	 public void Cancel()
	 {
		 this.setResult(RESULT_CANCELED);
		 finish();
	 }
	

}
