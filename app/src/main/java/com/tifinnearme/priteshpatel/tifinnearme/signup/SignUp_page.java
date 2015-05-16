package com.tifinnearme.priteshpatel.tifinnearme.signup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tifinnearme.priteshpatel.tifinnearme.R;

/**
 * Created by pritesh.patel on 03-04-15.
 */
public class SignUp_page extends ActionBarActivity{
    TextView welcome,are_you,tifin,customer;
    ImageView tifin_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout rl=new RelativeLayout(this);
        rl.setGravity(3);
        rl.setBackgroundColor(Color.parseColor("#E6E6E6"));

        welcome=new TextView(this);
        welcome.setTextSize(25);
        welcome.setText("Welcome to TifinvalaNearMe");
        welcome.setId(1);


        RelativeLayout.LayoutParams welcome_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        welcome_params.addRule(RelativeLayout.TEXT_ALIGNMENT_CENTER);
        welcome_params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        welcome_params.setMargins(50,50,0,0);

        tifin_img=new ImageView(this);
        tifin_img.setImageResource(R.drawable.tifin);
        tifin_img.setId(2);


        RelativeLayout.LayoutParams image_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        image_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        image_params.addRule(RelativeLayout.BELOW,welcome.getId());
        image_params.setMargins(0,50,0,0);


        are_you=new TextView(this);
        are_you.setId(3);
        are_you.setText("Are you");
        are_you.setTextSize(25);

        RelativeLayout.LayoutParams areu_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //areu_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        areu_params.addRule(RelativeLayout.BELOW,tifin_img.getId());
        areu_params.setMargins(50,30,0,0);

        tifin=new TextView(this);
        tifin.setText("Tifinvala?");
        tifin.setId(4);
        tifin.setTextColor(Color.BLUE);
        tifin.setTextSize(28);
        tifin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUp_page.this,Tifinvala_reg.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        customer=new TextView(this);
        customer.setText("Customer?");
        customer.setId(5);
        customer.setTextSize(28);
        customer.setTextColor(Color.RED);
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUp_page.this, Customer_reg.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        RelativeLayout.LayoutParams tifintext_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //tifintext_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tifintext_params.addRule(RelativeLayout.BELOW,are_you.getId());
        tifintext_params.setMargins(50,30,0,0);

        RelativeLayout.LayoutParams custtext_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
       // custtext_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        custtext_params.addRule(RelativeLayout.BELOW,are_you.getId());
        custtext_params.addRule(RelativeLayout.RIGHT_OF,tifin.getId());
        custtext_params.setMargins(30,30,0,0);



        rl.addView(welcome,welcome_params);
        rl.addView(tifin_img,image_params);
        rl.addView(are_you,areu_params);
        rl.addView(tifin,tifintext_params);
        rl.addView(customer,custtext_params);


        setContentView(rl);


    }
}
