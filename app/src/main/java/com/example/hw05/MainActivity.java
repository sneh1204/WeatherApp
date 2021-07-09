package com.example.hw05;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw05.response.CurWeatherResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements CitiesFragment.ICities, CurWeatherFragment.ICurWeather, WeatherForecastFragment.IWeatherForecast {

    /**
     * Assignment #HW 05
     * MainActivity.java
     * Sneh Jain
     * Ivy Pham
     */

    public static final String OWM_API_KEY = "5cf7b6b1b5382ed5e25f02992dc39c8c";

    public final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private final OkHttpClient client = new OkHttpClient();

    public static final String TAG = "TAG";

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendCitiesFragment();
    }

    public void sendCitiesFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new CitiesFragment())
                .commit();
    }

    public void sendCurWeatherFragment(Data.City city){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, CurWeatherFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }

    public void sendWeatherForecastFragment(Data.City city){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, WeatherForecastFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }

    public void requestCurrentWeather(Data.City city, APIResponse response){
        HttpUrl url = HttpUrl.parse(BASE_URL + "weather").newBuilder()
                .addQueryParameter("q", city.getCity() + "," + city.getCountry())
                .addQueryParameter("appid", MainActivity.OWM_API_KEY)
                .addQueryParameter("units", "imperial")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        sendRequest(request, response);
    }

    public void requestWeatherForecast(Data.City city, APIResponse response){
        HttpUrl url = HttpUrl.parse(BASE_URL + "forecast").newBuilder()
                .addQueryParameter("q", city.getCity() + "," + city.getCountry())
                .addQueryParameter("appid", MainActivity.OWM_API_KEY)
                .addQueryParameter("units", "imperial")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        sendRequest(request, response);
    }

    private void toggleDialog(boolean toggle, @Nullable String msg){
        if(toggle){
            runOnUiThread(()->{
                dialog = new ProgressDialog(MainActivity.this);
                if(msg != null)
                    dialog.setMessage(msg);
                dialog.setCancelable(false);
                dialog.show();
            });

        }else{
            runOnUiThread(()->dialog.dismiss());
        }
    }

    private void sendRequest(Request request, APIResponse callback) {
        toggleDialog(true, getString(R.string.loading));
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                toggleDialog(false, null);
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                toggleDialog(false, null);

                ResponseBody responseBody = response.body();
                String res_string;
                if (responseBody != null) {
                    res_string = responseBody.string();
                    if (response.isSuccessful()) {
                        callback.onResponse(res_string);
                        runOnUiThread(() -> {
                            callback.responseOnUI(res_string);
                        });
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(MainActivity.this, res_string, Toast.LENGTH_LONG).show();
                            sendCitiesFragment();
                        });
                        callback.onError(res_string);
                    }
                }
            }
        });
    }

    interface APIResponse{

        void onResponse(@NotNull String res_string);

        void responseOnUI(@NotNull String res_string);

        void onError(@NotNull String response);

    }

}