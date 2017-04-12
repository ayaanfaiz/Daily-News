package com.example.ayaan.dailynews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    private EditText enteredtext;
    private Button search;
    private NewsAdapter newsAdapter;
    private LoaderManager loaderManager;
    private String urltosearch;
    private TextView noInternet;
    private TextView noResult;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enteredtext = (EditText)findViewById(R.id.enteredtext);
        progressBar =(ProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        search = (Button)findViewById(R.id.search);
        noInternet = (TextView)findViewById(R.id.nointernet);
        noInternet.setVisibility(View.GONE);
        loaderManager = getLoaderManager();
        noResult = (TextView)findViewById(R.id.noresult);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        ListView news = (ListView)findViewById(R.id.list);
        newsAdapter = new NewsAdapter(this,new ArrayList<News>());
        if(networkInfo != null && networkInfo.isConnected()){
        loaderManager.initLoader(1,null,NewsActivity.this);
        noInternet.setVisibility(View.GONE);
            news.setAdapter(newsAdapter);

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noResult.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    String s;
                    s=enteredtext.getText().toString().trim();
                    Log.e("onClick","---");
                    urltosearch = "http://content.guardianapis.com/search?q="+s+"&api-key=test";
                    loaderManager.restartLoader(1,null,NewsActivity.this);
                }
            });

        }else {
            noInternet.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        //news.setEmptyView(noResult);

    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Log.e("Loadeer","on Create");
        if (newsAdapter!=null)
        {
            newsAdapter.clear();
        }
        return new NewsLoader(this,urltosearch);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        progressBar.setVisibility(View.GONE);
        newsAdapter.clear();

        if(newsAdapter != null && news!= null){
            newsAdapter.addAll(news);
            if(newsAdapter.isEmpty()){
                noResult.setVisibility(View.VISIBLE);
            }else {
                noResult.setVisibility(View.GONE);
            }
        }

       // newsAdapter.clear();
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }
}
