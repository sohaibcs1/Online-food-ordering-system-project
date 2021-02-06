package com.gcu.msohaib.managerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

    TextView name,nic,orderid,ph,dateAndtime,address,NoOfDeal_Order,totalBill;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    static private String val1,msg;
    TextView areat;
    Button delete;

    Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncomplete_orders_details);
        initiliz();

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://customer-ea16c.firebaseio.com/customer");
        final Intent intent = new Intent(getApplicationContext(),Message.class);
        send.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(intent);

            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            val1 = extras.getString("Val1");

        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot=dataSnapshot.child(val1);

                if(dataSnapshot.exists()) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    UserProfile2 userProfile2 = dataSnapshot.getValue(UserProfile2.class);
                    // Log.d("Tag",userProfile.getUserName());

                    String n = userProfile.getUserName();
                    String ni = userProfile.getUserNic();
                    String oid = userProfile.getorder();
                    String pho = userProfile.getUserPhone();
                    String dt = userProfile.getdate();
                    String ad = userProfile.getUserAdress();
                    String bi = userProfile.getnoOforder();
                    String billi = userProfile2.getbill();
                    String area=userProfile2.getarea();
                    totalBill.setText(billi);
                    name.setText(n);
                    nic.setText(ni);
                    orderid.setText(oid);
                    ph.setText(pho);
                    dateAndtime.setText(dt);
                    address.setText(ad);
                    NoOfDeal_Order.setText(bi);
                    areat.setText(area);
                    msg = "Customer Information:::\n" + " Name:" + "" + n + "\n  " + "NIC:" + "" + ni + "\nPhone:" + "" + pho + "\nDate and time:" + "" + dt + "\nAddress:" + "" + ad + "\nBill:" + "" + billi;
                    intent.putExtra("Values", msg);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UncompleteOrdersDetails.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

//delete uncomplete order
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UncompleteOrdersDetails.this);

                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete this Uncomplete order?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        deleteArtist();
                        Toast.makeText(UncompleteOrdersDetails.this, "delete successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),UncompleteOrder.class);
                        startActivity(intent);
                        finish();

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
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
    nic=(TextView) findViewById(R.id.nic);
    orderid=(TextView)findViewById(R.id.norder);
    ph=(TextView) findViewById(R.id.phone);
    dateAndtime=(TextView) findViewById(R.id.date);
    address=(TextView) findViewById(R.id.address);
    NoOfDeal_Order=(TextView) findViewById(R.id.bill);
    send=(Button) findViewById(R.id.button7);
    totalBill=(TextView) findViewById(R.id.textView22);
    areat=(TextView) findViewById(R.id.textView20);
    delete=(Button) findViewById(R.id.button);
}

    private void deleteArtist(){
        final DatabaseReference drArtist= FirebaseDatabase.getInstance().getReferenceFromUrl("https://customer-ea16c.firebaseio.com/customer");



        drArtist.child(val1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot Snapshot) {

                drArtist.getRef().child(val1).removeValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }

        });

    }
}
