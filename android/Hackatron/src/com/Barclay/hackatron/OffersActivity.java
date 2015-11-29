package com.Barclay.hackatron;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Barclay.adapters.OfferAdapter;
import com.Barclay.classess.offerList;
import com.nirhart.parallaxscroll.views.ParallaxListView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OffersActivity extends Activity {
	TextView UserName;
	String username;
	ParallaxListView listView;	
	OfferAdapter adapter;
	ArrayList<offerList> arr=new ArrayList<offerList>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offer_layout);
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			username = extras.getString("UserName");
		}
		Init();
	}

	private void Init() {
		try {
			String json = loadJSONFromAsset();
			JSONObject jsonObj = new JSONObject(json);
			JSONObject jsonMenu = jsonObj.getJSONObject("Offers");
			JSONArray jsonCatArray = jsonMenu.getJSONArray("CompanyList");
			for (int i = 0; i < jsonCatArray.length(); i++) {
				JSONObject jobj=jsonCatArray.getJSONObject(i);
				offerList obj=new offerList();
				obj.companyNm=jobj.getString("comapnyNm");
				obj.dicountRate=jobj.getString("offerDiscount");
				obj.imageid=jobj.getString("imageID");
				arr.add(obj);
			}
			UserName=(TextView) findViewById(R.id.tv_UserName);
			UserName.setText("Welcome back "+username);
			listView=(ParallaxListView) findViewById(R.id.list_view);
			adapter=new OfferAdapter(OffersActivity.this,arr);
			listView.setAdapter(adapter);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public String loadJSONFromAsset() {
		String json = null;
		try {

			InputStream is = getAssets().open("offer.json");

			int size = is.available();

			byte[] buffer = new byte[size];

			is.read(buffer);

			is.close();

			json = new String(buffer, "UTF-8");

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;

	}
}
