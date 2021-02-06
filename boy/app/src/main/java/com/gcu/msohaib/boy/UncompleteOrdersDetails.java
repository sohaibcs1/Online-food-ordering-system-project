package com.gcu.msohaib.boy;

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

public class UncompleteOrdersDetails extends AppCompatActivity {

    TextView name,nic,orderid,ph,dateAndtime,address,NoOfDeal_Order,dealNumber,area;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String val1id;
    String lat;
    String lng;
    Button sendToMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncomplete_orders_details);
        initiliz();

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            val1id = extras.getString("Val1");

        }


            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://customer-ea16c.firebaseio.com/customer");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot=dataSnapshot.child(val1id);
                if(dataSnapshot.exists()) {

                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                    // Log.d("Tag",userProfile.getUserName());

                    name.setText(userProfile.getUserName());
                    nic.setText(userProfile.getUserNic());
                    orderid.setText(userProfile.getorder());
                    ph.setText(userProfile.getUserPhone());
                    dateAndtime.setText(userProfile.getdate());
                    address.setText(userProfile.getUserAdress());
                    NoOfDeal_Order.setText(userProfile.getbill());
                    dealNumber.setText(userProfile.getdnumber());
                    area.setText(userProfile.getarea());
                    lat = dataSnapshot.child("LatLong").child("latitude").getValue().toString();
                    lng = dataSnapshot.child("LatLong").child("longitude").getValue().toString();
                }
                sendToMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),CompleteOrder.class);
                        intent.putExtra("Val1", lat);
                        intent.putExtra("Val2", lng);
                        intent.putExtra("Val3", val1id);
                       startActivity(intent);


                    }
                });


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UncompleteOrdersDetails.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),UncompleteOrder.class);
        startActivity(intent);
        finish();
    }

public void initiliz(){
    name=(TextView) findViewById(R.id.name);
    nic=(TextView) findViewById(R.id.nic);
    orderid=(TextView)findViewById(R.id.norder);
    ph=(TextView) findViewById(R.id.phone);
    dateAndtime=(TextView) findViewById(R.id.date);
    address=(TextView) findViewById(R.id.address);
    NoOfDeal_Order=(TextView) findViewById(R.id.bill);
    sendToMap=(Button) findViewById(R.id.button7);
    dealNumber=(TextView) findViewById(R.id.textView24);
    area=(TextView) findViewById(R.id.textView26);

}
    private void deleteArtist(){
        final DatabaseReference drArtist= FirebaseDatabase.getInstance().getReferenceFromUrl("https://customer-ea16c.firebaseio.com/customer");



        drArtist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot Snapshot) {

                drArtist.getRef().child(val1id).removeValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }

        });





    }
}
