package com.Barclay.hackatron;


import org.json.JSONObject;

import com.Barclay.global.GlobalApplication;
import com.Barclay.global.HttpRequest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HackatronMainActivity extends Activity  implements OnClickListener{
EditText mUsername,mPassword;
TextView mWrongPassword;
Button mSubmit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hackatron_main);
		Init();
	}

	private void Init() {
		mUsername=(EditText) findViewById(R.id.edt_userNm);
		mPassword=(EditText) findViewById(R.id.edt_Passwrd);
		mSubmit=(Button) findViewById(R.id.bt_Submit);
		mWrongPassword=(TextView) findViewById(R.id.tv_wrongPassowrd);
		mSubmit.setOnClickListener(this);
		mWrongPassword.setVisibility(View.GONE);
		SharedPreferences pref = getSharedPreferences(GlobalApplication.PREFS_NAME, MODE_PRIVATE);
		String username = pref.getString(GlobalApplication.PREF_USERNAME, null);
		mUsername.setText(username);
		mUsername.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				clearServerMessage();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		mPassword.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				clearServerMessage();
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	protected void clearServerMessage() {
		
		mWrongPassword.setVisibility(View.GONE);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hackatron_main, menu);
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

	@Override
	public void onClick(View v) {
		int len1=mUsername.length();
		int len2=mPassword.length();
	
		if(len1>1 && len2>1)
		{
			String userName=mUsername.getText().toString().trim();
			String password=mPassword.getText().toString().trim();
			AsynLogin task=new AsynLogin(userName,password);
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else
		{
			mWrongPassword.setVisibility(View.VISIBLE);
			mWrongPassword.setText("Please Enter the Credentials");
		}
		
	}
	public class AsynLogin extends AsyncTask<String, Void, String>{
		String mServerResponse = "";
		Context mContext;
		String mUserName, mPassword;
		private ProgressDialog mProgress;
		public AsynLogin(String userName, String password) {
			mUserName=userName;
			mPassword=password;
			
		}
		@Override
		protected void onPreExecute() {
			Log.d("in", "preExecute");
			mProgress = new ProgressDialog(HackatronMainActivity.this);
			mProgress.setTitle(GlobalApplication.STRING_PLEASE_WAIT);
			mProgress.setCancelable(false);
			mProgress.setCanceledOnTouchOutside(false);
			mProgress.show();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				mServerResponse = HttpRequest
						.get(GlobalApplication.LoginCheck(), true, "UserName", mUserName, "PassWord", mPassword)
						.body();
				Log.d("responce Status::", "::" + mServerResponse.toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return mServerResponse;
		}
		
		@Override
		protected void onPostExecute(String result) {
			mProgress.dismiss();
			try {
				Log.e("result", "::" + result);	
				JSONObject jobj=new JSONObject(result);
				String status=jobj.getString("access");
				String balance=jobj.getString("balance");
				String name=jobj.getString("name");
				Log.e("Access","::"+status );
				if (!status.equals("deny")) {
					getSharedPreferences(GlobalApplication.PREFS_NAME, MODE_PRIVATE).edit()
					.putString(GlobalApplication.PREF_USERNAME, mUserName).commit();
					Intent in=new Intent(getApplicationContext(),LandingActivity.class);
					in.putExtra("Balance", balance);
					in.putExtra("UserName", name);
					startActivity(in);
				}
				else
				{
					mWrongPassword.setVisibility(View.VISIBLE);
					mWrongPassword.setText("inValid UserId or Password");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
