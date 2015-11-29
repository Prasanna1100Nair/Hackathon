package com.Barclay.global;

import android.app.Application;

public class GlobalApplication extends Application {
	public static String USERNAME;
	public static String PREFS_NAME = "MyPrefsFile";
	public static String PREF_USERNAME = "username";
	public static String  STRING_PLEASE_WAIT="Please Wait..";
	public static String LoginCheck() {
	//	String checkUrl = "http://192.168.0.108:8080/Payment/Login";
		String checkUrl = "http://192.168.43.23:8080/Payment/Login";
		return checkUrl;
	}
	public static String ServiceProvider() {
		String checkUrl = "https://restcountries.eu/rest/v1/callingcode";
		return checkUrl;
	}
	public static String TransferBalance() {
		String checkUrl = "http://192.168.43.23:8080/Payment/Transfer";
		return checkUrl;
	}
	public static String LastFiveTransaction() {
		String checkUrl = "http://192.168.43.23:8080/Payment/LastFiveTransaction";
		return checkUrl;
	}

}
