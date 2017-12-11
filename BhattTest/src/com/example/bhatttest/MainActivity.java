package com.TeamGlapp.bhatttest;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.json.JSONException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract.Constants;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	private static final int CAMERA_REQUEST = 1888;
	
	
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

	public void doFileUpload(File f, int type, String data)
			throws JSONException {
		Log.e("CHAT VIEW", "Started");
		String charset = "UTF-8";
		String requestURL = "http://ultraimg.com/api/1/upload/?key=3374fa58c672fcaad8dab979f7687397";

		MultipartUtility multipart;
		try {
			multipart = new MultipartUtility(requestURL, charset);
			
				multipart.addFilePart("source", f);
			
			List<String> response = multipart.finish(); // response from server.
			for(String s : response)	Log.e("Response is", s);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("CHAT VIEW", "ON ACTIVITY CALLED");
		if (requestCode == CAMERA_REQUEST
				&& resultCode == RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			File file = Environment.getExternalStorageDirectory();
			file = new File(file, com.TeamGlapp.bhatttest.Constants.CHAT_DATA_DIRECTORY);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(file, com.TeamGlapp.bhatttest.Constants.IMAGES_DIRECTORY);
			if (!file.exists()) {
				file.mkdirs();
			}
			final File f = new File(file, random() + ".png");
			try {
				f.createNewFile();

				// Convert bitmap to byte array
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				photo.compress(CompressFormat.PNG, 50 /* ignored for PNG */, bos);
				byte[] bitmapdata = bos.toByteArray();

				// write the bytes in file
				FileOutputStream fos = new FileOutputStream(f);
				fos.write(bitmapdata);
				fos.flush();
				fos.close();
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						try {
							doFileUpload(f, com.TeamGlapp.bhatttest.Constants.IMAGE, f.getName());
						} catch (JSONException e) {
							e.printStackTrace();
						}
						return null;
					}

					protected void onPostExecute(Void result) {
						
					};
				}.execute();

			} catch (Exception e) {
				Log.e("Not done", e.getMessage());
			}

		}
		// super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.send);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Click image and get file
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
