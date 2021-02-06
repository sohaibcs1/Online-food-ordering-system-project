package com.gcu.msohaib.managerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class completeOrder extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ListView listView;
    ArrayList<String> list=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        listView=(ListView) findViewById(R.id.lt1);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://customer-ea16c.firebaseio.com/completeOrder");
       //Query q=databaseReference.orderByChild("count").orderByKey();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

if(snapshot.exists()) {
    UserProfile2 userP = snapshot.getValue(UserProfile2.class);
    // list.add(userP.);
    list.add(snapshot.child("Order id").getValue().toString());

    // Log.d("Tag",userProfile.getcid());
}
                }
                adapter= new ArrayAdapter<String>(completeOrder.this,android.R.layout.simple_list_item_1,list);
                listView.setAdapter(adapter);

                //on click on listview
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Get the selected item text from ListView
                        String selectedItem = (String) parent.getItemAtPosition(position);

                        // Display the selected item text on TextView
                        Toast toast= Toast.makeText(completeOrder.this,"Order_id: "+selectedItem+" Selected" , Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(getApplicationContext(),completeOrdersDetails.class);
                        intent.putExtra("Val1", selectedItem);
                        startActivity(intent);

                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(completeOrder.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        startActivity(intent);
        finish();
    }



}
