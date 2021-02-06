package com.gcu.msohaib.managerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddBoy extends AppCompatActivity {

    Button b2,b3;
    private int PLACE_PICKER_REQUEST = 1;
    TextView tv1,numberOfOrder,salary;
    EditText name,phone,city,nic,email;
    String date;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    Spinner spinner;
    String  spinnerg ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_boy);
        b2=(Button) findViewById(R.id.button2);
        b3=(Button) findViewById(R.id.button3);
        tv1=(TextView) findViewById(R.id.textView7);
        name=(EditText) findViewById(R.id.editText);
        phone=(EditText) findViewById(R.id.editText5);
        nic=(EditText) findViewById(R.id.editText4);
        email=(EditText) findViewById(R.id.number);
        salary=(EditText) findViewById(R.id.editText2);
        spinner=(Spinner) findViewById(R.id.spinner);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mref = firebaseDatabase.getReferenceFromUrl("https://customer-ea16c.firebaseio.com/boy");

//add iem in spinner item
        List<String> categories = new ArrayList<String>();

        categories.add("LAHORE CANTONMENT");
        categories.add("MODEL TOWN LAHORE");
        categories.add("BAHRIA TOWN LAHORE");
        categories.add("GARDEN TOWN, LAHORE");
        categories.add("DHA LAHORE");
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


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    spinnerg = spinner.getSelectedItem().toString();
                    //dialog box apppear
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddBoy.this);

                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to place order?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            date();
                            String id = mref.push().getKey();
                            mref.child(id).child("Boy_id").setValue(id);
                            mref.child(id).child("Name").setValue(name.getText().toString().trim());
                            mref.child(id).child("Phone").setValue(phone.getText().toString().trim());
                            mref.child(id).child("NIC").setValue(nic.getText().toString().trim());
                            mref.child(id).child("Adress").setValue((tv1.getText().toString().trim()));
                            mref.child(id).child("Email").setValue(email.getText().toString().trim());
                            mref.child(id).child("date").setValue(date);
                            mref.child(id).child("area").setValue(spinnerg);
                            mref.child(id).child("salary").setValue(salary.getText().toString().trim());
                            Toast.makeText(AddBoy.this, " boy added Successfully!", Toast.LENGTH_SHORT).show();

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
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        startActivity(intent);
        finish();
    }


    private Boolean validate(){
        Boolean result = false;

        String n = name.getText().toString();
        String p = phone.getText().toString();
        String ni =nic.getText().toString();
        String tv =tv1.getText().toString();
        String em=email.getText().toString();
        String sa=salary.getText().toString();

        if(n.isEmpty() || p.isEmpty() || ni.isEmpty() || tv.isEmpty() || em.isEmpty() ||sa.isEmpty() ){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else if(p.length()<11 ){
            Toast.makeText(this, "Phone Formate is 03074538341", Toast.LENGTH_SHORT).show();
        }else if(ni.length()<13 ){
            Toast.makeText(this, "NIC Formate is 3410124434371", Toast.LENGTH_SHORT).show();
        }
        else if(tv.isEmpty() ){
            Toast.makeText(this, "Please Selecet location from button", Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(em).matches()){
            Toast.makeText(this, "Please Enter Correct email", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }

        return result;
    }

    public void date(){
        //get date and time
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm:ss");
        date = df.format(Calendar.getInstance().getTime());
    }

    public void goPlacePicker(View view){
        Toast toast= Toast.makeText(this, "Map Loading.......", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        try{
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(AddBoy.this), PLACE_PICKER_REQUEST);

        }

        catch(GooglePlayServicesRepairableException e){
            e.printStackTrace();
        }
        catch (GooglePlayServicesNotAvailableException e){
            e.printStackTrace();
        }

    }
    @Override
    protected void onActivityResult(int requestCode,int resulrCode,Intent data){

        if(requestCode==PLACE_PICKER_REQUEST){
            if(resulrCode==RESULT_OK){
                Place place= PlacePicker.getPlace(data,this);
                tv1.setText(place.getAddress());
            }
        }
    }

}
