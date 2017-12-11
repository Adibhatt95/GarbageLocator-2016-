package com.TeamGlapp.BCIBUU.garbagelocator;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import android.app.ActionBar;

public class Dashboard extends FragmentActivity {
    Intent intent1;
    private GoogleMap mMap = null; // Might be null if Google Play services APK is not available.
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = 8;
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(122 - 97) + 97);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public static String user = null;
    public static String userType = null;


    public void doFileUpload(File f, int type, String data)
    {

        AmazonUpDownLoad l_amaud = new AmazonUpDownLoad(getApplicationContext());
        l_amaud.BeginUpload(getApplicationContext(), f);

        startsendingdata(data);

    }
        /*
    public void doFileUpload(File f, int type, String data)
            throws JSONException {
        Log.e("CHAT VIEW", "Started");
        String charset = "UTF-8";
        String requestURL = "http://api-ultrafile.rhcloud.com/upload/";//ultraimg.com/api/1/upload/?key=3374fa58c672fcaad8dab979f7687397";


        MultipartUtility multipart;
        try {
            multipart = new MultipartUtility(requestURL, charset);

            multipart.addFilePart("source", f);

            List<String> response = multipart.finish(); // response from server.

            for (String s : response) {
                Log.e("Response is", s);

                JSONObject jObj = new JSONObject(s);
                JSONArray jObj2 = jObj.getJSONArray("files");
                // String name = jObj.getString("files");
                //Log.e("name is:", name);
                jObj = jObj2.getJSONObject(0);
                String date = jObj.getString("server_name");
                date = date.substring(0, date.length() - 4);
                Log.e("date is:", date);
                startsendingdata(date);
                //  setUpMap();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    static final int CAMERA_REQUEST = 1;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("CHAT VIEW", "ON ACTIVITY CALLED");
        if (requestCode == CAMERA_REQUEST
                && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            File file = Environment.getExternalStorageDirectory();
            file = new File(file,
                    Constants.CHAT_DATA_DIRECTORY);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(file, Constants.IMAGES_DIRECTORY);
            if (!file.exists()) {
                file.mkdirs();
            }
            final File f = new File(file, random() + ".png");
            try {
                f.createNewFile();

                // Convert bitmap to byte array
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 65 /* ignored for PNG */, bos);
                byte[] bitmapdata = bos.toByteArray();
                doFileUpload(f, com.TeamGlapp.BCIBUU.garbagelocator.Constants.IMAGE, f.getName());
                // write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                //AmazonUpDownLoad l_amaud = new AmazonUpDownLoad(getApplicationContext());
                //l_amaud.BeginUpload(getApplicationContext(), f);
                //startsendingdata(f.getAbsoluteFile().getName());
                /*new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Intent intent = getIntent();
                            //doFileUpload(f, com.example.user.garbagelocator.Constants.IMAGE, f.getName());
                            finish();
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    protected void onPostExecute(Void result) {

                    }

                    ;
                }.execute();*/

            } catch (Exception e) {
                Log.e("Not done", e.getMessage());

            }

        }

        // super.onActivityResult(requestCode, resultCode, data);
    }

    public Button btn;
    public Button btn2;
    public Button btn3;
    String redOrGreen = "both";//made entirely by aditya bhatt.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//made entirely by aditya bhatt.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btn = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.buttonClearedOnly);
        btn3 = (Button) findViewById(R.id.buttonUnclearedOnly);


        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        user = name;
        if (userType == null) {
            userType = user;
        }
        setUpMapIfNeeded();
        //TextView tview = (TextView) findViewById(R.id.textView);
        //tview.setText("Welcome, " + name);
        ActionBar l_ab = getActionBar();
        String l_name = name;
       // if (name.length() > 18)
        {//made entirely by aditya bhatt.
         //   l_name = name.substring(0,18);
        }
        l_ab.setTitle(l_name);

        if (userType.toLowerCase().contains("clnx")) {
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
        } else {
            btn2.setVisibility(View.INVISIBLE);
            btn3.setVisibility(View.INVISIBLE);
        }
        //getCurrentLocation();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();//made entirely by aditya bhatt.

    }

    boolean p_isMarked = false;
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    public double lat = -1;
    public double lon = -1;

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.

            if (mMap != null) {
                setUpMap();
                if (p_isMarked == false) {
                    LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        btn.setEnabled(false);
                        new AlertDialog.Builder(this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Error")//made entirely by aditya bhatt.
                                .setMessage("Please turn on location service!")
                                .show();
                    }
                }

                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                    @Override
                    public void onMyLocationChange(Location arg0) {
                        // TODO Auto-generated method stub
                        if (p_isMarked == false) {
                            // mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                            p_isMarked = true;
                        }
                        btn.setEnabled(true);
                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), 15));
                        // mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                        arg0.setAccuracy(10);

                        lat = (double) arg0.getLatitude();
                        lon = (double) arg0.getLongitude();
                        //String latt = Float.toString(lat);
                        //Log.e("lat=",latt);
                    }
                });

            }//made entirely by aditya bhatt.

        }
    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    Intent intent3;

    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.0473, 72.8356), 12));
        //mMap.setMyLocationEnabled(true);
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION,getApplicationContext(), this)) {
            mMap.setMyLocationEnabled(true);
        }
        else
        {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,PERMISSION_REQUEST_CODE_LOCATION,getApplicationContext(),this);
        }//made entirely by aditya bhatt.

        mMap.setBuildingsEnabled(true);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://adityabhatt.com/quickfire/MapReturn.aspx?user=" + user + "&state=" + Dashboard.userType;;
        //intent1 = new Intent(this, Login.class);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        //Toast toast=Toast.makeText(getApplicationContext(),response+"=response",Toast.LENGTH_SHORT);
                        //toast.setMargin(50,50);
                        //toast.show();
                        // r = response;
                        if (response != null) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Loading your complaints on the Map...", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            String[] arr = response.split(" ");
                            //startActivityForResult(intent1, 1);
                            if (response.equals("No rows found."))
                                return;
                            for (int i = 0; i < arr.length; i++) {
                                String[] loc = arr[i].split(",");
                                if (loc[2].equals("True") && (redOrGreen.equals("green") || redOrGreen.equals("both")) && loc[3].toLowerCase().equals("none")) {
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(Float.parseFloat(loc[0]), Float.parseFloat(loc[1])))
                                            .title("ImageUploaded here")
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                } else if (loc[2].equals("False") && (redOrGreen.equals("red") || redOrGreen.equals("both")) && loc[3].toLowerCase().equals("none")) {
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(Float.parseFloat(loc[0]), Float.parseFloat(loc[1])))
                                            .title("ImageUploaded here")
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                } else if (loc[3].toLowerCase().equals("yes")) {
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(Float.parseFloat(loc[0]), Float.parseFloat(loc[1])))
                                            .title("ImageUploaded here")
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                            .rotation(180));
                                }
                            }
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                            toast.setMargin(100, 50);
                            toast.show();
                        }
