package com.example.remotemobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class SmsRemoteReceiver  extends BroadcastReceiver implements RemoteBuddyCommon 
{
	private static SharedPreferences password = null;
	String commaSeparatedArray[] = null;	
	
	@Override
    public void onReceive(Context context, Intent intent) 
    {
           
		AudioManager audio_mngr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

		password = context.getSharedPreferences(PREFS_NAME, 0);

		Bundle bundle = intent.getExtras();
		
		SmsMessage[] msgs = null;
		
		String commaSeparatedArray[] = null;
		String getSms = "GET_SMS", getContact = "GET_CONTACT", getCallLog = "GET_CALL_LOG";
		String delSms = "DEL_SMS", delContactKeyword = "DEL_CONTACTS", delCalendarKeyword = "DEL_CALENDAR",delCallLog="DEL_CALL_LOG";
		String smsForward="SMS_FORWARD";
		String startSiron = "START_SIRON";
		String getdeviceid = "GET_DEVICE_ID";
		String silent="SILENT";
		String general="GENERAL";
		String vibrate="VIBRATE";
		String recievedMessage = null;
		String phoneNumber = null, number = null,str="",keyword1="",keyword2="",keyword3="";
		String callNumber = null, callName = null, callDate = null, callTime = null, callType = null, isCallNew, duration = null, result = null;

		ContentResolver cr = null;
		cr = context.getContentResolver();
		
		if (bundle != null)
		{
			// ---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) 
			{
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				str += "SMS from " + msgs[i].getOriginatingAddress();
				str += " :";
				phoneNumber = msgs[i].getOriginatingAddress();
				recievedMessage = msgs[i].getDisplayMessageBody();
				str += msgs[i].getMessageBody().toString();
				str += "\n";
			}
			// ---display the new SMS message---
			Toast.makeText(context, recievedMessage, Toast.LENGTH_SHORT).show();
			String app_password  = password.getString(KEYWORD, null);
			commaSeparatedArray = recievedMessage.split(",");
			keyword1 = commaSeparatedArray[0];
			keyword2 = commaSeparatedArray[1];
		
			Toast.makeText(context, keyword1, 100).show();
			
			if (matchesKeyword(app_password, keyword1)) 
			{
				
				Toast.makeText(context, "Keyword2="+keyword2, 100).show();
				if(keyword2.equals(getContact))
				{
					keyword3=commaSeparatedArray[2];
					
						Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,null, "DISPLAY_NAME = '" + keyword3 + "'", null, null);
						if (cursor.moveToFirst()) 
						{
								String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
								Cursor phones = cr.query(Phone.CONTENT_URI, null,Phone.CONTACT_ID + " = " + contactId, null, null);	
								while (phones.moveToNext()) 
								{
										number = phones.getString(phones.getColumnIndex(Phone.NUMBER));
										int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
						
										switch (type) 
										{
											case Phone.TYPE_HOME:
																break;
											case Phone.TYPE_MOBILE:
																break;
											case Phone.TYPE_WORK:
																break;
										}
								}
								phones.close();

						}
						Toast.makeText(context, phoneNumber+":"+number, 100).show();
						SmsManager sms = SmsManager.getDefault();
						sms.sendTextMessage(phoneNumber, null, number, null, null);
			} 
				
				else if (keyword2.equals(getCallLog)) 
				{
					
					/*
					String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
					Uri callUri = Uri.parse("content://call_log/calls");

					Cursor cur = context.getContentResolver().query(callUri, null,null, null, strOrder);
					// loop through cursor
					while (cur.moveToNext()) 
					{
						CallLog callLog = new CallLog();
						callNumber = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
						callName = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
						callDate =cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.DATE)).toString();
						callType = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.TYPE));
						isCallNew = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.NEW));
						duration = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.DURATION));
					}

					result = "\n" + "Number= " + callNumber + "\n";
					result += " Name=" + callName + "\n";
					result+=" Date"+callDate+"\n";
					result+=" Duration="+duration+" sec"+"\n";

					SmsManager sms = SmsManager.getDefault();

					if(str.length()>160){
					    ArrayList<String> smses = sms.divideMessage(str);  

					        sms.sendMultipartTextMessage(phoneNumber, null,
					                    smses, null, null);
					 }
					else{
						sms.sendTextMessage(phoneNumber, null, result, null, null);
					 }
					*/
					
					 final int MISSED_CALL_TYPE = 0;
					 StringBuffer sb = new StringBuffer();
				        Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
				                null, null, null);
				        int number1 = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
				        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
				        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
				        int duration1 = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
				        sb.append("Call Details :");
				        while (managedCursor.moveToNext()) {
				            String phNumber = managedCursor.getString(number1);
				            String callType1 = managedCursor.getString(type);
				            String callDate1 = managedCursor.getString(date);
				            Date callDayTime = new Date(Long.valueOf(callDate1));
				            String callDuration = managedCursor.getString(duration1);
				            String dir = null;
				            int dircode = Integer.parseInt(callType1);
				            switch (dircode) {

				            case CallLog.Calls.OUTGOING_TYPE:
				                dir = "OUTGOING";
				                break;

				            case CallLog.Calls.INCOMING_TYPE:
				                dir = "INCOMING";
				                break;

				            case CallLog.Calls.MISSED_TYPE:
				                dir = "MISSED";
				                break;
				            }
				            sb.append("\nPhone Number:" + phNumber + " \nCall Type:"
				                    + dir + " \nCall Date:" + callDayTime
				                    + " \nCall duration in sec :" + callDuration+"\n");
				        }
				        managedCursor.close();
				        SmsManager sms = SmsManager.getDefault();
				        String str2=sb.toString();
				        
						if(str2.length()>160){
						    ArrayList<String> smses = sms.divideMessage(str2);  

						        sms.sendMultipartTextMessage(phoneNumber, null,
						                    smses, null, null);
						 }
						else{
							sms.sendTextMessage(phoneNumber, null, str2, null, null);
						 }
	
				}
				
				else if(keyword2.equals(smsForward))
				{
					keyword3=commaSeparatedArray[2];
					
					Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,null, "DISPLAY_NAME = '" + keyword3 + "'", null, null);
					if (cursor.moveToFirst()) 
					{
							String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
							Cursor phones = cr.query(Phone.CONTENT_URI, null,Phone.CONTACT_ID + " = " + contactId, null, null);	
							while (phones.moveToNext()) 
							{
									number = phones.getString(phones.getColumnIndex(Phone.NUMBER));
									int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
					
									switch (type) 
									{
										case Phone.TYPE_HOME:
															break;
										case Phone.TYPE_MOBILE:
															break;
										case Phone.TYPE_WORK:
															break;
									}
							}
							phones.close();

					}
					Toast.makeText(context, phoneNumber+":"+number, 100).show();
					
					String keyword4=commaSeparatedArray[3];
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(number, null, keyword4, null, null);
				}
				
				else if(keyword2.equals(getSms))
				{
					Uri uriSMSURI = Uri.parse("content://sms");
					Cursor cur = context.getContentResolver().query(uriSMSURI, null, null,null, null);
					String smscontent = ""; 
					 int offset = cur.getCount() - 4;
					 while (cur.moveToNext()) 
					 {            
						 if(cur.getPosition()<=2 && (null != cur.getString(2) || "" != cur.getString(2)) && (null != cur.getString(11) || "" != cur.getString(11)))
						 {                  
							 System.out.println( cur.getPosition());                  
							 smscontent += "From :" + cur.getString(2) + " : " + cur.getString(cur.getColumnIndex("body"))+"\n";            
						 }
					}
					 SmsManager sms = SmsManager.getDefault();
					 if(smscontent.length()>160){
						    ArrayList<String> smses = sms.divideMessage(smscontent);  

						        sms.sendMultipartTextMessage(phoneNumber, null,
						                    smses, null, null);
						 }
						else{
							sms.sendTextMessage(phoneNumber, null, smscontent, null, null);
						 }
					/*Toast.makeText(context, smscontent, 100).show();
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(phoneNumber, null, smscontent, null, null);*/

				}
								
				else if(keyword2.equals(startSiron))
				{
					MediaPlayer mp1;
					mp1 = MediaPlayer.create(context, R.raw.college_siren);
					mp1.start();
				}
				
				else if(keyword2.equals(getdeviceid))
				{
					//retrieve a reference to an instance of TelephonyManager
			        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			        String myDeviceId=telephonyManager.getDeviceId();
			       	String id="";
			        if (myDeviceId == null)
			       	{
			       	        	myDeviceId= "not available";
			        }
			        
			        int phoneType = telephonyManager.getPhoneType();
			        switch(phoneType)
			        {
			        		case TelephonyManager.PHONE_TYPE_NONE:
			        								 id+="NONE: " + myDeviceId;
			        
			        		case TelephonyManager.PHONE_TYPE_GSM:
			        			id+="GSM: IMEI=" + myDeviceId;
			        
			        		case TelephonyManager.PHONE_TYPE_CDMA:
			        			id+= "CDMA: MEID/ESN=" + myDeviceId;
			        
			        /*
			         *  for API Level 11 or above
			         *  case TelephonyManager.PHONE_TYPE_SIP:
			         *   return "SIP";
			         */
			        
			        default:
			        	id+="UNKNOWN: ID=" + myDeviceId;
			        }
			        SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(phoneNumber, null, id, null, null);

				}
				
				else if(keyword2.equals(silent))
				{
					audio_mngr.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				}
				
				else if(keyword2.equals(vibrate))
				{
					audio_mngr.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				}
				
				else if (keyword2.equals(general))
				{
					audio_mngr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				}
				
				else if(keyword2.equals(delContactKeyword))
				{
					ContentResolver contentResolver = context.getContentResolver();
					Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null,null, null);
					while (cursor.moveToNext()) 
					{
						String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
						Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI,lookupKey);
						contentResolver.delete(uri, null, null);
					}
				}
				
				else if(keyword2.equals(delCalendarKeyword))
				{
					String strUriEvents = "content://calendar/events";
					Uri uri_calendar = Uri.parse(strUriEvents);
					String str_column_name = "_id";
					String[] projection = { str_column_name };
					int columnIndex = 0;
					String str_id = "";
					Vector<String> vector_id = new Vector<String>();
					int delRow = 0;
					String where = "";
					try 
					{
						Cursor cursor = cr.query(uri_calendar, projection, null,null, null);
						if (cursor.moveToFirst()) 
						{
							do 
							{
								columnIndex = cursor.getColumnIndex(str_column_name);
								str_id = cursor.getString(columnIndex);
								vector_id.add(str_id);
							} while (cursor.moveToNext());
						}
						cursor.close();
						for (int i = 0; i < vector_id.size(); i++) 
						{
							str_id = vector_id.get(i);
							where = str_column_name + "=" + str_id;
							delRow = cr.delete(uri_calendar, where, null);
						}
					} 
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				else if(keyword2.equals(delCallLog))
				{
					context.getContentResolver().delete(CallLog.Calls.CONTENT_URI,null,null);
				}
				else if(keyword2.equals("WRITE"))
	            {
	            	String path="/sdcard/"+commaSeparatedArray[2];
	            	
	            	try {
	    				File myFile = new File(path);
	    				if(!myFile.exists())
	    				{
	    					myFile.createNewFile();
	        				
	    				}
	    				FileInputStream fIn = new FileInputStream(myFile);
	    				BufferedReader myReader = new BufferedReader(
	    						new InputStreamReader(fIn));
	    				String aDataRow = "";
	    				String aBuffer = "";
	    				while ((aDataRow = myReader.readLine()) != null) {
	    					aBuffer += aDataRow + "\n";
	    				}
	    				myReader.close();
	    				Toast.makeText(context, aBuffer, 100).show();
	    				
	    				for(int i=3;i<commaSeparatedArray.length;i++)
	    					aBuffer=aBuffer + " "+commaSeparatedArray[i];
	    				Toast.makeText(context, aBuffer, 100).show();	
	    				
	    				FileOutputStream fOut = new FileOutputStream(myFile);
	    				OutputStreamWriter myOutWriter = new OutputStreamWriter(
	    						fOut);
	    				myOutWriter.append(aBuffer);
	    				myOutWriter.close();
	    				fOut.close();
	    				Toast.makeText(context,
	    						"Done writing SD 'mysdfile.txt'",
	    						Toast.LENGTH_SHORT).show();
	    			} catch (Exception e) {
	    				Toast.makeText(context, e.getMessage(),
	    						Toast.LENGTH_SHORT).show();
	    			}


	            }

				
				else if(keyword2.equals("READ"))
				{
				    	String path="/sdcard/"+commaSeparatedArray[2];
		            	
		            	try {
		    				File myFile = new File(path);
		    				
		    				FileInputStream fIn = new FileInputStream(myFile);
		    				BufferedReader myReader = new BufferedReader(
		    						new InputStreamReader(fIn));
		    				String aDataRow = "";
		    				String aBuffer = "";
		    				while ((aDataRow = myReader.readLine()) != null) 
		    				{
		    					aBuffer += aDataRow + "\n";
		    				}
		    				myReader.close();
		    				SmsManager sms =SmsManager.getDefault();
		    				sms.sendTextMessage(phoneNumber, null, aBuffer, null, null);
		    				
		    				Toast.makeText(context,
		    						"Done reading SD 'mysdfile.txt'",
		    						Toast.LENGTH_SHORT).show();
		    			} catch (Exception e) {
		    				Toast.makeText(context, e.getMessage(),
		    						Toast.LENGTH_SHORT).show();
		    			}
		                
		        
				}
				
				else if(keyword2.equals(delSms))
				{
					Uri uri_sms = Uri.parse("content://sms");
					
					String str_column_name = "_id";
					String[] projection = { str_column_name };
					int columnIndex = 0;
					String str_id = "";
					Vector<String> vector_id = new Vector<String>();
					int delRow = 0;
					String where = "";
				
					try 
					{
						Cursor cursor = cr.query(uri_sms, projection, null, null,null);
						if (cursor.moveToFirst()) 
						{
							do 
							{
								columnIndex = cursor.getColumnIndex(str_column_name);
								str_id = cursor.getString(columnIndex);
								vector_id.add(str_id);
							} while (cursor.moveToNext());
						}
						cursor.close();
						for (int i = 0; i < vector_id.size(); i++) 
						{
							str_id = vector_id.get(i);
							where = str_column_name + "=" + str_id;
							delRow = cr.delete(uri_sms, where, null);
						}
					} 
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}       
    }

    private boolean matchesKeyword(String app_password, String message)
    {
            if(app_password.equals(message))
            	return true;
            else
            	return false;
    }
}