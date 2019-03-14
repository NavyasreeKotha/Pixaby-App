package com.example.navyasree.pixaby;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    EditText editText;
    Button button;
    String imgurl="https://pixabay.com/api/?key=10860748-83b5347a866cfeb8e4c85c3e2&q=";
    ArrayList<ImageModel> imageModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler);
        progressBar=findViewById(R.id.progress);
        editText=findViewById(R.id.title);
        button=findViewById(R.id.btn);
        imageModels=new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(new ImageAdapter(this,imageModels));

    }

    public void data(View view) {
        new ImageTask().execute();
    }

    public class ImageTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(imgurl+editText.getText().toString());

                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream=urlConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()){
                    return  scanner.next();
                }
                else{
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            if(s!=null){
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    Log.i("imgurl",String.valueOf(jsonObject));
                    JSONArray jsonArray=jsonObject.getJSONArray("hits");
                    Log.i("imgurl",String.valueOf(jsonArray));
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject volume = jsonArray.getJSONObject(i);
                        Log.i("imgurl",String.valueOf(volume));
                        String imageurl =volume.getString("largeImageURL");
                        imageModels.add(new ImageModel(imageurl));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            progressBar.setVisibility(View.GONE);

        }
    }
}
