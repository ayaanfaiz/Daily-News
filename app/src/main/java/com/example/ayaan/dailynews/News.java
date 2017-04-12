package com.example.ayaan.dailynews;

/**
 * Created by AYAAN on 4/11/2017.
 */

public class News {
    private String mHead;
    private String mDetails;
    private String mUrl;
    public News(String head,String details,String url){
        mHead = head;
        mDetails = details;
        mUrl = url;
    }
    public String getHead(){
        return mHead;
    }
    public String getDetails(){
        return mDetails;
    }
    public String getUrl(){
        return mUrl;
    }
}
