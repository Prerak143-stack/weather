package com.example.patel.weatherone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    double latitude = 37.8267;
    double longitude = -122.4233;
    public static final String TAG = MainActivity.class.getSimpleName();
    public CurrentWeather currentWeather;

    private TextView temperatureTextView;
    private ImageView iconImageView;
    private TextView timeValueTextView;
    private TextView humidityTextView;
    private TextView precipValueTextView;
    private TextView summaryValueTextView;
    private ImageView refreshImageView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        temperatureTextView = findViewById(R.id.temperatureTextView);
        iconImageView = findViewById(R.id.iconImageView);
        timeValueTextView = findViewById(R.id.timeTextView);
        humidityTextView = findViewById(R.id.humidityTextView);
        precipValueTextView = findViewById(R.id.precipTextView);
        summaryValueTextView = findViewById(R.id.summaryTextView);
        refreshImageView = findViewById(R.id.refreshImageView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        getForecast(latitude, longitude);


        refreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshImageView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        refreshImageView.setVisibility(View.VISIBLE);
                        getForecast(latitude, longitude);
                    }
                }, 1000);
            }
        });

    }




    private void getForecast(double latitude, double longitude) {
        String ApiKey = "20e7ae6d09ac3cdbcad9ed8990b21d33";

        String url = "https://api.darksky.net/forecast/"  + ApiKey + "/" + latitude + "," + longitude;

        //String url = "https://api.darksky.net/forecast/20e7ae6d09ac3cdbcad9ed8990b21d33/37.8267,-122.4233";

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(url).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.v(TAG, "Fail. No response from client.");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {

                        String jsonData = response.body().string();

                        if (response.isSuccessful()) {
                            Log.v(TAG, jsonData);

                            try {
                                currentWeather = getCurrentWeather(jsonData);


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        //Drawable drawable = getDrawable(Integer.parseInt(currentWeather.getIcon()));
                                        //^why not?

                                        iconImageView.setImageDrawable(getDrawable(currentWeather.getIcon()));
                                        temperatureTextView.setText(currentWeather.getTemperature() + "");
                                        timeValueTextView.setText("At " + currentWeather.getFormattedTime() + " it will be");
                                        humidityTextView.setText(currentWeather.getHumidity() + "");
                                        precipValueTextView.setText(currentWeather.getPrecipChance() + "");
                                        summaryValueTextView.setText(currentWeather.getSummary());

                                    }
                                });


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        else{
                            alertUserAboutError();
                        }

                    } catch (IOException e) {
                        Log.e(TAG, "IO Exception Caught", e);
                    }
                }
            });
        }
        else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG);
        }
    }




    private CurrentWeather getCurrentWeather(String jsonData) throws JSONException {
        CurrentWeather currentWeather = new CurrentWeather();

        JSONObject jsonObject = new JSONObject(jsonData);

        currentWeather.setTimeZone(jsonObject.getString("timezone"));

        JSONObject currently = jsonObject.getJSONObject("currently");

        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setLocationLabel("Alcatraz Island, CA");
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTemperature(currently.getDouble("temperature"));


        return currentWeather;
    }





    private boolean isNetworkAvailable(){
        Boolean isAvailable = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }

        return isAvailable;
    }





    private void alertUserAboutError() {

        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        alertDialogFragment.show(getSupportFragmentManager(), "error_message");

    }
}
