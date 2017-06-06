package com.example.vijayendra.loopjrequests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity {

    TextView text, text2, text3;
    String a, b, c;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new AsyncHttpClient();

        text = (TextView) findViewById(R.id.text);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);

        a = "status code - \n";
        b = "headers - \n";
        c = "response - \n";

        Button get_button = (Button) findViewById(R.id.get_button);
        Button post_button = (Button) findViewById(R.id.post_button);
        Button put_button = (Button) findViewById(R.id.put_button);
        Button delete_button = (Button) findViewById(R.id.delete_button);

        get_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequest();
            }
        });
        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRequest();
            }
        });
        put_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putRequest();
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRequest();
            }
        });
    }

    private void getRequest() {
        RequestParams params = new RequestParams();
        params.put("lng", -80);
        params.put("lat", 12);
        client.get("https://my-ninjas.herokuapp.com/api/ninjas", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.i("---------success------", "-------------------------------------success------------------------------------");
                text2.setText(a + String.valueOf(statusCode));
                text3.setText(b + Arrays.toString(headers));
                text.setText(c + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
                Log.i("---------fail----------", "-------------------------------------fail------------------------------------");
                text2.setText(a + String.valueOf(statusCode));
                text3.setText(b + Arrays.toString(headers));
                text.setText(c + res.toString());
            }
        });
    }

    private void postRequest() {
        String url = "https://my-ninjas.herokuapp.com/api/ninjas/";

        JSONObject geo = new JSONObject();
        try {
            geo.put("type", "point");
            geo.put("coordinates", new JSONArray("[-80, 12]"));
        } catch (JSONException e) {
            Log.d("========G E O========", e.getMessage());
        }

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("name", "chandu");
            jsonParams.put("rank", "brown belt");
            jsonParams.put("available", true);
            jsonParams.put("geometry", geo);
        } catch (JSONException e) {
            Log.d("========J error========", e.getMessage());
        }

        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            Log.d("========E error========", e.getMessage());
        }

        client.post(getApplicationContext(), url, entity, "application/json", new JsonHttpResponseHandler() {
            //format for post req = context, url, entity, contentType, new ResponseHandler
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("---------success------", "-------------------------------------success------------------------------------");
                text2.setText(a + String.valueOf(statusCode));
                text3.setText(b + Arrays.toString(headers));
                text.setText(c + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
                Log.i("---------fail----------", "-------------------------------------fail------------------------------------");
                text2.setText(a + String.valueOf(statusCode));
                text3.setText(b + Arrays.toString(headers));
                text.setText(c + res.toString());
            }
        });
    }

    private void putRequest() {
        String url = "https://my-ninjas.herokuapp.com/api/ninjas/593591fa553c1700049df5de";

        JSONObject geo = new JSONObject();
        try {
            geo.put("type", "point");
            geo.put("coordinates", new JSONArray("[-80.1, 12]"));
        } catch (JSONException e) {
            Log.d("========G E O========", e.getMessage());
        }

        JSONObject entity = new JSONObject();
        try {
            entity.put("geometry", geo);
        } catch (JSONException e) {
            Log.d("--------------", "json in json");
        }

        StringEntity param = null;
        try {
            param = new StringEntity(entity.toString());
        } catch (UnsupportedEncodingException e) {
            Log.d("--------------", "String entity");
        }

        client.put(getApplicationContext(), url, param, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("---------success------", "-------------------------------------success------------------------------------");
                text2.setText(a + String.valueOf(statusCode));
                text3.setText(b + Arrays.toString(headers));
                text.setText(c + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
                Log.i("---------fail----------", "-------------------------------------fail------------------------------------");
                text2.setText(a + String.valueOf(statusCode));
                text3.setText(b + Arrays.toString(headers));
                text.setText(c + res.toString());
            }
        });
    }

    private void deleteRequest() {
        String url = "https://my-ninjas.herokuapp.com/api/ninjas/593591fa553c1700049df5de";

        client.delete(getApplicationContext(), url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("---------success------", "-------------------------------------success------------------------------------");
                text2.setText(a + String.valueOf(statusCode));
                text3.setText(b + Arrays.toString(headers));
                text.setText(c + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject res) {
                Log.i("---------fail----------", "-------------------------------------fail------------------------------------");
                text2.setText(a + String.valueOf(statusCode));
                text3.setText(b + Arrays.toString(headers));
                text.setText(c + res.toString());
            }
        });
    }
}
