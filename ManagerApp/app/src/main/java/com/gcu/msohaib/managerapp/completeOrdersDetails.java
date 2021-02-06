package com.gcu.msohaib.managerapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class completeOrdersDetails extends AppCompatActivity {

    TextView name,fuel,orderid,ph,dateAndtime,address,bill,boy_id;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    static private String val1,msg;
    Button send;
    String billi;
    String bi;
    Button map;
    String lat;
    String lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_orders_details);
        initiliz();

        map.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+31.5732+","+74.3079+"&daddr="+lat+","+lng;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
                               });

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://customer-ea16c.firebaseio.com/completeOrder");
 
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            val1 = extras.getString("Val1");

        }


        //total bill from uncomplete order
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot=dataSnapshot.child(val1);
if(dataSnapshot.exists()) {
    UserProfile2 user = dataSnapshot.getValue(UserProfile2.class);

    //Log.d("Tag",userProfile.getUserName());
    //  db.child(val1).child("bill");
    String boy = user.getid();
    String n = user.getcustomer();
    String fu = user.getfuel();
    //   String oid=userProfile.getorder();
    String pho = user.getCustomerPhone();
    String dt = user.getdate();
    String ad = user.getaddress();
    String orderId = dataSnapshot.child("Order id").getValue().toString();
    String bi = user.getbill();
    lat = user.getlat();
    lng = user.getlng();
    name.setText(n);
    fuel.setText(fu);
    //       orderid.setText(oid);
    ph.setText(pho);
    dateAndtime.setText(dt);
    address.setText(ad);
    bill.setText(bi);
    boy_id.setText(boy);
    orderid.setText(orderId);
}
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(completeOrdersDetails.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });










        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot=dataSnapshot.child(val1);

                        UserProfile2 user = dataSnapshot.getValue(UserProfile2.class);

                       //Log.d("Tag",userProfile.getUserName());

                    String boy=user.getid();
                      String n=user.getcustomer();
                    String fu=user.getfuel();
                     //   String oid=userProfile.getorder();
                      String pho=user.getCustomerPhone();
                     String dt=user.getdate();
                    String ad=user.getaddress();
                    String orderId=dataSnapshot.child("Order id").getValue().toString();
                     String bi=user.getbill();

                  name.setText(n);
                  fuel.setText(fu);
             //       orderid.setText(oid);
                    ph.setText(pho);
             dateAndtime.setText(dt);
              address.setText(ad);
              bill.setText(bi);
                boy_id.setText(boy);
                orderid.setText(orderId);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(completeOrdersDetails.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        startActivity(intent);
        finish();
    }

public void initiliz(){
    name=(TextView) findViewById(R.id.name);
    fuel=(TextView) findViewById(R.id.nic);
    orderid=(TextView)findViewById(R.id.norder);
    ph=(TextView) findViewById(R.id.phone);
    dateAndtime=(TextView) findViewById(R.id.date);
    address=(TextView) findViewById(R.id.address);
    bill=(TextView) findViewById(R.id.bill);
    boy_id=(TextView) findViewById(R.id.textView19);
    map=(Button) findViewById(R.id.button9);

}

}
