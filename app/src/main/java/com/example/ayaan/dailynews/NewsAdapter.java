package com.example.ayaan.dailynews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.fillAfter;
import static android.R.attr.resource;

/**
 * Created by AYAAN on 4/11/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }
    public View getView(int position, View currentView, ViewGroup parent){
        final News current = getItem(position);
        View currentList = currentView;
        if(currentList == null){
            currentList = LayoutInflater.from(getContext()).inflate(R.layout.places,parent,false);
        }
        TextView heading = (TextView)currentList.findViewById(R.id.heading);
        heading.setText(current.getHead());
        TextView details = (TextView)currentList.findViewById(R.id.details);
        details.setText(current.getDetails());
        Log.e("Adapter","---(");
        try {
            URL url = new URL(current.getUrl());
        } catch (MalformedURLException e) {
            Log.e("URL connot be fetched"," in Adapter");
        }
        currentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("onclick:", "onClick: ");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(current.getUrl()));
                view.getContext().startActivity(intent);
            }
        });
        return currentList;
    }
}
