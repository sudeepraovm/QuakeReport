package com.example.android.quakereport;


public class Earthquake {
    private String mMagnitude;
    private String mLocation;
    private long mDate;
    private String murl;

    public Earthquake(String Mag , String Loc , long Date,String url){
        mMagnitude=Mag;
        mLocation=Loc;
        mDate=Date;
        murl=url;
    }
    public String getMag(){ return mMagnitude;}
    public String getLoc(){ return mLocation;}
    public long getDate(){ return mDate;}
    public String getURL(){ return murl;}
}
