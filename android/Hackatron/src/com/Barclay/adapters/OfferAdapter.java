package com.Barclay.adapters;

import java.io.InputStream;
import java.util.ArrayList;

import com.Barclay.classess.offerList;
import com.Barclay.hackatron.R;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
public class OfferAdapter extends BaseAdapter {
	Activity activity;
	ArrayList<offerList> array;
	LayoutInflater minflate; 
	ImageView icon;
	public com.Barclay.utils.ImageLoader imageLoader; 
	public OfferAdapter(Activity offersActivity, ArrayList<offerList> array) {
		activity=offersActivity;
		this.array=array;
		minflate=LayoutInflater.from(activity);
		imageLoader = new com.Barclay.utils.ImageLoader(activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return array.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView=minflate.inflate(R.layout.list_single_element, parent, false);
			LinearLayout singlelayout=(LinearLayout) convertView.findViewById(R.id.ll_layout);
			TextView companyNM=(TextView) convertView.findViewById(R.id.tv_name);
			TextView company_info=(TextView) convertView.findViewById(R.id.tv_address);
			TextView rating=(TextView) convertView.findViewById(R.id.tv_rate);
			 icon=(ImageView) convertView.findViewById(R.id.img_icon);
			 try {
				companyNM.setText(array.get(position).companyNm);
				company_info.setText("Articles about companies in Wikipedia are generally fairly shallow. The aim of this Project is to enhance ");
				rating.setText(array.get(position).dicountRate);
				String temp=array.get(position).imageid;
				convertView.setTag(companyNM);
				int Test=position%2;
				if (Test==0) {
					//singlelayout.setBackgroundDrawable(R.drawable.item_background2);
					singlelayout.setBackgroundDrawable( activity.getResources().getDrawable(R.drawable.item_background) );
				}
				else
				{
					singlelayout.setBackgroundDrawable( activity.getResources().getDrawable(R.drawable.item_background2) );
				}
				if (temp.equals("1")) {
					// android.resource://com.your.packagename/" + R.raw.radiocd5
					// imageLoader.DisplayImage( "android.resource://com.Barclay.hackatron/" + R.raw.amazon, icon);
					 InputStream ims = activity.getAssets().open("amazon.png");
					    // load image as Drawable
					    Drawable d = Drawable.createFromStream(ims, null);
					    // set image to ImageView
					    icon.setImageDrawable(d);
				}
				else if (temp.equals("2")) {
					//imageLoader.DisplayImage( "android.resource://com.Barclay.hackatron/" + R.raw.ebay, icon);
					InputStream ims = activity.getAssets().open("ebay.png");
				    // load image as Drawable
				    Drawable d = Drawable.createFromStream(ims, null);
				    // set image to ImageView
				    icon.setImageDrawable(d);
				}
				else if (temp.equals("3")) {
					//imageLoader.DisplayImage( "android.resource://com.Barclay.hackatron/" + R.raw.alibaba, icon);
					InputStream ims = activity.getAssets().open("alibaba.png");
				    // load image as Drawable
				    Drawable d = Drawable.createFromStream(ims, null);
				    // set image to ImageView
				    icon.setImageDrawable(d);
				}
			 } catch (Exception e) {
				e.printStackTrace();
			}
		}
		return convertView;
	}
}
