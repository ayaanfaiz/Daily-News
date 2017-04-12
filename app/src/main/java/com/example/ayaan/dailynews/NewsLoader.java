package com.example.ayaan.dailynews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by AYAAN on 4/11/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    String mUrl;
    public NewsLoader(Context context,String url) {
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading(){
        forceLoad();
    }
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        List<News> news = NewsJson.fetchNewsData(mUrl);
        return news;
    }
}
