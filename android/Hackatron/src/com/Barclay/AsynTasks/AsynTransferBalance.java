package com.Barclay.AsynTasks;

import org.json.JSONObject;

import com.Barclay.global.GlobalApplication;
import com.Barclay.global.HttpRequest;
import com.Barclay.hackatron.LandingActivity;
import com.Barclay.hackatron.R;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AsynTransferBalance extends AsyncTask<String, Void, String> {

	String mServerResponse = "";
	Context mContext;
	String mUserName, mUserMobileNo, mAmt;
	int mDialogueBoxHeight, mDialogueBoxWidth;
	private ProgressDialog mProgress;

	public AsynTransferBalance(String UserName, String UserMobileNo, String Amt,Context con) {
		mUserName = UserName;
		mUserMobileNo = UserMobileNo;
		mAmt = Amt;
		mContext=con;
		 DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
			mDialogueBoxWidth = metrics.widthPixels;
			mDialogueBoxHeight = metrics.heightPixels;

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
							.get(GlobalApplication.TransferBalance(),true,"Sender",mUserName,"Receiver",mUserMobileNo,"Amount",mAmt)
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
			JSONObject jobj=new JSONObject(result);
			String status=jobj.getString("status");
			String balance=jobj.getString("balance");
			String Username=jobj.getString("name");
			Log.e("status", "::"+status) ;
			CustomDialog(mContext,status,balance,Username);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	private void CustomDialog(final Context con, String error,final String balance,final String Username) {
	final Dialog mCustomDialog = new Dialog(con);
	mCustomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	mCustomDialog.setContentView(R.layout.alert);
	mCustomDialog.setCanceledOnTouchOutside(false);
	Button dialogOk = (Button) mCustomDialog.findViewById(R.id.bt_Ok);
	TextView message = (TextView) mCustomDialog.findViewById(R.id.txt_serverMessage);
	message.setText(error);
	ImageView closeDialogue = (ImageView) mCustomDialog.findViewById(R.id.img_Close);
	mCustomDialog.show();
	 mCustomDialog.getWindow().setLayout((int) (mDialogueBoxWidth /
	 1.5), LayoutParams.WRAP_CONTENT);
	closeDialogue.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent in=new Intent(con,LandingActivity.class);
			in.putExtra("Balance", balance);
			in.putExtra("UserName", Username);
			con.startActivity(in);
			mCustomDialog.dismiss();
		}
	});
	dialogOk.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent in=new Intent(con,LandingActivity.class);
			in.putExtra("Balance", balance);
			in.putExtra("UserName", Username);
			con.startActivity(in);
			mCustomDialog.dismiss();
			
		}
	});

}
}
