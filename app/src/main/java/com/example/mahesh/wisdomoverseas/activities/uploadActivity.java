package com.example.mahesh.wisdomoverseas.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.mahesh.wisdomoverseas.R;

public class uploadActivity extends AppCompatActivity {




    private String postUrl = "http://wo.brandwar.in/Employee/UploadFilesData";
    private WebView webView;
    private ProgressBar progressBar;
    private ImageView imgHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.webView);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(postUrl);
        webView.setHorizontalScrollBarEnabled(false);
    }



    @Override
    public boolean onSupportNavigateUp() {

        Intent intent = new Intent(uploadActivity.this,TodayCalls.class);
        startActivity(intent);
        //onBackPressed();
        return true;
    }
}
