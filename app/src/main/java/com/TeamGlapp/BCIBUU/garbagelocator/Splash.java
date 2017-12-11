package com.TeamGlapp.BCIBUU.garbagelocator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 16-01-2016.
 */

public class Splash extends Activity {
    String check="No";
    private static int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ss);
        checkLoginExitIfErrorWithMessage();
//made entirely by aditya bhatt.


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(check.equals("Yes")) {
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                }
                else if (check.equals("No"))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Your Password has been changed.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove(getString(R.string.mprPasswdKeyStr));
                    editor.putString(getString(R.string.mprAdvKeyStr), null);
                    editor.putString(getString(R.string.mprUsrKeyStr), l_usrnameStr);
                    editor.putString(getString(R.string.mprPasswdKeyStr), "");
                    editor.commit();
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                }
                else if(check.equals("Exit"))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection. Turn it on if you want to use the app.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    startActivity(intent);
                }
                else if(check.equals("startlogin"))
                {
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                }//made entirely by aditya bhatt.
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
        getActionBar().hide();
/*
        final ImageView iv = (ImageView)findViewById(R.id.imageView);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
       // final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.);
        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent i = new Intent(getBaseContext(), Login.class);

            }//made entirely by aditya bhatt.

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
    }
    void saveCredentials(String l_advCodeStr,  String l_usrnameStr, String l_passwdStr)
    {
        SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.mprAdvKeyStr), l_advCodeStr);
        editor.putString(getString(R.string.mprUsrKeyStr), l_usrnameStr);
        editor.putString(getString(R.string.mprPasswdKeyStr), l_passwdStr);
        editor.commit();
    }//made entirely by aditya bhatt.
    String l_usrnameStr;
    String l_passwdStr;

    public void checkLoginExitIfErrorWithMessage()
    {
        SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //l_advcodeStr = "19A2-2657-78A8-B648";
        //l_usrnameStr = "My_Family";
        //l_passwdStr = "b3w1h6o2m6z4";
        // l_advcodeStr = sharedPref.getString(getResources().getString(R.string.mprAdvKeyStr), "");
        l_usrnameStr = sharedPref.getString(getResources().getString(R.string.mprUsrKeyStr), "");
         l_passwdStr = sharedPref.getString(getResources().getString(R.string.mprPasswdKeyStr), "");
        if(l_passwdStr.equals(null)||l_passwdStr.equals("")||l_passwdStr.equals(" "))
        {
            check="startlogin";
            return;
        }//made entirely by aditya bhatt.
        String l_advcodeStr = Dashboard.userType = sharedPref.getString(getResources().getString(R.string.mprAdvKeyStr), "");
        try {
//made entirely by aditya bhatt.
            // url = new URL(p_URL);
            // urlConnection = (HttpURLConnection) url.openConnection();
            String l_str = "hello";
            //in = new java.io.BufferedInputStream(urlConnection.getInputStream());
            //java.io.readStream(in);
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://adityabhatt.com/quickfire/WebForm1.aspx"; //?Usrname=" + l_usrnameStr + "&passwd=" + l_passwdStr;

// Request a string response from the provided URL.

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //mTextView.setText("Response is: "+ response.substring(0,500));
                            //Toast toast=Toast.makeText(getApplicationContext(),response+"=response",Toast.LENGTH_SHORT);
                            //toast.setMargin(50,50);
                            //toast.show();
//made entirely by aditya bhatt.
                            if (response.contains("Success")) {
                                if (response.contains("bmc"))
                                {
                                    Dashboard.userType = "clnx";
                                    check = "Yes";

                                }
                                else
                                {
                                    Dashboard.userType = "none";
                                    check = "Yes";

                                }
                                saveCredentials(Dashboard.userType, l_usrnameStr, l_passwdStr);
                                return;

                                // progDailog.dismiss();
                            } else {
                    ;
                                Toast toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                check = "No";
                                return;//made entirely by aditya bhatt.
                            }
//made entirely by aditya bhatt.
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //mTextView.setText("That didn't work!");


                            check = "Exit";
                            return;
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Usrname", l_usrnameStr);
                    params.put("passwd", l_passwdStr);
                    return params;
                }
            };
//made entirely by Aditya Bhatt.
// Add the request to the RequestQueue.
            queue.add(stringRequest);
            //}

        } catch(Exception e) {
            //e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), "Internet Connection Lost.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        } finally {
            // progDailog=null;
            //urlConnection.disconnect();
        }

    }
}
