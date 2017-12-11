package com.TeamGlapp.BCIBUU.garbagelocator;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.*;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ViewImage extends FragmentActivity {
//made entirely by aditya bhatt.
    String Imgname = null;
    String latti = null;
    String longi = null;
    String username = null;
    String add = null;
    String latori = null;
    String lonori = null;
    private static final int PICK_IMAGE = 1;
    //String imageURL =   "\"http://api-ultrafile.rhcloud.com/media/";
    String imageURL = "\"https://s3-us-west-2.amazonaws.com/djacm/";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        Button b = (Button) findViewById(R.id.button3);
        ActionBar l_ab = getActionBar();
        l_ab.setDisplayHomeAsUpEnabled(true);
        Bundle bundle2 = getIntent().getExtras();
        longi = bundle2.getString("longi");
        latti = bundle2.getString("latti");
        username = bundle2.getString("username");
        latori = bundle2.getString("currentlat");
        lonori = bundle2.getString("currentlon");
        if (Dashboard.userType.toLowerCase().contains("clnx")) {
            b.setVisibility(View.VISIBLE);
        } else {
            b.setVisibility(View.INVISIBLE);
        }
        add = getCompleteAddressString(Double.parseDouble(latti), Double.parseDouble(longi));
        TextView tv = (TextView) findViewById(R.id.textView8);
        tv.setText(add);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://adityabhatt.com/quickfire/ReturnImageName.aspx?lat=" + latti + "&lon=" + longi + "";
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
                          //  Toast toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                           // toast.setMargin(100, 50);
                            //toast.setDuration(Toast.LENGTH_LONG);
                            //toast.show();
                            WebView wv = (WebView) findViewById(R.id.webView);
                            wv.setInitialScale(1);
                            wv.getSettings().setJavaScriptEnabled(true);
                            wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                            wv.getSettings().setBuiltInZoomControls(true);
                            wv.getSettings().setSupportZoom(true);
                            wv.getSettings().setLoadWithOverviewMode(true);
                            wv.getSettings().setUseWideViewPort(true);
                            String[] res = response.split(",");
                            TextView tv1 = (TextView) findViewById(R.id.textView7);
                            tv1.setText("Added by " + res[0]);
                            //wv.loadUrl("http://www.ultraimg.com/images/" + response + ".png");
                            String l_Str = "";

                            if (res[2].contains("Not")) {
                                if (!res[1].contains(".png") && !res[1].contains(".jpg") && !res[1].contains(".JPG")) {
                                    l_Str = "<!DOCTYPE html><html><body style = \"text-align:center\"><img src="
                                            + imageURL + res[1] + "\""
                                            + " width=\"100%\"></body></html>";
                                } else {
                                    l_Str = "<!DOCTYPE html><html><body style = \"text-align:center\"><img src="
                                            + imageURL + res[1] + "\""
                                            + " width=\"100%\"></body></html>";
                                }
                                tv1.setText("Added by " + res[0] + ", this has been marked uncleared.");
                                if (res[3].toLowerCase().equals("yes")) {
                                    tv1.setText("Added by " + res[0] + ", this has been marked as a designated point. That means that garbage from surrounding areas is collected and kept at this spot in order to be cleaned regularly. This spot will be visible to all users. (Please refrain from uploading images of garbage located at this spot)");
                                }
                            } else if (res[2].equals("nophotohere")) {

                                l_Str = " <html><head></head><body><Marquee>No photo Uploaded here, but the garbage has been marked cleared.</Marquee></body></html>";
                                wv.setVisibility(View.INVISIBLE);
                                tv1.setText("Added by " + res[0] + ", this has been marked cleared but without an image.");
                                if (res[3].toLowerCase().equals("yes")) {
                                    tv1.setText("Added by " + res[0] + ", this has been marked as a designated point. That means that garbage from surrounding areas is collected and kept at this spot in order to be cleaned regularly. This spot will be visible to all users.(Please refrain from uploading images of garbage located at this spot)");
                                }
                            } else if (!res[2].contains("Not")) {
                                if (!res[2].contains(".png") && !res[2].contains(".jpg") && !res[2].contains(".JPG")) {
                                    l_Str = "<!DOCTYPE html><html><body style = \"text-align:center\"><img src="
                                            + imageURL + res[2] + "\""
                                            + " width=\"100%\"></body></html>";
                                } else {
                                    l_Str = "<!DOCTYPE html><html><body style = \"text-align:center\"><img src="
                                            + imageURL + res[2] + "\""
                                            + " width=\"100%\"></body></html>";
                                }
                                tv1.setText("Added by " + res[0] + ", this has been marked cleared.");

                                if (res[3].toLowerCase().equals("yes")) {
                                    tv1.setText("Added by " + res[0] + ", this has been marked as a designated point. That means that garbage from surrounding areas is collected and kept at this spot in order to be cleaned regularly. This spot will be visible to all users.(Please refrain from uploading images of garbage located at this spot)");
                                }
                            }

                            //tv1.setText(l_Str);

                            wv.loadDataWithBaseURL(null, l_Str,
                                    "text/html", "UTF-8", null);

                            //       wv.zoomIn();
                            //wv.zoomIn();
                            Imgname = res[1];

                            wv.reload();

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
                Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection. Turn it on if you want to view image.", Toast.LENGTH_SHORT);
                toast.setMargin(90, 90);
                toast.show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    AlertDialog actions;

    public void ClearedClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option");
        String[] options = {"Upload with camera", "Upload from gallery", "Upload no photo", "Mark Designated Spot"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
        actions.show();

    }

    DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0: // camera

                    Location selected_location = new Location("locationA");
                    selected_location.setLatitude(Double.parseDouble(latori));
                    selected_location.setLongitude(Double.parseDouble(lonori));
                    Location near_locations = new Location("locationA");
                    near_locations.setLatitude(Double.parseDouble(latti));
                    near_locations.setLongitude(Double.parseDouble(longi));

                    double distance = selected_location.distanceTo(near_locations);
                    Log.i("TAG", Double.toString(distance));
                    if (distance < 40) {
                        if (Dashboard.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getApplicationContext(), ViewImage.this)) {
                            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(camera_intent, CAMERA_REQUEST);
                        }
                        else
                        {
                           Dashboard.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Dashboard.PERMISSION_REQUEST_CODE_EXTSTOR, getApplicationContext(), ViewImage.this);
                        }

                    } else {

                        new AlertDialog.Builder(ViewImage.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Error")
                                .setMessage("You are not close to the uploaded image! 1)Go back to the map screen and 2)Turn on Location Services(if not on), 3)go closer to the marker and click on it again.")
                                .show();
                        return;
                    }
                    break;
                case 1: // gallery
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                    try {
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), FILE_SELECT_CODE);
                    } catch (ActivityNotFoundException ex) {
                        // Potentially direct the user to the Market with a Dialog
                        Toast.makeText(ViewImage.this, "Please install a File Manager.",
                                Toast.LENGTH_SHORT).show();
                    }

                    break;
                case 2: // no image
                    startsendingdata("nophotohere");
                    break;
                case 3: //designated spot
                    startsendingedesignatedspot();
                    break;
                default:
                    break;
            }
        }
    };

    private String getPath(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

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

    String user;

    public void doFileUpload(File f, int type, String data) {
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

                //JSONObject jObj = new JSONObject(s);
                //String name = jObj.getString("image");
                //Log.e("name is:", name);
                //jObj = new JSONObject(name);
                //String date = jObj.getString("name");
                //Log.e("date is:", date);
                //startsendingdata(date);
                //  setUpMap();
                JSONObject jObj = new JSONObject(s);
                JSONArray jObj2 = jObj.getJSONArray("files");
                // String name = jObj.getString("files");
                //Log.e("name is:", name);
                jObj = jObj2.getJSONObject(0);
                String date= jObj.getString("server_name");
                //date = date.substring(0,date.length()-4);
                Log.e("date is:", date);
                startsendingdata(date);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    static final int CAMERA_REQUEST = 1;
    static final int FILE_SELECT_CODE = 3;
    File pdf;

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

                // write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                doFileUpload(f, Constants.IMAGE, f.getName());
                /*
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            doFileUpload(f, com.example.user.garbagelocator.Constants.IMAGE, f.getName());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    protected void onPostExecute(Void result) {

                    }

                    ;
                }.execute();*/

            } catch (Exception e) {
                Log.e("not proper", e.getMessage());

            }

        } else if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {

            Log.e("CHAT VIEW", "ON ACTIVITY CALLED");
            //Uri uri = data.getData();
            Uri uri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(
                    uri, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            String value=null;
            if(filePath == null)
            {
                filePath=ViewImage.getPath(getApplicationContext(), uri);

                pdf = new File(filePath);
            }
            else {

                cursor.close();
                Log.d("", "File Uri: " + uri.toString());
                // Get the path
                try {
                    //String path = uri.getPath();
                    //  Log.e("path is",path);
                    pdf = new File(filePath);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(pdf == null)
            {
                //do this

            }
            long Filesize = getFolderSize(pdf) / 1024;
            long value2 = 0;
            if (Filesize >= 1024)
                value2 = Filesize / 1024;

            if (value2 > 2) {
                new AlertDialog.Builder(ViewImage.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Error")
                        .setMessage("Not permitted to upload file more than 2 mb")
                        .show();

                return;
            }
            doFileUpload(pdf, Constants.IMAGE, pdf.getName());

           /*final ProgressDialog dialog = ProgressDialog.show(ViewImage.this, "", "Uploading...",true);
            new AsyncTask<Void, Void, Void>() {




                @Override
                protected Void doInBackground(Void... params) {
                    try {

                        doFileUpload(pdf, 2, "");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                protected void onPostExecute(Void result) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            "file uploaded" , Toast.LENGTH_LONG).show();
                }
            }.execute();*/
            // Log.d(TAG, "File Path: " + path);
        }
        // super.onActivityResult(requestCode, resultCode, data);
    }
    private Uri getUri() {
        String state = Environment.getExternalStorageState();
        if(!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
    Intent intent1;

    public static long getFolderSize(File f) {
        long size = 0;
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                size += getFolderSize(file);
            }
        } else {
            size = f.length();
        }
        return size;
    }

    public void startsendingdata(String date) {
        if (Imgname == null || latti == null || longi == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT);
            toast.setMargin(100, 50);
            toast.show();
            return;
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        String time = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);

        String DateTime = day + "/" + month + "/" + year + "_at_" + time;
        String url = "http://adityabhatt.com/quickfire/UpdateorDelete.aspx?key=" + "update" + "&Imgname=" + date + "&lat=" + latti + "&lon=" + longi + "&ImgnameOri=" + Imgname + "&DTC=" + DateTime;
        //intent1 = new Intent(this, ViewImage.class);
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
                            Toast toast = Toast.makeText(getApplicationContext(), "Updating Successful.", Toast.LENGTH_SHORT);
                            toast.setMargin(100, 50);
                            toast.show();
                            Intent intent3 = getIntent();
                            finish();
                            startActivity(intent3);
                            //startActivityForResult(intent1, 1);
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
                Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection. Turn it on if you want to upload image.", Toast.LENGTH_SHORT);
                toast.setMargin(50, 50);
                toast.show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    public void startsendingedesignatedspot() {
        if (Imgname == null || latti == null || longi == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT);
            toast.setMargin(100, 50);
            toast.show();
            return;
        }
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://adityabhatt.com/quickfire/UpdateorDelete.aspx?key=" + "updatedesig" + "&lat=" + latti + "&lon=" + longi + "&ImgnameOri=" + Imgname;
        //intent1 = new Intent(this, ViewImage.class);
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
                            Toast toast = Toast.makeText(getApplicationContext(), "Updating Successful, marked as designated spot.", Toast.LENGTH_SHORT);
                            toast.setMargin(100, 50);
                            toast.show();
                            Intent intent3 = getIntent();
                            finish();
                            startActivity(intent3);
                            //startActivityForResult(intent1, 1);
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
                Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection. Turn it on if you want to upload image.", Toast.LENGTH_SHORT);
                toast.setMargin(50, 50);
                toast.show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
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
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                // Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                //  Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  Log.w("My Current loction address", "Cant get Address!");
        }
        return strAdd;
    }

    public void onEmailClick(View v) {
        try {
            String message = "";
            message += "(Please send this email only if your complaint-has not been met with/marker not turned green yet- and its been a week since you uploaded this complaint). The Following location was tagged indicating a location of an open garbage dump: ";
            message += "\n" + "Location: Latitude=" + latti.toString() + " and Longitude=" + longi.toString();
            message += "\n" + "Address: " + add;
            message += "\n" + "The Image at this position can be viewed at"+imageURL + Imgname;
            message += "\n" + "Sent from DJSCE Garbage Locator App.";
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"djletsmakeindiagreen@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tagged Location of Garbage Dump which has not been cleared for more than a week.");
            emailIntent.putExtra(Intent.EXTRA_TEXT, message);
            emailIntent.setType("message/rfc822");
            startActivity(emailIntent);

        } catch (ActivityNotFoundException e) {


        }
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
        if (item.getItemId() == android.R.id.home) {
            // Handle "up" button behavior here.
            onBackPressed();
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
                "ViewImage Page", // TODO: Define a title for the content shown.
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
                "ViewImage Page", // TODO: Define a title for the content shown.
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

//the onclick listener which calls onActivityresult
public static String getPath(final Context context, final Uri uri)
{
    final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT;

    // DocumentProvider
    if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }

            // TODO handle non-primary volumes
        }
        // DownloadsProvider
        else if (isDownloadsDocument(uri)) {

            final String id = DocumentsContract.getDocumentId(uri);
            final Uri contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

            return getDataColumn(context, contentUri, null, null);
        }
        // MediaProvider
        else if (isMediaDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }

            final String selection = "_id=?";
            final String[] selectionArgs = new String[] {
                    split[1]
            };

            return getDataColumn(context, contentUri, selection, selectionArgs);
        }
    }
    // MediaStore (and general)
    else if ("content".equalsIgnoreCase(uri.getScheme())) {
        return getDataColumn(context, uri, null, null);
    }
    // File
    else if ("file".equalsIgnoreCase(uri.getScheme())) {
        return uri.getPath();
    }

    return null;
}

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }



}
