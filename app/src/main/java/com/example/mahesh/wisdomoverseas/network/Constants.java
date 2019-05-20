package com.example.mahesh.wisdomoverseas.network;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
    public static final String DATEFORMAT="yyyy-MM-dd'T'HH:mm:ss";
    public static final String HISTORYDATEFORMAT="yyyy-MM-dd";
    public static final String PENDING="Pending Lead";
    public static final String PROCESSING="Processing";
    public static final String JOINED="Joined";
    public static final int CAMERA_REQUEST = 1888;
    public  static final String status = "";


    public static String convertStringToDate(String date){
        String date1=null;
        if(date!=null && !date.isEmpty()){
            String[] splittedDate=date.split("T");
            Log.e("splittedDate",""+splittedDate);
            if(splittedDate!=null && splittedDate.length>0 && splittedDate[0]!=null){
                String dateformat=splittedDate[0];
                Log.e("dateformat",""+dateformat);

                if(dateformat!=null &&!dateformat.isEmpty()){
                    try {
                        Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(dateformat);
                        Log.e("date2",""+date2);
                        if(date2!=null){
                            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                            date1=df.format(date2);
                            Log.e("date1",""+date1);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        }


        return date1;
    }



}
