package com.Barclay.hackatron;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LandingActivity extends Activity implements OnClickListener {
	Button recharge,billPayment,fundTransfer,Ticketing,CardLoans,offer,MyTransactions;
	String username="",balance="";
	TextView UserName,mbalance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing_activity);
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			username = extras.getString("UserName");
			balance=extras.getString("Balance");
		}
		Init();
	}

	private void Init() {
		UserName=(TextView) findViewById(R.id.tv_UserName);
		mbalance=(TextView) findViewById(R.id.tv_balance);
		recharge=(Button) findViewById(R.id.bt_Recharge);
		billPayment=(Button) findViewById(R.id.bt_BillPayment);
		fundTransfer=(Button) findViewById(R.id.bt_FundTransfer);
		Ticketing=(Button) findViewById(R.id.bt_Ticketing);
		CardLoans=(Button) findViewById(R.id.bt_CArdsLoan);
		offer=(Button) findViewById(R.id.bt_offers);
		MyTransactions=(Button) findViewById(R.id.bt_MyTransaction);
		recharge.setOnClickListener(this);
		billPayment.setOnClickListener(this);
		fundTransfer.setOnClickListener(this);
		Ticketing.setOnClickListener(this);
		CardLoans.setOnClickListener(this);
		offer.setOnClickListener(this);
		MyTransactions.setOnClickListener(this);
		UserName.setText("Welocome "+username);
		mbalance.setText("Your balance is "+balance);
	}

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.bt_FundTransfer) {
			Intent in=new Intent(getApplicationContext(),AccountSelectionActivity.class);
			in.putExtra("Balance", balance);
			in.putExtra("UserName", username);
			startActivity(in);
		}
		if (v.getId()==R.id.bt_offers) {
			Intent in=new Intent(getApplicationContext(),OffersActivity.class);
			in.putExtra("UserName", username);
			startActivity(in);
		}
		if (v.getId()==R.id.bt_MyTransaction) {
			Intent in=new Intent(getApplicationContext(),MyTransactionactivity.class);
			in.putExtra("UserName", username);
			startActivity(in);
		}
		
	}
}
