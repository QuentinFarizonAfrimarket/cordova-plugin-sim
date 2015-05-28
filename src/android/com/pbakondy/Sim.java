// MCC and MNC codes on Wikipedia
// http://en.wikipedia.org/wiki/Mobile_country_code

// Mobile Network Codes (MNC) for the international identification plan for public networks and subscriptions
// http://www.itu.int/pub/T-SP-E.212B-2014

// class TelephonyManager
// http://developer.android.com/reference/android/telephony/TelephonyManager.html

package com.pbakondy;

import android.app.Activity;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.provider.Settings;
import android.content.Intent;
import android.telephony.ServiceState;

public class Sim extends CordovaPlugin {

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
    if (action.equals("getSimInfo")) {
		try {
			Context context = this.cordova.getActivity().getApplicationContext();

			TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			String phoneNumber = manager.getLine1Number();
			String countryCode = manager.getSimCountryIso();
			String simOperator = manager.getSimOperator();
			String carrierName = manager.getSimOperatorName();

			int callState = manager.getCallState();
			int dataActivity = manager.getDataActivity();
			int networkType = manager.getNetworkType();
			int phoneType = manager.getPhoneType();
			int simState = manager.getSimState();

			String mcc = "";
			String mnc = "";

			if (simOperator.length() >= 3) {
			mcc = simOperator.substring(0, 3);
			mnc = simOperator.substring(3);
			}


			JSONObject result = new JSONObject();

			result.put("carrierName", carrierName);
			result.put("countryCode", countryCode);
			result.put("mcc", mcc);
			result.put("mnc", mnc);
			result.put("phoneNumber", phoneNumber);

			result.put("callState", callState);
			result.put("dataActivity", dataActivity);
			result.put("networkType", networkType);
			result.put("phoneType", phoneType);
			result.put("simState", simState);

			callbackContext.success(result);
			return true;
		} catch (final Exception e) {
			callbackContext.error(e.getMessage());
		}
		
    } else if (action.equals("getAirplaneModeStatus")){
		try {
			boolean isEnabled = Settings.System.getInt(this.cordova.getActivity().getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
			callbackContext.success(new JSONObject().put("enabled", isEnabled));
			return true;
		} catch (final Exception e) {
			callbackContext.error(e.getMessage());
		}
    } else if(action.equals("enableAirplaneMode")){
		try {
			Settings.System.putInt(this.cordova.getActivity().getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 1);
			Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
			intent.putExtra("state", 1);
			this.cordova.getActivity().sendBroadcast(intent);
			
			callbackContext.success(new JSONObject().put("enabled", true));
			return true;
		} catch (final Exception e) {
			callbackContext.error(e.getMessage());
		}
	} else if(action.equals("disableAirplaneMode")){
		try {
			Settings.System.putInt(this.cordova.getActivity().getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
			Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
			intent.putExtra("state", 0);
			this.cordova.getActivity().sendBroadcast(intent);
			
			callbackContext.success(new JSONObject().put("enabled", false));
			return true;
		} catch (final Exception e) {
			callbackContext.error(e.getMessage());
		}
	} else if(action.equals("toggleAirplaneMode")){
		try {
			// read the airplane mode setting
			boolean isEnabled = Settings.System.getInt(this.cordova.getActivity().getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
		
			// toggle airplane mode
			Settings.System.putInt(this.cordova.getActivity().getContentResolver(), Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
		
			// Post an intent to reload
			Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
			intent.putExtra("state", !isEnabled);
			this.cordova.getActivity().sendBroadcast(intent);
			
			boolean endisEnabled = Settings.System.getInt(this.cordova.getActivity().getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
			
			callbackContext.success(new JSONObject().put("enabled", endisEnabled));
			return true;
		} catch (final Exception e) {
			callbackContext.error(e.getMessage());
		}
	} else if (action.equals("getServiceState")){
		try {
			ServiceState serviceState = new ServiceState();
			String phonestate;

			switch(serviceState.getState()) {
				case ServiceState.STATE_EMERGENCY_ONLY: 
					phonestate = "STATE_EMERGENCY_ONLY";
					break;
				case ServiceState.STATE_IN_SERVICE: 
					phonestate = "STATE_IN_SERVICE";
					break;
				case ServiceState.STATE_OUT_OF_SERVICE: 
					phonestate = "STATE_OUT_OF_SERVICE"; 
					break;
				case ServiceState.STATE_POWER_OFF: 
					phonestate = "STATE_POWER_OFF"; 
					break;
				default:
					phonestate = "Unknown";
					break;
			}   
			
			callbackContext.success(new JSONObject().put("state", phonestate));
			return true;
		} catch (final Exception e) {
			callbackContext.error(e.getMessage());
		}
	
	
	}

	
	return false;
	
  }
}
