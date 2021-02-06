package com.gcu.msohaib.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Deal3 extends AppCompatActivity {
Button b1;
int price;
    String d3="deal Number=3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal3);
        b1=(Button)findViewById(R.id.placeOrder);
    price=777;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Deal3.this, OrderDetails.class);
                myIntent.putExtra("val1",d3);
                startActivity(myIntent);
            }
        });
    }
}
