package com.gcu.msohaib.managerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewBoysDetail extends AppCompatActivity {

    TextView nameBoy,nicBoy,Salary,phoneBoy,dateBoy,addressBoy,idBoy;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    static private String val1;
    Button b5;
    String bid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_boys_detail);
        initiliz();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://customer-ea16c.firebaseio.com/boy");

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            val1 = extras.getString("Val2");

        }
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewBoysDetail.this);

                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete this boy?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        deleteArtist();
                        Toast.makeText(ViewBoysDetail.this, "delete successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),ViewBoys.class);
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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot=dataSnapshot.child(val1);

if(dataSnapshot.exists()) {
    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
    UserProfile2 userProfile2 = dataSnapshot.getValue(UserProfile2.class);
    // Log.d("Tag",userProfile.getUserName());
    bid = userProfile.getboy();
    nameBoy.setText(userProfile.getUserName());
    nicBoy.setText(userProfile.getUserNic());
    idBoy.setText(bid);
    phoneBoy.setText(userProfile.getUserPhone());
    dateBoy.setText(userProfile.getdate());
    addressBoy.setText(userProfile.getUserAdress());
    Salary.setText(userProfile2.getsalary());

}

                }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewBoysDetail.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        startActivity(intent);
        finish();
    }
    public void initiliz(){
        nameBoy=(TextView) findViewById(R.id.nameb);
        nicBoy=(TextView) findViewById(R.id.nicb);
        Salary=(TextView)findViewById(R.id.salary);
        phoneBoy=(TextView) findViewById(R.id.phoneb);
        dateBoy=(TextView) findViewById(R.id.dateb);
        addressBoy=(TextView) findViewById(R.id.addressb);
        idBoy=(TextView) findViewById(R.id.idb);
        b5=(Button) findViewById(R.id.button5);
    }

    private void deleteArtist(){
        final DatabaseReference drArtist= FirebaseDatabase.getInstance().getReferenceFromUrl("https://customer-ea16c.firebaseio.com/boy");



        drArtist.child(bid).addListenerForSingleValueEvent(new ValueEventListener() {
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

