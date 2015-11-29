package com.Barclay.hackatron;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Barclay.adapters.MyTransactionAdapter;
import com.Barclay.classess.Mytransaction;
import com.Barclay.global.GlobalApplication;
import com.Barclay.global.HttpRequest;
import com.nirhart.parallaxscroll.views.ParallaxListView;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyTransactionactivity extends Activity {
	TextView UserName;
	String username;
	ParallaxListView listView;
	MyTransactionAdapter adapter;
	ArrayList<Mytransaction> arr=new ArrayList<Mytransaction>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_transaction_view);
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			username = extras.getString("UserName");
		}
		Init();
	}
	private void Init() {
		try {
			UserName=(TextView) findViewById(R.id.tv_UserName);
			UserName.setText("Welcome back "+username);
			listView=(ParallaxListView) findViewById(R.id.list_view);
			
			AsynMyTransaction task=new AsynMyTransaction("9167671065",MyTransactionactivity.this);
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();		}
		
		
	}
	public class AsynMyTransaction extends AsyncTask<String, Void, String> {

		String mServerResponse = "";
		Context mContext;
		String mUserName, mUserMobileNo, mAmt;
		int mDialogueBoxHeight, mDialogueBoxWidth;
		private ProgressDialog mProgress;


		public AsynMyTransaction(String UserName,Context con) {
			mUserName = UserName;
			mContext=con;
		}


		@Override
		protected void onPreExecute() {
			Log.d("in", "preExecute");
			mProgress = new ProgressDialog(mContext);
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
								.get(GlobalApplication.LastFiveTransaction(),true,"Username",mUserName)
								.body();
				Log.d("responce Status::", "::" + mServerResponse.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mServerResponse;
		}

		@Override
		protected void onPostExecute(String result) {
			mProgress.dismiss();
			try {
				Log.e("result", "::" + result);
				JSONObject jobjj=new JSONObject(result);
				JSONArray jArray=jobjj.getJSONArray("transactions");
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject jobj=jArray.getJSONObject(i);
					Mytransaction obj=new Mytransaction();
					obj.sender=jobj.getString("Sender");
					obj.receiver=jobj.getString("Receiver");
					obj.date=jobj.getString("Timpestamp");
					obj.Amount=jobj.getString("Amount");
					arr.add(obj);
							
				}
				adapter=new MyTransactionAdapter(MyTransactionactivity.this,arr);
				listView.setAdapter(adapter);	
			} catch (Exception e) {
				e.printStackTrace();

			}

		}

	}
}
