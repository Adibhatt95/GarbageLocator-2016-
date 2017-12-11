package com.TeamGlapp.BCIBUU.garbagelocator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Register extends Activity implements View.OnClickListener{
LinearLayout l1;
    RelativeLayout l2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_register);
        Button b= (Button) findViewById(R.id.button);
        b.setOnClickListener(this);
         l1=(LinearLayout) findViewById(R.id.layout1);
         l2=(RelativeLayout) findViewById(R.id.layout2);
    }

  boolean isVerfied = false;
    boolean isValid =false;
    Intent intent;
    String iL=null;
    String name = null;
    String email = null;
    String pass1 = null;
    String passcon = null;
    String num = null;

    boolean p_regClickInProgress = false;

    public void onRegisterClick(View v)
    {
        if (p_regClickInProgress) return;


        EditText text2 = (EditText)findViewById(R.id.editText);
        name = text2.getText().toString().trim();
        name = name.replace(" ", "_");
        text2 = (EditText)findViewById(R.id.editText2);
        email = text2.getText().toString().trim();
        text2 = (EditText)findViewById(R.id.editText3);
        pass1 = text2.getText().toString().trim();
        text2 = (EditText)findViewById(R.id.editText4);
        passcon = text2.getText().toString().trim();
        EditText number = (EditText)findViewById(R.id.editText6);
        num = number.getText().toString().trim();
        EditText Veri = (EditText)findViewById(R.id.EdVeri);
        String verific = Veri.getText().toString().trim();
        //Toast toast=Toast.makeText(getApplicationContext(),name+" "+email,Toast.LENGTH_SHORT);
        //toast.setMargin(100,50);
        //toast.show();

        if(name.isEmpty()||email.isEmpty()||pass1.isEmpty()||passcon.isEmpty()||num.isEmpty()||verific.isEmpty())
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("Please fill all fields before continuing!")
                    .show();
            return;
        }
        if(!email.contains("@"))
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("Email format wrong")
                    .show();
            return;
        }
        if(!pass1.equals(passcon))
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("Passwords to not match")
                    .show();
            return;
        }
        //iL="12234";
        if(verific.equals(iL)==false)
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("WRONG VERIFICATION PASSCODE.")
                    .show();
            return;
        }
        if(verific.equals(iL)) {

            p_regClickInProgress = true;
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://adityabhatt.com/quickfire/WebForm2Register.aspx?";//Usrname=" + name + "&email=" + email + "&passwd=" + pass1+"&phone="+num;
            intent = new Intent(this, Login.class);
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
                            // r = response;
                            if (response.contains("Success")) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Registration Successful, now please login.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                startActivityForResult(intent, 1);
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                                toast.setMargin(100, 50);
                                toast.show();
                            }
                            p_regClickInProgress = false;
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //mTextView.setText("That didn't work!");
                    Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
                    toast.setMargin(50, 50);
                    toast.show();
                    p_regClickInProgress = false;

                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Usrname", name);
                    params.put("email", email);
                    params.put("passwd", pass1);
                    params.put("phone", num);

                    return params;
                }
            };
// Add the request to the RequestQueue.
            queue.add(stringRequest);

        }


    }


    public void SendSMS(View v)
    {
        if (p_inprocess) return;
        p_inprocess = true;
        EditText number = (EditText)findViewById(R.id.editText6);
        String num = number.getText().toString().trim();
        Random r = new Random();
        int i1 = r.nextInt(90000 - 10000) + 10000;
        iL = String.valueOf(i1);
        String message= "Hello! Your Verification Code to register for our DJACM Garbage Locator Application is "+iL;
        message = message.replaceAll(" ","%20");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://bulksms.mysmsmantra.com:8080/WebSMS/SMSAPI.jsp?username=DJACM&password=1761139648&sendername=DJACMM&mobileno="+num+"&message="+message;
        intent = new Intent(this, Login.class);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        Toast toast=Toast.makeText(getApplicationContext(),"Sending SMS, please wait...",Toast.LENGTH_LONG);
                        toast.setMargin(100, 50);
                        toast.show();
                        // r = response;
                        if(response.toLowerCase().contains("success")) {
                            isValid = true;
                        }
                        else if(response.equals("DND"))
                        {
                            Toast toast2=Toast.makeText(getApplicationContext(),"Turn off your DND service.",Toast.LENGTH_SHORT);
                            toast2.setMargin(50,50);
                            toast2.show();
                            isValid=false;
                        }
                        else if(response.toLowerCase().equals("invalid"))
                        {
                            Toast toast2=Toast.makeText(getApplicationContext(),"Invalid number.",Toast.LENGTH_SHORT);
                            toast2.setMargin(50,50);
                            toast2.show();
                            isValid=false;
                        }
                        p_inprocess = false;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Toast toast=Toast.makeText(getApplicationContext(),"No Internet Connection. Turn it on if you want to register.",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                p_inprocess = false;
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    boolean p_inprocess = false;
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button)
        {
            if (p_inprocess) return;
            p_inprocess = true;
            EditText number = (EditText)findViewById(R.id.editText6);
            String num = number.getText().toString().trim();

            if(num == null || num.equals(""))
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Error")
                        .setMessage("Fill the number field!")
                        .show();
                return;

            }

            EditText edtxtcheat = (EditText)findViewById(R.id.editText6);
            Random r = new Random();
            int i1 = r.nextInt(90000 - 10000) + 10000;
            iL = String.valueOf(i1);
            String message= "Hello! Your Verification Code to register for our Garbage Locator Application is "+iL;
            message = message.replaceAll(" ","%20");
            RequestQueue queue = Volley.newRequestQueue(this);
            if(num.contains("+"))
            {
                num=num.replace("+","");
            }
            String url = "http://bulksms.mysmsmantra.com:8080/WebSMS/SMSAPI.jsp?username=DJACM&password=1761139648&sendername=DJSACM&mobileno="+num+"&message="+message;
            intent = new Intent(this, Login.class);
// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //mTextView.setText("Response is: "+ response.substring(0,500));
                            Toast toast=Toast.makeText(getApplicationContext(),"Sending SMS, please wait...",Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            // r = response;
                            if(response.toLowerCase().contains("success")) {
                                isValid = true;
                               final EditText edtxtcheat = (EditText)findViewById(R.id.editText6);
                                l2.setVisibility(View.GONE);
                                l1.setVisibility(View.VISIBLE);
                                edtxtcheat.setEnabled(false);
                            }
                            else if(response.equals("DND"))
                            {
                                Toast toast2=Toast.makeText(getApplicationContext(),"Turn off your DND service.",Toast.LENGTH_LONG);

                                toast2.show();
                                isValid=false;

                            }
                            else if(response.toLowerCase().contains("invalid"))
                            {
                                new AlertDialog.Builder(Register.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Error")
                                        .setMessage("Invalid number. :(")
                                        .show();
                                isValid=false;
                                p_inprocess = false;
                                return;
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //mTextView.setText("That didn't work!");
                    Toast toast=Toast.makeText(getApplicationContext(),"No Internet Connection. Turn it on if you want to register.",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    p_inprocess = false;

                }
            });
// Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
    }




}
