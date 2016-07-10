package com.carrymybag.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
	// LogCat tag
	private static String TAG = SessionManager.class.getSimpleName();

	// Shared Preferences
	SharedPreferences pref;

	Editor editor;
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Shared preferences file name
	private static final String PREF_NAME = "CarrymybagLogin";
	
	private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

	// Shared preferences file name
	private static final String PREF_NAME_Wel = "welcome";

	private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public void setLogin(boolean isLoggedIn) {

		editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

		// commit changes
		editor.commit();

		Log.d(TAG, "User login session modified!");
	}
	
	public boolean isLoggedIn(){
		return pref.getBoolean(KEY_IS_LOGGED_IN, false);
	}

	public void setFirstTimeLaunch(boolean isFirstTime) {
		editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
		editor.commit();
	}

	public boolean isFirstTimeLaunch() {
		return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
	}
}
