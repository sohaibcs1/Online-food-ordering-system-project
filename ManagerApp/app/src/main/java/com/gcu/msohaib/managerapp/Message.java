package com.gcu.msohaib.managerapp;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Message extends Activity {

	public String msg ;
	Button bt1;
	ListView listView;
	TextView phone,area;
	ArrayList<String> list=new ArrayList<>();
	private ArrayAdapter<String> adapter;
	private FirebaseAuth firebaseAuth;
	private FirebaseDatabase firebaseDatabase;
	@Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg);
      	bt1 = (Button) findViewById(R.id.sendMessage);
		listView=(ListView) findViewById(R.id.lst);
		phone=(TextView) findViewById(R.id.p);
		area=(TextView) findViewById(R.id.ar) ;

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            msg = extras.getString("Values");

        }


		firebaseAuth = FirebaseAuth.getInstance();
		firebaseDatabase = FirebaseDatabase.getInstance();
		final DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://customer-ea16c.firebaseio.com/boy");
		databaseReference.addValueEventListener(new ValueEventListener() {

			//get id
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				list.clear();
				for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

					if(snapshot.exists()) {
						UserProfile userProfile = snapshot.getValue(UserProfile.class);
						list.add(userProfile.getboy());
					}
					// Log.d("Tag",userProfile.getcid());
				}
				adapter= new ArrayAdapter<String>(Message.this,android.R.layout.simple_list_item_1,list);
				listView.setAdapter(adapter);

				//on click on listview
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// Get the selected item text from ListView
						final String selectedItem = (String) parent.getItemAtPosition(position);

						// Display the selected item text on TextView
						Toast toast= Toast.makeText(Message.this,"Order_id: "+selectedItem+" Selected" , Toast.LENGTH_SHORT);
						toast.show();


						//get details to relavent id(selectedItem

						databaseReference.addValueEventListener(new ValueEventListener() {
							@Override
							public void onDataChange(DataSnapshot dataSnapshot) {

								dataSnapshot=dataSnapshot.child(selectedItem);

								if(dataSnapshot.exists()) {
									UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
									UserProfile2 userProfile2 = dataSnapshot.getValue(UserProfile2.class);

									phone.setText(userProfile.getUserPhone());
									area.setText(userProfile2.getarea());

								}

							}


							@Override
							public void onCancelled(DatabaseError databaseError) {
								Toast.makeText(Message.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
							}
						});




					}
				});



			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Toast.makeText(Message.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
			}
		});

        bt1.setOnClickListener(new OnClickListener(){

       	 public void onClick(View v){

			 sendSMS();
     	   }


        });

    }
	protected void sendSMS() {
		String p=phone.getText().toString().trim();

		Log.i("Send SMS", "");
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);

		smsIntent.setData(Uri.parse("smsto:"));
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.putExtra("address"  , new String (p));
		smsIntent.putExtra("sms_body"  , msg);

		try {
			startActivity(smsIntent);
			finish();
			Log.i("Finished sending SMS...", "");
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(Message.this,
					"SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
		}
	}
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(),UncompleteOrdersDetails.class);
		startActivity(intent);
		finish();
	}
}
