package com.gcu.msohaib.managerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    Button Uncomplete,AddBoy,viewBoy,ext,complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firebaseAuth = FirebaseAuth.getInstance();
        Uncomplete=(Button) findViewById(R.id.button2);
        AddBoy=(Button) findViewById(R.id.addBoy);
        viewBoy=(Button)findViewById(R.id.viewboy);
        ext=(Button) findViewById(R.id.button6);
        complete=(Button) findViewById(R.id.button4);
        complete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
                startActivity(new Intent(SecondActivity.this,completeOrder.class));
            }
        });

        Uncomplete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
                startActivity(new Intent(SecondActivity.this,UncompleteOrder.class));
            }
        });
        AddBoy.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
                startActivity(new Intent(SecondActivity.this,AddBoy.class));
            }
        });
        viewBoy.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
                startActivity(new Intent(SecondActivity.this,ViewBoys.class));
            }
        });

        ext.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();

            }
        });

    }


    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
