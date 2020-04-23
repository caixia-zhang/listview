package com.example.listview;

import android.graphics.Bitmap;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String mUserName;
    private  String mDate;
    private int mprogress;
    private String mdate_del;

    public byte[] getMbitmap() {
        return mbitmap;
    }

    public void setMbitmap(byte[] mbitmap) {
        this.mbitmap = mbitmap;
    }

    private byte[] mbitmap;




    public String getmdata_del() {
        return mdate_del;
    }
    public void setmdata_del(String m_date_del) {
        this.mdate_del = m_date_del;
    }
    public int getProgress() {
        return mprogress;
    }

    public void setProgress(int progress) {
        this.mprogress = progress;
    }

    public String getmDate() {
        return mDate;
    }
    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
    public String getmUserName() {
        return mUserName;
    }
    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public UserInfo(String userName, String Date, int progress, String date_del, byte[] bitmapimg)
    {
        mUserName=userName;
        mDate=Date;
        mprogress=progress;
        mdate_del=date_del;
        mbitmap=bitmapimg;
    }
    public UserInfo(String userName, String Date,String date_del, byte[] bitmapimg)
    {
        mUserName=userName;
        mDate=Date;
        this.mprogress=0;
        mdate_del=date_del;
       mbitmap=bitmapimg;
    }

}
