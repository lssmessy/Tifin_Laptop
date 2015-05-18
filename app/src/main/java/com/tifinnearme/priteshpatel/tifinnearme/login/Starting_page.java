package com.tifinnearme.priteshpatel.tifinnearme.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.tifinnearme.priteshpatel.tifinnearme.MainActivity;
import com.tifinnearme.priteshpatel.tifinnearme.Main_Map;
import com.tifinnearme.priteshpatel.tifinnearme.api_links.API_LINKS;
import com.tifinnearme.priteshpatel.tifinnearme.signup.SignUp_page;
import com.tifinnearme.priteshpatel.tifinnearme.Webcall;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PRITESH on 03-04-2015.
 */
public class Starting_page extends ActionBarActivity{
    EditText username,password;
    Button login,signup,webcall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll_view=new ScrollView(this);
        scroll_view.setBackgroundColor(Color.parseColor("#FFCC99"));
        RelativeLayout rl=new RelativeLayout(this);
        rl.setBackgroundColor(Color.parseColor("#FFCC99"));
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

        //Button initialization

        login=new Button(this);
        login.setBackgroundColor(Color.parseColor("#A5ABAB"));
        login.setText("Login");
        login.setId(3);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validations()) {
                    InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(login.getWindowToken(),0);
                    new Login_background().execute();


                    /*Intent i = new Intent(Starting_page.this, MainActivity.class);
                    startActivity(i);*/
                }
             }
        });

        signup=new Button(this);
        signup.setText("Sign Up");
        signup.setBackgroundColor(Color.parseColor("#A5ABAB"));
        signup.setId(4);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Starting_page.this,SignUp_page.class);
                startActivity(i);
            }
        });

        webcall=new Button(this);
        webcall.setText("Webcall");
        webcall.setId(5);
        webcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Starting_page.this,Webcall.class);
                startActivity(i);
            }
        });

        RelativeLayout.LayoutParams webcall_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //signup_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        webcall_params.addRule(RelativeLayout.RIGHT_OF,signup.getId());
        webcall_params.addRule(RelativeLayout.BELOW, password.getId());
        webcall_params.setMargins(50,30,0,0);


        RelativeLayout.LayoutParams signup_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //signup_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        signup_params.addRule(RelativeLayout.RIGHT_OF,login.getId());
        signup_params.addRule(RelativeLayout.BELOW, password.getId());
        signup_params.setMargins(50,30,0,0);
        //signup_params.setMargins(0,30,0,0)

        RelativeLayout.LayoutParams login_params=new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //login_params.addRule(RelativeLayout.);
        login_params.addRule(RelativeLayout.BELOW, password.getId());
        login_params.setMargins(50,30,0,0);

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
        username_params.setMargins(20, 50, 0, 0);

        Resources rs=getResources();
        int pixels= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,320,rs.getDisplayMetrics());

        username.setWidth(pixels);
        password.setWidth(pixels);

        rl.addView(username,username_params);
        rl.addView(password,password_params);
        rl.addView(login,login_params);
        rl.addView(signup,signup_params);
        rl.addView(webcall,webcall_params);
        scroll_view.addView(rl);
        setContentView(scroll_view);

    }

    private boolean validations() {

        if(username.getText().toString().trim().length() <=0){
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Username is blank");
            ad.setMessage("Username can't be left blank");

            ad.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    username.setText("");
                    username.requestFocus();

                }
            });
            ad.show();

            return false;
        }
        /*else if(username.getText().length()<= 0)
        {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Username is blank");
            ad.setMessage("Username can't be left blank");

            ad.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    username.requestFocus();
                }
            });
            ad.show();

            return false;
        }*/
        else if(password.getText().length() <6) {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Password");
            ad.setMessage("Password length must be greater than 6");

            ad.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    password.requestFocus();
                }
            });
            ad.show();

            return  false;
        }
        else
            return true;

    }


    private class Login_background extends AsyncTask<Void,Void,Void>{
        ProgressDialog dialog;
        static final String p="MyLog";
        boolean res=false;
        String user="";

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog=new ProgressDialog(Starting_page.this);
            dialog.setMessage("Checking credentials...");
            dialog.setTitle("Logging");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
            

            Log.i(p, "onPreExecute");
        }


        @Override
        protected Void doInBackground(Void... params) {

            String uname=username.getText().toString();
            String pwd=password.getText().toString();
            List<NameValuePair> data=new ArrayList<NameValuePair>();

            data.add(new BasicNameValuePair("username",uname));
            data.add(new BasicNameValuePair("password",pwd));


            try{
                HttpClient client=new DefaultHttpClient();
                HttpPost post=new HttpPost(API_LINKS.URL_LINK+API_LINKS.CHECK_USER);
                post.setEntity(new UrlEncodedFormEntity(data));
                HttpResponse response=client.execute(post);

                if(response!=null){
                    InputStream is=response.getEntity().getContent();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb=new StringBuilder();
                    String line=null;
                    while((line=bufferedReader.readLine())!=null){

                        sb.append(line+"\n");
                    }
                    //this.res=sb.toString();
                    JSONObject jsonObject=new JSONObject(sb.toString());
                    jsonObject.get("user_validated");
                    if(jsonObject.get("user_validated")==true){
                        this.res=true;

                    }
                    else if(jsonObject.get("user_validated")==false)
                    {
                        this.res=false;
                    }
                    this.user= jsonObject.getString("username");
                }




            }catch (Exception e){e.getMessage();}


            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(p, "onPostExecute");
            dialog.dismiss();

            AlertDialog.Builder aBuilder=new AlertDialog.Builder(Starting_page.this);
            aBuilder.setTitle("Login");

            if(res==true)
                  aBuilder.setMessage("Welcome to tifin near me \'"+user+"\'");
            else if(res==false)
                aBuilder.setMessage("Username/Password is incorrect!");


            aBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if(res==true)
                    startActivity(new Intent(Starting_page.this, MainActivity.class));
                    else
                        return;
                }
            });
            aBuilder.show();

            //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.i(p,"onProgressUpdate");
            dialog.dismiss();
        }
    }
}
