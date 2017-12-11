package com.TeamGlapp.BCIBUU.garbagelocator;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;

        import android.net.*;
        import android.preference.PreferenceManager;
        import android.os.Bundle;

        import android.view.Gravity;
        import android.view.View;

        import android.view.WindowManager;
        import android.view.inputmethod.InputMethodManager;

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


public class Login extends Activity implements View.OnClickListener{


    static String p_URL = null, p_reloadURL = null;
    LinearLayout l1,l2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        RelativeLayout rl1=(RelativeLayout) findViewById(R.id.layout);
        RelativeLayout rl2=(RelativeLayout) findViewById(R.id.button2);
        loadCredentials();
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        l1=(LinearLayout) findViewById(R.id.l1);
        l2=(LinearLayout) findViewById(R.id.l2);
        getActionBar().hide();
    }
    String l_advcodeStr = null, l_usrnameStr = null, l_passwdStr = null;
    Intent intent6;
    void loadCredentials() {


        Intent l_intent = getIntent();
        if (l_intent != null) {
            Uri l_uri = l_intent.getData();
            if (l_uri != null) {
                l_advcodeStr = l_uri.getQueryParameter("advcode");
                l_usrnameStr = l_uri.getQueryParameter("usrname");
                l_passwdStr = l_uri.getQueryParameter("passwd");
            }
        }
        SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //l_advcodeStr = "19A2-2657-78A8-B648";
        //l_usrnameStr = "My_Family";
        //l_passwdStr = "b3w1h6o2m6z4";
        // l_advcodeStr = sharedPref.getString(getResources().getString(R.string.mprAdvKeyStr), "");
        l_usrnameStr = sharedPref.getString(getResources().getString(R.string.mprUsrKeyStr), "");
        l_passwdStr = sharedPref.getString(getResources().getString(R.string.mprPasswdKeyStr), "");
        l_advcodeStr = Dashboard.userType = sharedPref.getString(getResources().getString(R.string.mprAdvKeyStr), "");
        if (l_advcodeStr == null || l_usrnameStr == null || l_passwdStr == null) {


        }
        EditText l_advCode, l_usrname, l_passwd;
        l_passwd = (EditText) findViewById(R.id.password);
        l_usrname = (EditText) findViewById(R.id.userName);
        //l_advCode = (EditText) findViewById(R.id.advCode);
        if(l_passwdStr.equals(null)||l_passwdStr.equals("")||l_passwdStr.equals(" "))
        {
            l_passwd.setText(l_passwdStr);
        }
        else {
            Intent intent6 = new Intent(this,Dashboard.class);
            Bundle bundle = new Bundle();
            bundle.putString("name",l_usrnameStr);
            intent6.putExtras(bundle);
            startActivityForResult(intent6, 1);
        }
//made entirely by aditya bhatt.

            l_usrname.setText(l_usrnameStr);
//made entirely by aditya bhatt.

        /*if (l_advcodeStr.isEmpty()) {
            MyJavaScriptInterface l_js = null;
            l_js = ReadJavaScriptLocalStorage();
        }
        else*///made entirely by aditya bhatt.




        if ( ! l_usrnameStr.isEmpty() && ! l_passwdStr.isEmpty()) {
            p_URL = "http://adityabhatt.com/quickfire/hrc.aspx?OP=200&Usrname=" + l_usrnameStr + "&passwd=" + l_passwdStr;
            p_reloadURL = "http://adityabhatt.com/quickfire/hrc.aspx?OP=201&AdvCode=" + l_advcodeStr
                    + "&Usrname=" + l_usrnameStr + "&passwd=" + l_passwdStr;

            saveCredentials(l_advcodeStr, l_usrnameStr, l_passwdStr);
//made entirely by aditya bhatt.

//            Toast toast=Toast.makeText(getApplicationContext(),l_usrnameStr,Toast.LENGTH_SHORT);
//            toast.setMargin(50,50);
//            toast.show();//made entirely by aditya bhatt.

            //Intent intent = new Intent(this, Dashboard.class);
            //startActivityForResult(intent, 1 );
        }
    }

    @Override
    protected void onNewIntent(final Intent intent) {

        super.onNewIntent(intent);
        setIntent(intent);
        //MainActivity.ShowMsg(this, intent.getDataString());
        // We do not call setIntent() with the new intent,
        // so we have to retrieve URI from the intent argument.

        // Do something with the URI...
    }
    public String r="none";
    Intent intent;
    public ProgressDialog progDailog;
    Activity activity;
    public void startDashBoard(View view) {

//made entirely by aditya bhatt.
        EditText l_advCode, l_usrname, l_passwd;
        l_passwd = (EditText) findViewById(R.id.password);
        l_usrname = (EditText) findViewById(R.id.userName);
//made entirely by aditya bhatt.

        //String l_advCodeStr = l_advCode.getText().toString();
        final String l_usrnameStr = l_usrname.getText().toString().trim();
        final String l_passwdStr = l_passwd.getText().toString().trim();

        // hide keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(l_advCode.getWindowToken(), 0);
        //hide

//made entirely by aditya bhatt.
        if (l_usrnameStr.isEmpty() || l_passwdStr.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Error")
                    .setMessage("Please fill all fields before continuing!")
                    .show();
            return;
        }
        //p_URL = "http://fno.mprofit.in/MprDashboard.aspx?OP=200&AdvCode=" + l_advCodeStr
        //        + "&Usrname=" + l_usrnameStr + "&passwd=" + l_passwdStr;
        p_URL = "http://adityabhatt.com/quickfire/hrc.aspx?OP=200"
                + "&Usrname=" + l_usrnameStr + "&passwd=" + l_passwdStr;
        activity=this;

       // java.net.URL url = null;
            //HttpURLConnection urlConnection = null;
           // java.io.InputStream in = null;
            try {
                    progDailog = ProgressDialog.show(activity, null, "Logging in ... ", true, true);
                    progDailog.show();
                    // url = new URL(p_URL);
                    // urlConnection = (HttpURLConnection) url.openConnection();
                    String l_str = "hello";
                    //in = new java.io.BufferedInputStream(urlConnection.getInputStream());
                    //java.io.readStream(in);
                    RequestQueue queue = Volley.newRequestQueue(this);
                String url = "http://adityabhatt.com/quickfire/WebForm1.aspx"; //?Usrname=" + l_usrnameStr + "&passwd=" + l_passwdStr;
                intent = new Intent(this, Dashboard.class);
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
                                r = response;
                                if (response.contains("Success")) {
                                    if (response.contains("bmc"))
                                    {
                                        Dashboard.userType = "clnx";
                                    }
                                    else//made entirely by aditya bhatt.
                                    {
                                        Dashboard.userType = "none";
                                    }
                                    saveCredentials(Dashboard.userType, l_usrnameStr, l_passwdStr);
                                    progDailog.hide();
                                    progDailog.dismiss();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("name",l_usrnameStr);

                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, 1);
                                    // progDailog.dismiss();
                                } else {
                                    progDailog.hide();
                                    progDailog.dismiss();
                                    Toast toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override//made entirely by aditya bhatt.
                            public void onErrorResponse(VolleyError error) {
                                //mTextView.setText("That didn't work!");
                                progDailog.hide();
                                progDailog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection. Turn it on if you want to login.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
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

// Add the request to the RequestQueue.

                queue.add(stringRequest);//made entirely by aditya bhatt.
                //}

            } catch(Exception e) {
                //e.printStackTrace();

            } finally {
               // progDailog=null;
                //urlConnection.disconnect();
          }

        //super.onDestroy(
    }

    void saveCredentials(String l_advCodeStr,  String l_usrnameStr, String l_passwdStr)
    {
        SharedPreferences sharedPref =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.mprAdvKeyStr), l_advCodeStr);
        editor.putString(getString(R.string.mprUsrKeyStr), l_usrnameStr);
        editor.putString(getString(R.string.mprPasswdKeyStr), l_passwdStr);
        editor.commit();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//made entirely by aditya bhatt.
        startActivity(intent);
    }
    public void startIntroPage(View view)
    {
       // Intent intent = new Intent(this, info_page.class);
       // startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(resultCode)
        {
            case -1:
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void RegisterhyperlinkClick(View v)
    {
        intent = new Intent(this,Register.class);
        startActivityForResult(intent, 1);//made entirely by aditya bhatt.
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.layout || v.getId() == R.id.button6)
        {
            l2.setVisibility(View.INVISIBLE);
            l2.setVisibility(View.GONE);
            l1.setVisibility(View.VISIBLE);
        }
        else if(v.getId()==R.id.button2)
        {
            startActivity(new Intent(this,Register.class));
        }
    }

    public void LayoutonClick(View v)
    {
        l1.setVisibility(View.INVISIBLE);
        l1.setVisibility(View.GONE);
        l2.setVisibility(View.VISIBLE);
    }
    public void cancelOnClick(View v)
    {//made entirely by aditya bhatt.
        l1.setVisibility(View.INVISIBLE);
        l1.setVisibility(View.GONE);
        l2.setVisibility(View.VISIBLE);
    }

    public void ForgotPasswordClick(View v)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://52.33.103.163/bmc/ForgotPassword.aspx?user=" + Dashboard.user));
        startActivity(browserIntent);
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mpr_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will//made entirely by aditya bhatt.
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            onBackPressed();
            return true;
        }

        if (id == R.id.action_logout) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            //super.onDestroy();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }
