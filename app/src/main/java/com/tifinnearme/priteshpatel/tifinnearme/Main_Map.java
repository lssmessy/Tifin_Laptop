package com.tifinnearme.priteshpatel.tifinnearme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tifinnearme.priteshpatel.tifinnearme.gps.GPSTracker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Main_Map extends ActionBarActivity implements LocationListener {
    //private final LatLng MUMBAI=new LatLng(18.9750,72.8258);
    private GoogleMap map;
    private Location loc;
    private LocationManager locationManager;
    GPSTracker myGps;
    Geocoder geocoder;
    EditText et;
    private ConnectivityManager connectivityManager;
    double lattitude, longitude;
    String street, City, State, Country, postal_code;
    Toolbar toolbar;
    //CheckLocation chk=new CheckLocation();

    //private LocationManager location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);
        /*toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);*/
        /*EditText et=new EditText(getApplicationContext());
        getActionBar().setCustomView(et);*/
        et = (EditText) findViewById(R.id.search_loc);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        checkLocation();


    }


    public void checkLocation() {


        if (isInternetAvailable() == false) {
            Log.i("Internet", "Disabled");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("No internet connection is available. Please check your internet connection before using this app!");
            alert.setTitle("No Internet");
            alert.setPositiveButton("Wifi Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(i);
                }
            });
            alert.setNegativeButton("Mobile data", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Intent i = new Intent(Settings.ACTION_SETTINGS);

                    startActivity(i);
                }
            });

            alert.show();
        } else if (isInternetAvailable()) {
            Log.i("Internet", "Available");
            myGps = new GPSTracker(this);
            if (myGps.canGetLocation()) {
                Log.i("canGetLocation", "yes");
                this.lattitude = myGps.getLatitude();
                this.longitude = myGps.getLongitude();
                geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> address = null;
                try {
                    address = geocoder.getFromLocation(lattitude, longitude, 1);

                    if (address.contains(null)) {
                        throw new Exception("NULL pointer exception");
                    } else {
                        City = address.get(0).getAddressLine(0);
                        State = address.get(0).getAddressLine(1);
                        Country = address.get(0).getAddressLine(2);
                        // map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                        LatLng myLL = new LatLng(lattitude, longitude);
                        map.addMarker(new MarkerOptions().position(myLL).title(City + "," + State + "," + Country));
                        Toast.makeText(this, myLL.toString(), Toast.LENGTH_LONG).show();
                        CameraUpdate myCam = CameraUpdateFactory.newLatLngZoom(myLL, 15);
                        // map.addPolygon(new PolygonOptions().add(myLL).fillColor(Color.GRAY));
                        map.animateCamera(myCam);
                        map.addCircle(new CircleOptions().center(myLL).visible(true).radius(500).strokeColor(Color.GRAY).strokeWidth(3));
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
                        Thread.sleep(3000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    checkLocation();
                } catch (Exception e) {
                    e.printStackTrace();
                    //checkLocation();
                }
            }
        }


    }

    @Override
    protected void onPostResume() {

        super.onPostResume();
        Log.i("onPostResume", "onPostResume");
        //checkLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume", "onResume");
        //checkLocation();

    }

    @Override
    protected void onRestart() {

        super.onRestart();
        Log.i("onRestart", "onRestart");
        checkLocation();
    }

    //To check whether internet is ON/OFF
    public boolean isInternetAvailable() {
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)

            return true;

        else if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED) {

            return false;
        }

        return false;

    }

    public void searchClick(View view) {

        //new LoadinBackground().execute();

        if (!isempty() && isInternetAvailable()) {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
            geocoder = new Geocoder(this, Locale.getDefault());

            List<Address> addresss = null;
            try {
                addresss = geocoder.getFromLocationName(et.getText().toString(), 1);
                Log.i("address", addresss.toString());
                Address add = addresss.get(0);

                if (addresss.size() > 0) {
                    lattitude = addresss.get(0).getLatitude();
                    longitude = addresss.get(0).getLongitude();
                    Toast.makeText(this, String.valueOf(lattitude) + "=>" + String.valueOf(longitude), Toast.LENGTH_SHORT).show();

                    List<Address> ad = geocoder.getFromLocation(lattitude, longitude, 1);

                    City = ad.get(0).getAddressLine(0);
                    State = ad.get(0).getAddressLine(1);
                    Country = ad.get(0).getAddressLine(2);
                    checkNull();
                    Log.i("latlng", String.valueOf(lattitude) + "=>" + String.valueOf(longitude));

                    LatLng newLoc = new LatLng(lattitude, longitude);
                    CameraUpdate showLoc = CameraUpdateFactory.newLatLngZoom(newLoc, 15);
                    if (showLoc != null) {
                        map.clear();
                        map.animateCamera(showLoc);
                        map.addCircle(new CircleOptions().center(newLoc).visible(true).radius(500).strokeColor(Color.GRAY).strokeWidth(3));

                                map.addMarker(new MarkerOptions().position(newLoc).title(City + "," + State + "," + Country));
                        et.setText(City + "," + State + "," + Country);

                    } else {
                        Toast.makeText(this, String.valueOf(lattitude) + "=>" + String.valueOf(longitude), Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {

                e.printStackTrace();
            }


        } else {
            if (!isInternetAvailable()) {
                Log.i("Internet", "Disabled");
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("No internet connection is available. Please check your internet connection before using this app!");
                alert.setTitle("No Internet");
                alert.setPositiveButton("Wifi Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(i);
                    }
                });
                alert.setNegativeButton("Mobile data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent i = new Intent(Settings.ACTION_SETTINGS);

                        startActivity(i);
                    }
                });

                alert.show();
            } else if (isempty())//If textfield is empty
                Toast.makeText(this, "Enter something to search", Toast.LENGTH_LONG).show();

        }


    }

    private void checkNull() {
        if (City == null)
            this.City = "";
        if (State == null)
            this.State = "";
        if (Country == null)
            this.Country = "";
        else if (City == null && State == null && Country == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("No place found");
            alert.setMessage("No place found for the entered location name. Please type a correct location name!");
            alert.show();
        }

    }

    private boolean isempty() {
        if (et.getText().length() > 0)
            return false;
        else
            return true;
    }

    public void removeText(View view) {
        et.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        loc.setLatitude(0.0);
        loc.setLongitude(0.0);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

 /*   public class LoadinBackground extends AsyncTask{
        ProgressDialog dialog=new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {

            dialog.setMax(100);
            dialog.setMessage("Loading locations...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          //  dialog.show();

        }


        @Override
        public Object doInBackground(Object[] params) {

            if(!isempty() && isInternetAvailable()) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                List<Address> addresss = null;
                try {
                    addresss = geocoder.getFromLocationName(et.getText().toString(), 1);
                    Log.i("address",addresss.toString());
                    Address add = addresss.get(0);

                    if (addresss.size() > 0) {
                        lattitude = addresss.get(0).getLatitude();
                        longitude = addresss.get(0).getLongitude();
                      //  Toast.makeText(MainActivity.this, String.valueOf(lattitude) + "=>" + String.valueOf(longitude), Toast.LENGTH_SHORT).show();

                        List<Address> ad = geocoder.getFromLocation(lattitude, longitude, 1);
                        street = ad.get(0).getSubLocality();
                        City = ad.get(0).getLocality();
                        State =ad.get(0).getAdminArea();
                        Country =ad.get(0).getCountryName();
                        postal_code=ad.get(0).getAddressLine(3);
                        checkNull();
                        Log.i("latlng", String.valueOf(lattitude) + "=>" + String.valueOf(longitude));

                        LatLng newLoc = new LatLng(lattitude, longitude);
                        CameraUpdate showLoc = CameraUpdateFactory.newLatLngZoom(newLoc, 16);
                        if (showLoc != null) {
                            map.clear();
                            map.animateCamera(showLoc);
                            map.addCircle(new CircleOptions().center(newLoc).visible(true).radius(200));

                            map.addMarker(new MarkerOptions().position(newLoc).title(street +" "+ City +" "+ State +" "+ Country +" "+postal_code));
                            et.setText(street +" "+ City +" "+ State +" "+ Country);

                        } else {
                            Toast.makeText(MainActivity.this, String.valueOf(lattitude) + "=>" + String.valueOf(longitude), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e) {

                    e.printStackTrace();
                }catch (IllegalStateException is){
                    is.printStackTrace();

                }


            }
            else
            {
                if(!isInternetAvailable()) {
                    Log.i("Internet", "Disabled");
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("No internet connection is available. Please check your internet connection before using this app!");
                    alert.setTitle("No Internet");
                    alert.setPositiveButton("Wifi Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(i);
                        }
                    });
                    alert.setNegativeButton("Mobile data", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent i = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(i);
                        }
                    });

                    alert.show();
                }
                else if(isempty())//If textfield is empty
                    Toast.makeText(MainActivity.this, "Enter something to search", Toast.LENGTH_LONG).show();

            }

            return 1;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }
}*/
