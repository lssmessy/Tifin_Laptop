package com.tifinnearme.priteshpatel.tifinnearme.signup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.tifinnearme.priteshpatel.tifinnearme.MainActivity;
import com.tifinnearme.priteshpatel.tifinnearme.R;
import com.tifinnearme.priteshpatel.tifinnearme.api_links.API_LINKS;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pritesh.patel on 21-05-15.
 */
public class Activation_page extends Activity {
    private EditText textfield;
    String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activation_code);
        textfield=(EditText)findViewById(R.id.editText2);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(Activation_page.this);
        this.username=preferences.getString("username","");

    }
    public void checkActivation(View view){

       if(textfield.getText().toString().trim().length()> 0)
       {
           new LoadinBackGround().execute();

       }
        else {

           AlertDialog.Builder aBuilder=new AlertDialog.Builder(Activation_page.this);
           aBuilder.setTitle("Activation code");
           aBuilder.setMessage("Please enter activation code!");
           aBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();

               }
           });
           aBuilder.show();
       }


    }
    public void reGenerateCode(View view){
                new RegenerateBackground().execute();
    }
    public class LoadinBackGround extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog dialog,dialog2;
        static final String p="MyLog";
        boolean res=false;
        String match;
        String errors="";

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog=new ProgressDialog(Activation_page.this);
            dialog.setMessage("Registering...");
            dialog.setTitle("Sign up");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dialog2=new ProgressDialog(Activation_page.this);
            dialog2.setMessage("Redirecting...");
            dialog2.setTitle("Login");
            dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dialog.show();

            Log.i(p, "onPreExecute");
        }


        @Override
        protected Void doInBackground(Void... params) {


            List<NameValuePair> data=new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair("activation_code",textfield.getText().toString()));
            data.add(new BasicNameValuePair("username",username));
            try{
                HttpClient client=new DefaultHttpClient();
                HttpPost post=new HttpPost(API_LINKS.URL_LINK+API_LINKS.ACTIVATION_LINK);
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
                    //jsonObject.get("user_registered");
                    //this.user= String.valueOf(jsonObject.get("user_data"));
                    match=jsonObject.getString("match");

                    if(match=="true"){
                        this.res=true;

                    }
                    else
                    {
                        this.res=false;
                        errors="Entered activation code is wrong!!";
                    }

                }




            }catch (Exception e){e.getMessage();}


            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(p,"onPostExecute");
            dialog.dismiss();
            AlertDialog.Builder aBuilder=new AlertDialog.Builder(Activation_page.this);
            aBuilder.setTitle("Login");

            if(res==true)
            {
                dialog2.show();
                startActivity(new Intent(Activation_page.this, MainActivity.class));
                dialog2.dismiss();
                finish();
            }
            else if(res==false) {
                aBuilder.setMessage(errors);


                aBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                aBuilder.show();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.i(p,"onProgressUpdate");
            dialog.dismiss();
        }
    }

    public class RegenerateBackground extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog dialog,dialog2;
        static final String p="MyLog";
        boolean res=false;
        String new_code;
        String errors="";

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog=new ProgressDialog(Activation_page.this);
            dialog.setMessage("Regerating code...");
            dialog.setTitle("Sign up");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dialog.show();

            Log.i(p, "onPreExecute");
        }


        @Override
        protected Void doInBackground(Void... params) {


            List<NameValuePair> data=new ArrayList<NameValuePair>();
            //data.add(new BasicNameValuePair("activation_code",textfield.getText().toString()));
            data.add(new BasicNameValuePair("username",username));
            data.add(new BasicNameValuePair("regenerate","true"));
            try{
                HttpClient client=new DefaultHttpClient();
                HttpPost post=new HttpPost(API_LINKS.URL_LINK+API_LINKS.ACTIVATION_LINK);
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
                    //jsonObject.get("user_registered");
                    //this.user= String.valueOf(jsonObject.get("user_data"));
                    new_code=jsonObject.getString("new_code");

                }




            }catch (Exception e){e.getMessage();}


            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(p,"onPostExecute");
            dialog.dismiss();
            AlertDialog.Builder aBuilder=new AlertDialog.Builder(Activation_page.this);
            aBuilder.setTitle("Login");
            aBuilder.setMessage("New code is: \n" + new_code);
            aBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
                aBuilder.show();
            }

        }


    }



