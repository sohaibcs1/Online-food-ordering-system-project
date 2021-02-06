package com.gcu.msohaib.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Deal1 extends AppCompatActivity {
        Button b1;
        String d1="deal Number=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal1);
        b1=(Button)findViewById(R.id.placeOrder);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Deal1.this, OrderDetails.class);
                myIntent.putExtra("val1",d1);
                startActivity(myIntent);
            }
        });
    }
}