//made entirely by aditya bhatt.
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection. Turn it on if you want to view map.", Toast.LENGTH_SHORT);
                toast.setMargin(50, 50);
                toast.show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        intent3 = new Intent(this, ViewImage.class);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                Toast toast2 = Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT);
                toast2.setGravity(Gravity.CENTER, 0, 0);
                toast2.show();
                LatLng loc = arg0.getPosition();// if marker source is clicked

                //Double lonn = loc.longitude; //(double)Math.round(loc.longitude * 1000000d) / 100000d ;
                //Double latt = loc.latitude; // (double)Math.round(loc.latitude * 1000000d) / 100000d ;
                Double lonn = (double) Math.round(loc.longitude * 100000d) / 100000d;
                Double latt = (double) Math.round(loc.latitude * 100000d) / 100000d;
               // Toast toast = Toast.makeText(getApplicationContext(), latt.toString() + " " + lonn.toString(), Toast.LENGTH_SHORT);
                //toast.setMargin(100, 50);//made entirely by aditya bhatt.
                //toast.show();
                Bundle bundle2 = new Bundle();
                String l_str = lonn.toString();
                //l_str = l_str.substring(0,8);
                bundle2.putString("longi", l_str);// lonn.toString());
                l_str = latt.toString();
                //l_str = l_str.substring(0,8);
                bundle2.putString("latti", l_str);// latt.toString());
                bundle2.putString("currentlat", Double.toString(lat));
                bundle2.putString("currentlon", Double.toString(lon));
                bundle2.putString("status", "main map");
                bundle2.putString("username", user);
                intent3.putExtras(bundle2);
                startActivityForResult(intent3, 1);

                //Toast.makeText(MainActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                return true;
            }

        });
    }

    /* void getCurrentLocation() {
         Location myLocation  = mMap.getMyLocation();
         if(myLocation!=null)
         {
             double dLatitude = myLocation.getLatitude();
             double dLongitude = myLocation.getLongitude();
             Log.i("APPLICATION", " : " + dLatitude);
             Log.i("APPLICATION"," : "+dLongitude);
             mMap.addMarker(new MarkerOptions().position(
                     new LatLng(dLatitude, dLongitude)).title("My Location"));
             mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatitude, dLongitude), 8));

         }
         else
         {
             Toast.makeText(this, "Unable to fetch the current location", Toast.LENGTH_SHORT).show();
         }

     }*/
    public void onAddPhotoClick(View v) {
        if ((lat == 0 && lon == 0) || lat == -1 || lon == -1) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("Please turn on location service!")
                    .show();
            return;
        }//made entirely by aditya bhatt.

        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,getApplicationContext(), this)) {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, CAMERA_REQUEST);
        }
        else
        {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_REQUEST_CODE_EXTSTOR, getApplicationContext(), this);
        }


    }//made entirely by aditya bhatt.

    public void startsendingdata(String date) {
        if ((lat == 0 && lon == 0) || lat == -1 || lon == -1) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("Please turn on location service!")
                    .show();
            return;//made entirely by aditya bhatt.
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        double lonwe = (float) Math.round(lon * 100000d) / 100000d;
        double latwe = (float) Math.round(lat * 100000d) / 100000d;

        String address = getCompleteAddressString(lat, lon);
        int i = 0;
        /*while(address.equals("")||address.equals(" ")||address.equals(null)||(i<7))
        {
            address = getCompleteAddressString(lat, lon);
            i++;
        }*/
        if (address.equals("") || address.equals(" ") || address.equals(null)) {
            address = "address not found by google maps";
        }
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        String time = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
//made entirely by aditya bhatt.
        if (c.get(Calendar.MINUTE) < 10) {
            time = c.get(Calendar.HOUR_OF_DAY) + ":0" + c.get(Calendar.MINUTE);
        }
        String DateTime = day + "/" + month + "/" + year + "_at_" + time;
        String url = "http://adityabhatt.com/quickfire/MapForm.aspx?User=" + user + "&Imgname=" + date + "&lat=" + latwe + "&lon=" + lonwe + "&add=" + address + "&DT=" + DateTime;
        //intent1 = new Intent(this, Login.class);
// Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        //Toast toast=Toast.makeText(getApplicationContext(),response+"=response",Toast.LENGTH_SHORT);
                        //toast.setMargin(50,50);
                        //toast.show();
                        // r = response;
                        if (response.contains("Success")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Uploading Successful.", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            Intent intent = getIntent();
                            //doFileUpload(f, com.example.user.garbagelocator.Constants.IMAGE, f.getName());
                            finish();
                            startActivity(intent);
                            //startActivityForResult(intent1, 1);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection. Turn it on if you want to upload image.", Toast.LENGTH_SHORT);
                toast.setMargin(50, 50);
                toast.show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void OnRefresh(View v) {
        Intent intent4 = getIntent();
        finish();
        startActivity(intent4);
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(",");
                }
                strAdd = strReturnedAddress.toString();
                strAdd = strAdd.replaceAll(" ", "%20");
                // Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                //  Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    public void ClearedOnly(View v) {
        mMap.clear();
        redOrGreen = "green";
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://adityabhatt.com/quickfire/MapReturn.aspx?user=" + user + "&state=" + Dashboard.userType;;
        //intent1 = new Intent(this, Login.class);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        //Toast toast=Toast.makeText(getApplicationContext(),response+"=response",Toast.LENGTH_SHORT);
                        //toast.setMargin(50,50);
                        //toast.show();
                        // r = response;
                        if (response != null) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Loading Map.", Toast.LENGTH_SHORT);
                            toast.setMargin(100, 50);
                            toast.show();
                            String[] arr = response.split(" ");
                            //startActivityForResult(intent1, 1);
                            if (response.equals("No rows found."))
                                return;
                            for (int i = 0; i < arr.length; i++) {
                                String[] loc = arr[i].split(",");
                                if (loc[2].equals("True") && loc[3].equals("none")) {
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(Float.parseFloat(loc[0]), Float.parseFloat(loc[1])))
                                            .title("ImageUploaded here")
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                } else if (loc[3].equals("Yes")) {
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(Float.parseFloat(loc[0]), Float.parseFloat(loc[1])))
                                            .title("ImageUploaded here")
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                            .rotation(180));
                                }


                            }
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                            toast.setMargin(100, 50);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection. Turn it on if you want to view map.", Toast.LENGTH_SHORT);
                toast.setMargin(50, 50);
                toast.show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        intent3 = new Intent(this, ViewImage.class);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                Toast toast2 = Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT);
                toast2.setGravity(Gravity.CENTER, 0, 0);
                toast2.show();
                LatLng loc = arg0.getPosition();// if marker source is clicked

                //Double lonn = loc.longitude; //(double)Math.round(loc.longitude * 1000000d) / 100000d ;
                //Double latt = loc.latitude; // (double)Math.round(loc.latitude * 1000000d) / 100000d ;
                if ((lat == -1 || lon == -1) && userType.toLowerCase().contains("clnx")) {
                    new AlertDialog.Builder(Dashboard.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Error")
                            .setMessage("Turn on Location Services and click on marker again.")
                            .show();

                    return false;
                }
                Double lonn = (double) Math.round(loc.longitude * 100000d) / 100000d;
                Double latt = (double) Math.round(loc.latitude * 100000d) / 100000d;
                Toast toast = Toast.makeText(getApplicationContext(), latt.toString() + " " + lonn.toString(), Toast.LENGTH_SHORT);
                toast.setMargin(100, 50);
                toast.show();
                Bundle bundle2 = new Bundle();
                String l_str = lonn.toString();
                //l_str = l_str.substring(0,8);
                bundle2.putString("longi", l_str);// lonn.toString());
                l_str = latt.toString();
                //l_str = l_str.substring(0,8);
                bundle2.putString("latti", l_str);// latt.toString());
                bundle2.putString("currentlat", Double.toString(lat));
                bundle2.putString("currentlon", Double.toString(lon));
                bundle2.putString("status", "cleared");
                bundle2.putString("username", user);
                intent3.putExtras(bundle2);
                startActivityForResult(intent3, 1);
                //Toast.makeText(MainActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                return true;
            }

        });
    }

    public void UnclearedOnly(View v) {
        mMap.clear();
        redOrGreen = "red";
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://adityabhatt.com/quickfire/MapReturn.aspx?user=" + user + "&state=" + Dashboard.userType;;
        //intent1 = new Intent(this, Login.class);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        //Toast toast=Toast.makeText(getApplicationContext(),response+"=response",Toast.LENGTH_SHORT);
                        //toast.setMargin(50,50);
                        //toast.show();
                        // r = response;
                        if (response != null) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Loading Map.", Toast.LENGTH_SHORT);
                            toast.setMargin(100, 50);
                            toast.show();
                            String[] arr = response.split(" ");
                            //startActivityForResult(intent1, 1);
                            if (response.equals("No rows found."))
                                return;
                            for (int i = 0; i < arr.length; i++) {
                                String[] loc = arr[i].split(",");
                                if (loc[2].equals("False")) {
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(Float.parseFloat(loc[0]), Float.parseFloat(loc[1])))
                                            .title("ImageUploaded here")
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                }
                            }

                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                            toast.setMargin(100, 50);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection. Turn it on if you want to view map.", Toast.LENGTH_SHORT);
                toast.setMargin(50, 50);
                toast.show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        intent3 = new Intent(this, ViewImage.class);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                Toast toast2 = Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT);
                toast2.setGravity(Gravity.CENTER, 0, 0);
                toast2.show();
                if ((lat == -1 || lon == -1) && userType.toLowerCase().contains("clnx")) {
                    new AlertDialog.Builder(Dashboard.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Error")
                            .setMessage("Turn on Location Services and click on marker again.")
                            .show();

                    return false;
                }
                LatLng loc = arg0.getPosition();// if marker source is clicked

                //Double lonn = loc.longitude; //(double)Math.round(loc.longitude * 1000000d) / 100000d ;
                //Double latt = loc.latitude; // (double)Math.round(loc.latitude * 1000000d) / 100000d ;
                Double lonn = (double) Math.round(loc.longitude * 100000d) / 100000d;
                Double latt = (double) Math.round(loc.latitude * 100000d) / 100000d;
                Toast toast = Toast.makeText(getApplicationContext(), latt.toString() + " " + lonn.toString(), Toast.LENGTH_SHORT);
                toast.setMargin(100, 50);
                toast.show();
                Bundle bundle2 = new Bundle();
                String l_str = lonn.toString();
                //l_str = l_str.substring(0,8);
                bundle2.putString("longi", l_str);// lonn.toString());
                l_str = latt.toString();
                //l_str = l_str.substring(0,8);
                bundle2.putString("latti", l_str);// latt.toString());
                bundle2.putString("currentlat", Double.toString(lat));
                bundle2.putString("currentlon", Double.toString(lon));
                bundle2.putString("status", "not cleared");
                bundle2.putString("username", user);
                intent3.putExtras(bundle2);
                startActivityForResult(intent3, 1);
                //Toast.makeText(MainActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                return true;
            }

        });
    }

    public void onLogout(View v) {
        Intent intent5 = new Intent(this, Login.class);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(getString(R.string.mprPasswdKeyStr));
        editor.putString(getString(R.string.mprAdvKeyStr), null);
        editor.putString(getString(R.string.mprUsrKeyStr), user);
        editor.putString(getString(R.string.mprPasswdKeyStr), "");
        editor.commit();
        startActivityForResult(intent5, 1);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_logout) {
            // Mark the currently visible content (e.g. article) in the activity as a favorite
            // - requires app specific code which is omitted here.

            // Tell Android that the item click has been handled.
            onLogout(item.getActionView());
            return true;
        } else if (id == R.id.action_changePasswd) {
            Intent intent = new Intent(this, BrowserActivity.class);
            startActivityForResult(intent, 1);

        }
        else if(id == R.id.aboutus)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.letsmakeindiaclean.org"));
            startActivity(browserIntent);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Dashboard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.TeamGlapp.BCIBUU.garbagelocator/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Dashboard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.TeamGlapp.BCIBUU.garbagelocator/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public static final int PERMISSION_REQUEST_CODE_LOCATION = 1;
    public static final int PERMISSION_REQUEST_CODE_EXTSTOR = 2;
    public static final int PERMISSION_REQUEST_CODE_CAMERA = 3;

    public static void requestPermission(String strPermission,int perCode,Context _c,Activity _a){

        if (ActivityCompat.shouldShowRequestPermissionRationale(_a, strPermission)){
            Toast.makeText(_a.getApplicationContext(),"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();
        } else {

            ActivityCompat.requestPermissions(_a,new String[]{strPermission},perCode);
        }
    }

    public static boolean checkPermission(String strPermission,Context _c,Activity _a){
        int result = ContextCompat.checkSelfPermission(_c, strPermission);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_REQUEST_CODE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);

                } else {

                    Toast.makeText(getApplicationContext(),"Permission Denied, You cannot access location data.",Toast.LENGTH_LONG).show();

                }
                break;
            case PERMISSION_REQUEST_CODE_EXTSTOR:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera_intent, CAMERA_REQUEST);
                } else {

                    Toast.makeText(getApplicationContext(),"Permission Denied, You cannot access location data.",Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}
