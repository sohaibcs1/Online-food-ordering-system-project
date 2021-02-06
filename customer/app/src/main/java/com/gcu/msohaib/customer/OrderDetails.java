package com.gcu.msohaib.customer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.LoginFilter;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Thread.sleep;

public class OrderDetails extends AppCompatActivity {
    Button b2,b3;
    private int PLACE_PICKER_REQUEST = 1;
    TextView tv1,numberOfOrder;
    EditText name,phone,nic,nuOfOrder;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    LatLng latlng;

    int totalBill;
    String date;
    int nOrder;
    String price;
    String deal;
    Spinner sp ;
    String  spinnerg ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        b2=(Button) findViewById(R.id.button2);
        b3=(Button) findViewById(R.id.button3);
        tv1=(TextView) findViewById(R.id.textView7);
        name=(EditText) findViewById(R.id.editText);
        phone=(EditText) findViewById(R.id.editText5);
        nic=(EditText) findViewById(R.id.editText4);
        sp=(Spinner) findViewById(R.id.spinner);
        numberOfOrder=(EditText) findViewById(R.id.number);

        if (getIntent().getExtras() != null) {
            deal= getIntent().getStringExtra("val1");
        }

        //add item in spinner
        List<String> categories = new ArrayList<String>();

        categories.add("LAHORE CANTONMENT");
        categories.add("MODEL TOWN LAHORE");
        categories.add("BAHRIA TOWN LAHORE");
        categories.add("GARDEN TOWN, LAHORE");
        categories.add("DHA LAHORE");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(OrderDetails.this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parrent, View view, int position, long id) {
                String item = parrent.getItemAtPosition(position).toString();
              //  Toast.makeText(parrent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mref = firebaseDatabase.getReferenceFromUrl("https://customer-ea16c.firebaseio.com/customer");


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {
                    //dialog box apppear
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetails.this);

                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to place order?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                             spinnerg = sp.getSelectedItem().toString();
                                date();
                                String id = mref.push().getKey();
                                mref.child(id).child("Customer_id").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                mref.child(id).child("Order_id").setValue(id);
                                mref.child(id).child("Name").setValue(name.getText().toString().trim());
                                mref.child(id).child("Phone").setValue(phone.getText().toString().trim());
                                mref.child(id).child("NIC").setValue(nic.getText().toString().trim());
                                mref.child(id).child("Adress").setValue((tv1.getText().toString().trim()));
                                mref.child(id).child("NoOfDeal_Order").setValue(numberOfOrder.getText().toString().trim());
                                mref.child(id).child("date").setValue(date);
                                mref.child(id).child("bill").setValue(totalBill());
                                mref.child(id).child("Status").setValue("Panding");
                                mref.child(id).child("LatLong").setValue(latlng);
                                mref.child(id).child("deal_number").setValue(deal);
                                mref.child(id).child("area").setValue(spinnerg);

                                Toast.makeText(OrderDetails.this, " Order Place Successfully!", Toast.LENGTH_SHORT).show();



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
    public String totalBill(){
      nOrder= Integer.parseInt(numberOfOrder.getText().toString().trim());
        totalBill=nOrder*1400;
        String tbill=Integer.toString(totalBill);
        return  tbill;
    }
    private Boolean validate(){
        Boolean result = false;

        String n = name.getText().toString();
        String p = phone.getText().toString();
        String ni =nic.getText().toString();
        String tv =tv1.getText().toString();


        if(n.isEmpty() || p.isEmpty()  || ni.isEmpty() ){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else if(phone.length()<11 ){
            Toast.makeText(this, "Phone Formate is 03074538341", Toast.LENGTH_SHORT).show();
        }
        else if(nic.length()<13 ){
            Toast.makeText(this, "NIC Formate is 3410124434371", Toast.LENGTH_SHORT).show();
        }
        else if(tv.isEmpty() ){
            Toast.makeText(this, "Please Selecet location from button", Toast.LENGTH_SHORT).show();
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
                startActivityForResult(builder.build(OrderDetails.this), PLACE_PICKER_REQUEST);

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
                tv1.setText( place.getAddress());
                latlng=place.getLatLng();
            }
        }
    }



}
