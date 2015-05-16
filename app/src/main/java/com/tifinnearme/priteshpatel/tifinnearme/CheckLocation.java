package com.tifinnearme.priteshpatel.tifinnearme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tifinnearme.priteshpatel.tifinnearme.gps.GPSTracker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;/**
 * Created by pritesh.patel on 31-03-15.
 */
public class CheckLocation extends Activity {
    private GoogleMap map;
    private Location loc;
    private  LocationManager locationManager;
    GPSTracker myGps;
    Geocoder geocoder;
    EditText et;
    private ConnectivityManager connectivityManager;
    double lattitude,longitude;
    String street,City,State,Country,postal_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLocation();
    }

    public void checkLocation(){


        if(isInternetAvailable()==false)
        {
            Log.i("Internet", "Disabled");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("No internet connection is available. Please check your internet connection before using this app!");
            alert.setTitle("No Internet");
            alert.setPositiveButton("Wifi Settings",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent i=new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(i);
                }
            });
            alert.setNegativeButton("Mobile data",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Intent i=new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);

                    startActivity(i);
                }
            });

            alert.show();
        }

        else if(isInternetAvailable()) {
            Log.i("Internet","Available");
            myGps=new GPSTracker(CheckLocation.this);
            if (myGps.canGetLocation()) {
                Log.i("canGetLocation","yes");
                this.lattitude = myGps.getLatitude();
                this.longitude = myGps.getLongitude();
                geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> address = null;
                try {
                    address = geocoder.getFromLocation(lattitude, longitude, 1);

                    if (address.contains(null)) {
                        throw new Exception("NULL pointer exception");
                    }else{
                        City = address.get(0).getAddressLine(0);
                        State = address.get(0).getAddressLine(1);
                        Country = address.get(0).getAddressLine(2);
                        // map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                        LatLng myLL = new LatLng(lattitude, longitude);
                        map.addMarker(new MarkerOptions().position(myLL).title(City + "," + State + "," + Country));
                        Toast.makeText(this, myLL.toString(), Toast.LENGTH_LONG).show();
                        CameraUpdate myCam = CameraUpdateFactory.newLatLngZoom(myLL, 14);
                        // map.addPolygon(new PolygonOptions().add(myLL).fillColor(Color.GRAY));
                        map.animateCamera(myCam);
                        map.addCircle(new CircleOptions().center(myLL).visible(true).radius(200));
                        map.setMyLocationEnabled(true);
                        et.setText(City + "," + State + "," + Country);

                        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                            @Override
                            public boolean onMyLocationButtonClick() {
                                map.clear();
                                checkLocation();
                                return true;
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    checkLocation();
                } catch (Exception e) {
                    e.printStackTrace();
                    checkLocation();
                }
            }
        }



    }
    public boolean isInternetAvailable(){
        connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()== NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED)

            return  true;

        else if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()== NetworkInfo.State.DISCONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.DISCONNECTED)
        {

            return false;
        }

        return  false;

    }



}
