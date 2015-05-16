package com.tifinnearme.priteshpatel.tifinnearme.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tifinnearme.priteshpatel.tifinnearme.MainActivity;
import com.tifinnearme.priteshpatel.tifinnearme.R;

/**
 * Created by pritesh.patel on 02-04-15.
 */
public class Customer_reg extends ActionBarActivity {
    EditText username,password,email,address,mobile;
    Button signup,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Customer Registration:");
       // setContentView(R.layout.customer_reg);
        ScrollView scroll_view=new ScrollView(this);
        scroll_view.setBackgroundColor(Color.parseColor("#FFCC99"));

        RelativeLayout rl=new RelativeLayout(this);
        rl.setBackgroundColor(Color.parseColor("#FFCC99"));
        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                return true;
            }
        });

        //Edittext initialization
        username=new EditText(this);
        username.setHint("Username");
        username.setId(1);
        username.setInputType(InputType.TYPE_CLASS_TEXT);
        username.setImeOptions(EditorInfo.IME_ACTION_NEXT);//To show next button on keypad

        password=new EditText(this);
        password.setHint("Password");
        password.setId(2);
        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        password.setImeOptions(EditorInfo.IME_ACTION_NEXT);//To show next button on keypad

        email=new EditText(this);
        email.setHint("Email");
        email.setId(3);
        email.setInputType(InputType.TYPE_CLASS_TEXT);
        email.setImeOptions(EditorInfo.IME_ACTION_NEXT);//To show next button on keypad


        address=new EditText(this);
        address.setHint("Address");
        address.setId(4);
        address.setInputType(InputType.TYPE_CLASS_TEXT);
        address.setImeOptions(EditorInfo.IME_ACTION_NEXT);//To show next button on keypad

        mobile=new EditText(this);
        mobile.setHint("Mobile Number");
        mobile.setId(5);
        mobile.setRawInputType(Configuration.KEYBOARD_12KEY);
        mobile.setImeOptions(EditorInfo.IME_ACTION_SEND);//To show next button on keypad
        mobile.setImeActionLabel("Sign Up",EditorInfo.IME_ACTION_SEND);
        mobile.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onSignUp(v);
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mobile.getWindowToken(),0);
                return true;
            }
        });


        //Button initialization
        signup=new Button(this);
        signup.setText("Sign Up");
        signup.setBackgroundColor(Color.parseColor("#A5ABAB"));
        signup.setId(6);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mobile.getWindowToken(),0);
                onSignUp(v);
            }
        });

       /* back=new Button(this);
        back.setBackgroundColor(Color.parseColor("#A5ABAB"));
        back.setText("< Back");
        back.setId(7);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClicked(v);
            }
        });*/

        //Setting positions of sign up buttons

        RelativeLayout.LayoutParams signup_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        signup_params.addRule(RelativeLayout.BELOW,mobile.getId());
        signup_params.setMargins(50,30,0,0);


        //back button

        /*RelativeLayout.LayoutParams back_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        back_params.addRule(RelativeLayout.RIGHT_OF,signup.getId());
        back_params.addRule(RelativeLayout.BELOW, mobile.getId());
        back_params.setMargins(50,30,0,0);*/

        //mobile text position
        RelativeLayout.LayoutParams mobile_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        mobile_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mobile_params.addRule(RelativeLayout.BELOW, address.getId());
        mobile_params.setMargins(0,30,0,0);
        //address
        RelativeLayout.LayoutParams address_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        address_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        address_params.addRule(RelativeLayout.BELOW,email.getId());
        address_params.setMargins(0,30,0,0);
        //email
        RelativeLayout.LayoutParams email_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        email_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        email_params.addRule(RelativeLayout.BELOW,password.getId());
        email_params.setMargins(0,30,0,0);
        //password
        RelativeLayout.LayoutParams password_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        password_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        password_params.addRule(RelativeLayout.BELOW,username.getId());
        password_params.setMargins(0,30,0,0);
        //username
        RelativeLayout.LayoutParams username_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        username_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        username_params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        username_params.setMargins(0, 50, 0, 0);
        //getting width

        Resources rs=getResources();
        int pixels=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, rs.getDisplayMetrics());

        //setting width
        username.setWidth(pixels);
        password.setWidth(pixels);
        email.setWidth(pixels);
        address.setWidth(pixels);
        mobile.setWidth(pixels);

        //adding all elements to relativelayout
        rl.addView(username,username_params);
        rl.addView(password,password_params);
        rl.addView(email,email_params);
        rl.addView(address,address_params);
        rl.addView(mobile,mobile_params);
        rl.addView(signup,signup_params);
        //rl.addView(back,back_params);

        scroll_view.addView(rl);
        setContentView(scroll_view);

    }

   /* public void onBackClicked(View view){
        Intent i=new Intent(this,SignUp_page.class);
        startActivity(i);
    }*/
    public void onSignUp(View view){
        new LoadinBackGround().execute();
    }

    public class LoadinBackGround extends AsyncTask<Void, Void, Void>{
        ProgressDialog dialog;
        static final String p="MyLog";
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog=new ProgressDialog(Customer_reg.this);
            dialog.setMessage("Loading map...");
            dialog.setTitle("Getting locations");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();

            Log.i(p,"onPreExecute");
        }


        @Override
        protected Void doInBackground(Void... params) {

            Intent i=new Intent(Customer_reg.this,MainActivity.class);

            try {
                Thread.sleep(2000);
                dialog.dismiss();
                 startActivity(i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i(p,"doInBackground after 2 secs");
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(p,"onPostExecute");
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.i(p,"onProgressUpdate");
            dialog.dismiss();
        }
    }
}
