package com.humorstech.respyr.reading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.profile.user.SelectProfile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SelectUser extends AppCompatActivity {

    private GridView gridView;
    private GridAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        StatusBarColor statusBarColor= new StatusBarColor(SelectUser.this);
        statusBarColor.setDarkColor(getResources().getColor(R.color.select_primary));


        gridView = findViewById(R.id.profileGridView);

        // Create dummy data items
        List<ProfileItem> dataItems = createDataItems();
        // Create and set the adapter
        adapter = new GridAdapter(this, SelectUser.this, dataItems);
        gridView.setAdapter(adapter);



        Button buttonAddNewProfile =findViewById(R.id.button_add_new_profile);
        buttonAddNewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserForm.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private List<ProfileItem> createDataItems() {
        List<ProfileItem> dataItems = new ArrayList<>();
        Dialogs.showLoadingDialog(SelectUser.this, "Please wait");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://humorstech.com//humors_app/app_final/fetch_test_data.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Dialogs.hideLoadingDialog();
                String response = new String(responseBody);

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        // Get the current JSONObject from the array
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        dataItems.add(
                                new ProfileItem(jsonObject.getString("name"),
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("gender"),
                                        jsonObject.getString("age")));


                    }

                    gridView.setAdapter(adapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Dialogs.hideLoadingDialog();
            }

        });


        return dataItems;
    }



}