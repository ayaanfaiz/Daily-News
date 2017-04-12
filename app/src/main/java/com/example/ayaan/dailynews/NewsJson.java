package com.example.ayaan.dailynews;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.N;
import static com.example.ayaan.dailynews.NewsJson.readString;

/**
 * Created by AYAAN on 4/11/2017.
 */

public class NewsJson{
    public static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
            Log.e(stringUrl,"Url Fetched:-");
        } catch (MalformedURLException e) {
            Log.e("URL creation","Error");
        }
        return url;
    }
    public static String readString(InputStream inputStream)throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        if(inputStream != null){
            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String l = bufferedReader.readLine();
            while (l!= null){
                stringBuilder.append(l);
                l=bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }
    public static String httpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if(jsonResponse == null){
            return jsonResponse;
        }
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(20000);
            connection.connect();
            int responseCode;
            responseCode = connection.getResponseCode();
            if(responseCode == 200){
                inputStream = connection.getInputStream();
                jsonResponse = readString(inputStream);
            }
        }
        catch (IOException e){
            Log.e("http request","error");
        }
        finally {
            if(connection != null){
                connection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    public static List<News> extractNewsData(String jsonResponse){
        List<News> news = new ArrayList<>();
        try {
            JSONObject base = new JSONObject(jsonResponse);
            JSONObject response = base.getJSONObject("response");
            JSONArray resultArray = response.getJSONArray("results");
            for(int i=0;i<resultArray.length();i++){
                JSONObject result = resultArray.getJSONObject(i);
                String webTitle = result.optString("webTitle");
                String webUrl = result.optString("webUrl");
                String sectionName = result.optString("sectionName");
                Log.e(webTitle,sectionName);
                News newss = new News(webTitle,sectionName,webUrl);
                news.add(newss);
            }

        } catch (JSONException e) {
            Log.e("Json Failed","Ayaan Please try again");
        }
        return news;
    }
    public static List<News> fetchNewsData(String urlFromLoader){
        URL url = createUrl(urlFromLoader);
        String jsonResponse = null;
        try {
            jsonResponse = httpRequest(url);
        } catch (IOException e) {
            Log.e("httpRequest failed","---");
        }
        List<News> news = extractNewsData(jsonResponse);
        return news;
    }
}
