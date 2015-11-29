package com.Barclay.adapters;

import java.util.ArrayList;

import com.Barclay.classess.Mytransaction;
import com.Barclay.classess.offerList;
import com.Barclay.hackatron.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyTransactionAdapter extends BaseAdapter {
	Activity activity;
	ArrayList<Mytransaction> array;
	LayoutInflater minflate; 
	public MyTransactionAdapter(Activity myTransactionactivity, ArrayList<Mytransaction> arr) {
		activity=myTransactionactivity;
		this.array=arr;
		minflate=LayoutInflater.from(activity);
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
			convertView=minflate.inflate(R.layout.single_mytransaction_view, parent, false);
			TextView sender=(TextView) convertView.findViewById(R.id.tv_sender);
			TextView receiver=(TextView) convertView.findViewById(R.id.tv_receiver);
			TextView amount=(TextView) convertView.findViewById(R.id.tv_Amt);
			TextView date=(TextView) convertView.findViewById(R.id.tv_date);
			try {
				sender.setText(array.get(position).sender);
				receiver.setText(array.get(position).receiver);
				amount.setText(array.get(position).Amount);
				date.setText(array.get(position).date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return convertView;
	}

}
