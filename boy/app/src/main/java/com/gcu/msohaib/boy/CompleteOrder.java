package com.gcu.msohaib.boy;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompleteOrder extends AppCompatActivity {
    TextView id,name,time,boy;
    Spinner spinner;
    String date;
    Button b1;
    EditText distance;

    ArrayAdapter<CharSequence> adapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    String   idg ;
    String  nameg;
    String  boyg;
    String  spinnerg ;

    String cuPhone;
    String cuAddress;
    ArrayList<String> list=new ArrayList<>();
    String preCuid;
    String bill;
    String lat,lng;
    Button b2;
    String distanceg;
    DatabaseReference databaseReference;
    String fu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        initiliz();
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            lat=extras.getString("Val1");
            lng=extras.getString("Val2");
            preCuid=extras.getString("Val3");


        }

// map view code
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+31.5732+","+74.3079+"&daddr="+lat+","+lng;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        } );

        List<String> categories = new ArrayList<String>();
        // String mystring = getResources().getString(R.string.select);




        categories.add("Not Deliver");
        categories.add("Order Deliver");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parrent, View view, int position, long id) {
                String item = parrent.getItemAtPosition(position).toString();
              //  Toast.makeText(parrent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       // read from server customer data
        // double ddist=Double.parseDouble(dist);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://customer-ea16c.firebaseio.com//customer");
        final DatabaseReference mref = firebaseDatabase.getReferenceFromUrl("https://customer-ea16c.firebaseio.com//completeOrder");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot=dataSnapshot.child(preCuid);
                if(dataSnapshot.exists()) {

                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                    // Log.d("Tag",userProfile.getUserName());

                    name.setText(userProfile.getUserName());
                    id.setText(preCuid);
                    boy.setText(user.getUid());
                    cuPhone = userProfile.getUserPhone();
                    cuAddress = userProfile.getUserAdress();
                    bill = userProfile.getbill();

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CompleteOrder.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
//send to manager button
            b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NotEmptyField()) {

                    double kmdist=Double.parseDouble(distance.getText().toString().trim());
                    double f = kmdist / 60;  // divided by km per litre
                    f = roundTwoDecimals(f);
                    fu = Double.toString(f);
                    //dialog box apppear
                    AlertDialog.Builder builder = new AlertDialog.Builder(CompleteOrder.this);

                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to Send to Manager?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            date();
                            String id = idg;
                            mref.child(id).child("Order id").setValue(idg);
                            mref.child(id).child("CustomerName").setValue(nameg);
                            mref.child(id).child("deliveryBoyId").setValue(boyg);
                            mref.child(id).child("OrderStatus").setValue(spinnerg);
                            mref.child(id).child("date").setValue(date);
                            mref.child(id).child("customerPhone").setValue(cuPhone);
                            mref.child(id).child("customerAddress").setValue(cuAddress);
                            mref.child(id).child("bill").setValue(bill);
                            mref.child(id).child("fuel").setValue(fu);
                            mref.child(id).child("distance").setValue(distance.getText().toString().trim());
                            mref.child(id).child("lat").setValue(lat);
                            mref.child(id).child("lng").setValue(lng);
                            Log.e("err","Message"+preCuid);

                            Toast.makeText(CompleteOrder.this, " Successfully Send to Manager!", Toast.LENGTH_SHORT).show();

                            //databaseReference.child(preCuid).removeValue();
                            deleteArtist();

                            dialog.dismiss();
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

            }
            });



    }
    public void initiliz(){
        id=(TextView) findViewById(R.id.textView13);
        name=(TextView) findViewById(R.id.textView11);
        boy=(TextView) findViewById(R.id.textView21);
        spinner=(Spinner) findViewById(R.id.spinner);
        b1=(Button) findViewById(R.id.button);
        b2=(Button) findViewById(R.id.button2);
        distance=(EditText) findViewById(R.id.editText);

    }

      private Boolean NotEmptyField(){
        Boolean result = false;

        idg = id.getText().toString();
        nameg = name.getText().toString();
        boyg = boy.getText().toString();
        spinnerg = spinner.getSelectedItem().toString();
        distanceg= distance.getText().toString().trim();


        if(idg.isEmpty() || nameg.isEmpty() || boyg.isEmpty() || distanceg.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else if(spinner.getSelectedItem()=="Not Deliver"){
                Toast.makeText(this, "Please Select Status__'Order Deliver' ", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }

        return result;
    }

    public void date(){
    //get date and time
    java.text.DateFormat df = new java.text.SimpleDateFormat("EEE, d MMM yyyy, hh:mm:ss");
     date = df.format(java.util.Calendar.getInstance().getTime());
}


    private void deleteArtist(){
        final DatabaseReference drArtist= FirebaseDatabase.getInstance().getReferenceFromUrl("https://customer-ea16c.firebaseio.com/customer");



        drArtist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot Snapshot) {

                drArtist.getRef().child(preCuid).removeValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }

        });



    }

    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.####");
        return Double.valueOf(twoDForm.format(d));
    }
}
