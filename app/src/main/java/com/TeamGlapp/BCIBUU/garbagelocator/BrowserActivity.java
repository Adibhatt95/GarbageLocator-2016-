package com.TeamGlapp.BCIBUU.garbagelocator;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class BrowserActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //made entirely by aditya bhatt.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ActionBar l_ab = getActionBar();
        l_ab.setDisplayHomeAsUpEnabled(true);
        l_ab.setTitle("Change Password");
        loadChangePassword();
    }

    WebView webView = null;
    String url = null;
    private ProgressDialog progDailog;

    public static boolean checkInternetConnection(Context context) {
//made entirely by aditya bhatt.
        ConnectivityManager con_manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
    protected void loadChangePassword() {
        if (!checkInternetConnection(this)) {
            Toast.makeText(getApplicationContext(), "You are offline, Please connect to Internet !", Toast.LENGTH_LONG).show();
            onBackPressed();
            return;
        }//made entirely by aditya bhatt.
        if (progDailog == null) {
            progDailog = ProgressDialog.show(this, null, "Please wait ... ", true);//ProgressDialog.show(activity, null, "Loading, please wait ... ", true);
            progDailog.setCancelable(true);
            webView = (WebView) findViewById(R.id.webView2);
            /*webView.setLongClickable(true);
            webView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View arg0) {

                    new AlertDialog.Builder(arg0.getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Reload")
                            .setMessage("Do you wish to download synced data again, this will will take some time.\r\nDo you wish to continue?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progDailog.show();
                                    webView.loadUrl(MainActivity.p_reloadURL);
                                    //Stop the activity
                                    //android.os.Process.killProcess(android.os.Process.myPid());

                                    //System.exit(1);
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                    return false;
                }
            });*/
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);//made entirely by aditya bhatt.
            webView.getSettings().setAppCacheEnabled(true);
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setInitialScale(160);
            url = "http://52.33.103.163/bmc/ChangePassword.aspx?user=" + Dashboard.user;
            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient() {
                /*@Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    webView.setVisibility(View.GONE);
                    progDailog.show();
                    view.loadUrl(url);

                    return true;
                }*/

                @Override
                public void onPageFinished(WebView view, final String url) {

                    //String pageTitle = view.getTitle();
                    //Toast.makeText(getApplicationContext(), pageTitle, Toast.LENGTH_SHORT).show();
                    //super.onPageFinished(view, url);
                    //webView.setVisibility(View.GONE);
                    //if (url.contains("about:blank") == false) {
                    //    progDailog.dismiss();
                    //}

                    progDailog.hide();

                }
            });
        }
    }
    //made entirely by aditya bhatt.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle "up" button behavior here.
            onBackPressed();
        }
        return true;
        // return true if you handled the button click, otherwise return false.
    }
}
