package com.stackmob.android.demo;

import android.content.Context;
import android.content.SharedPreferences;

public class C2DMRegistrationIDHolder {
	public static class NoStoredRegistrationIDException extends Exception {
		private static final long serialVersionUID = -4109182687918155379L;
		public NoStoredRegistrationIDException(String s) {
			super(s);
		}
	}
	private final String TAG = C2DMRegistrationIDHolder.class.getCanonicalName();
	private final String KEY = "registrationID";
	
	private Context context;
	private SharedPreferences prefs;
	public C2DMRegistrationIDHolder(Context ctx) {
		this.context = ctx;
		this.prefs = this.context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
	}
	
	public Boolean hasID() {
		return this.prefs.contains(KEY);
	}
	
	public void setID(String id) {
		SharedPreferences.Editor editor = this.prefs.edit();
		editor.putString(KEY, id);
		editor.commit();
	}
	
	public String getID() throws NoStoredRegistrationIDException {
		String id = this.prefs.getString(KEY, null);
		if(id == null) {
			throw new NoStoredRegistrationIDException("no registration ID was stored in shared prefs");
		}
		return id;
	}

}
