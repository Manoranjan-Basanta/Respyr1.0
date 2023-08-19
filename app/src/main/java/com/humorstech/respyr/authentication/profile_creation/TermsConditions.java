package com.humorstech.respyr.authentication.profile_creation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;

public class TermsConditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
        StatusBarColor statusBarColor= new StatusBarColor(TermsConditions.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        WebView webView = findViewById(R.id.terms);
        webView.loadUrl("https://humorstech.com/humors_app/app_final/terms.html");

        Button buttonBack=findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}