//made entirely by aditya bhatt.
    public MyJavaScriptInterface ReadJavaScriptLocalStorage()
    {
        WebView l_wv = new WebView(this);//(WebView) findViewById(R.id.webView);
        l_wv.getSettings().setJavaScriptEnabled(true);
        l_wv.getSettings().setDomStorageEnabled(true);
        final MyJavaScriptInterface javaInterface = new MyJavaScriptInterface();
        l_wv.addJavascriptInterface(javaInterface, "HTMLOUT");

        l_wv.setWebChromeClient(new WebChromeClient());

        l_wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                EditText l_advCode, l_usrname, l_passwd;
                l_passwd = (EditText) findViewById(R.id.password);
                l_usrname = (EditText) findViewById(R.id.userName);
                l_advCode = (EditText) findViewById(R.id.advCode);
                if (javaInterface.p_advCode.isEmpty() == fasplse) {
                    l_advCode.setText(javaInterface.p_advCode);
                    l_usrname.setText(javaInterface.p_advName);
                    l_passwd.setText(javaInterface.p_passwd);
                }//made entirely by aditya bhatt.
                //view.loadUrl("javascript:callMe()");
                //view.loadUrl("javascript:( function () { var advc = window.localStorage['advcode'];" +
                //        "var advn = window.localStorage['advname']; " +
                //        "var advp = window.localStorage['advpasswd']; window.HTMLOUT.someCallback(advn, advn, advp);" +
                //        " } ) ()");
            }
        });
        //l_wv.loadUrl("file:///android_asset/getLocal.html");
        l_wv.loadUrl("http://fno.mprofit.in/MprMobile/getLocal.html");

        return javaInterface;
    }*/


}

