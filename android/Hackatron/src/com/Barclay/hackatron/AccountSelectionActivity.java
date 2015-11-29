package com.Barclay.hackatron;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Barclay.AsynTasks.AsynTransferBalance;
import com.Barclay.global.GlobalApplication;
import com.Barclay.global.HttpRequest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AccountSelectionActivity extends Activity implements OnClickListener {
	Button contacts;
	private static final int PICK_CONTACT = 1045;
	EditText phoneNumber,money;
	TextView CountryName,UserName,mbalance;
	String Number,username="",balance="";
			Button Submit;
			int mDialogueBoxHeight, mDialogueBoxWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_activity);
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			username = extras.getString("UserName");
			balance=extras.getString("Balance");
		}
		Init();
	}

	private void Init() {
		phoneNumber=(EditText) findViewById(R.id.edt_phonenumber);
		contacts=(Button) findViewById(R.id.bt_Contacts);
		CountryName=(TextView) findViewById(R.id.tv_CountryName);
		UserName=(TextView) findViewById(R.id.tv_UserName);
		mbalance=(TextView) findViewById(R.id.tv_balance);
		Submit=(Button) findViewById(R.id.bt_Submit);
		money=(EditText) findViewById(R.id.edt_money);
		UserName.setText("Welocome "+username);
		mbalance.setText("Your balance is "+balance);
		contacts.setOnClickListener(this);
		Submit.setOnClickListener(this);
		CountryName.setVisibility(View.GONE);
		 DisplayMetrics metrics =getResources().getDisplayMetrics();
			mDialogueBoxWidth = metrics.widthPixels;
			mDialogueBoxHeight = metrics.heightPixels;
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		 super.onActivityResult(reqCode, resultCode, data);

		 switch (reqCode) {
		 case (PICK_CONTACT) :
		   if (resultCode == Activity.RESULT_OK) {

		     Uri contactData = data.getData();
		     Cursor c =  managedQuery(contactData, null, null, null, null);
		     if (c.moveToFirst()) {


		         String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

		         String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

		           if (hasPhone.equalsIgnoreCase("1")) {
		          Cursor phones = getContentResolver().query( 
		                       ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, 
		                       ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, 
		                       null, null);
		             phones.moveToFirst();
		              String cNumber = phones.getString(phones.getColumnIndex("data1"));
		             System.out.println("number is:"+cNumber);
		             cNumber=cNumber.replace(" ", "").replace("-", "");
		             Log.e("cNumber","::"+cNumber);
		             String countryCode="";	
		             if(cNumber.length()==13)
		             {
		            	
		            	  Number =cNumber.substring(3,(cNumber.length()));
		            	  countryCode= cNumber.substring(1, 3);
		            	 Log.e("countryCode","::"+countryCode);
		            	 Log.e("Number","::"+Number);
		             }
		             if(cNumber.length()==12)
		             {
		            	 char test=cNumber.charAt(0);
		            	 if (test==cNumber.charAt(0)) {
		            		 Number=cNumber.substring(2,(cNumber.length()));
			            	   countryCode= cNumber.substring(1,3);
						}
		            	 else
		            	 {
		            		 
		            	 
		            	  Number=cNumber.substring(1,(cNumber.length()));
		            	   countryCode= cNumber.substring(0,1);
		            	 }
		            	
		            	
		            	 Log.e("countryCode","::"+countryCode);
		            	 Log.e("Number","::"+Number);
		             }
		             else if(cNumber.length()==11)
		             {
		            	 char test=cNumber.charAt(0);
		            	 if (test==cNumber.charAt(0)) {
		            		 Number=cNumber.substring(1,(cNumber.length()));
			            	   countryCode= cNumber.substring(1,3);
						}
		            	 else
		            	 {
		            		 
		            	 
		            	  Number=cNumber.substring(1,(cNumber.length()));
		            	   countryCode= cNumber.substring(0,1);
		            	 }
			            	 Log.e("countryCode","::"+countryCode);
		            	 Log.e("in 11",Number);
		             }
		           
		           //  tv.setText(Number);
		             phoneNumber.setText(Number);
		             if (countryCode.length()>1) {
		            	// AsynServiceProvider task=new AsynServiceProvider(countryCode);
			           //  task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					}
		             
		           }
		         String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		         Log.e("name",name);


		     }
		     
		   }
		   break;
		 }
		 }

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.bt_Contacts) {
			Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
			  startActivityForResult(intent, PICK_CONTACT);
		}
		else if (v.getId()==R.id.bt_Submit) {
			int mainbal=Integer.parseInt(balance);
			String transfer=money.getText().toString().trim();
			int transferBal=Integer.parseInt(transfer);
			String PhonNumber=phoneNumber.getText().toString().trim();
			
			if(transferBal<=mainbal)
			{
				AsynTransferBalance task=new AsynTransferBalance("9167671065",PhonNumber , transfer, AccountSelectionActivity.this);
				task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
			else
			{
				String error="The Enter Amout is Greater then what you have in your account";
				CustomDialog(AccountSelectionActivity.this,error);
			}
		}
		
	}
	private void CustomDialog(final Context con, String error) {
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
			mCustomDialog.dismiss();
		}
	});
	dialogOk.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mCustomDialog.dismiss();
			
		}
	});

}
	public class AsynServiceProvider extends AsyncTask<String, Void, String>{
		String mServerResponse = "";
		Context mContext;
		String mUserName, mCountryCode;
		public AsynServiceProvider( String countryCode) {
			mCountryCode=countryCode;
		}
		@Override
		protected void onPreExecute() {
			Log.d("in", "preExecute");
			
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				mServerResponse = HttpRequest
						.get(GlobalApplication.ServiceProvider()+"/"+mCountryCode,true)
						.body();
				Log.d("responce Status::", "::" + mServerResponse.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return mServerResponse;
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			try {
				Log.e("result", "::" + result);	
				JSONArray jArray=new JSONArray(result);
				JSONObject jobj=jArray.getJSONObject(0);
				String countryName=jobj.getString("name");
				Log.e("countryName", "::"+countryName);
				CountryName.setVisibility(View.VISIBLE);
				CountryName.setText(countryName);
				
			} catch (Exception e) {
				e.printStackTrace();
				CountryName.setVisibility(View.GONE);
				
			}

		}

	}
}
