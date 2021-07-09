package com.example.hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hw05.response.CurWeatherResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.ResponseBody;

public class CurWeatherFragment extends Fragment {

    private static final String ARG_CITY = "CITY";

    TextView city, temp, temp_min, temp_max, desc, humid, w_speed, w_degree, cloudiness;
    Button forecast;
    ImageView sun;

    ICurWeather am;

    Data.City p_city;

    CurWeatherResponse curWeatherResponse;

    Gson gson = new Gson();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ICurWeather) {
            am = (ICurWeather) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public static CurWeatherFragment newInstance(Data.City p_city) {
        CurWeatherFragment fragment = new CurWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, p_city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            p_city = (Data.City) getArguments().getSerializable(ARG_CITY);
        }
    }

    interface ICurWeather{

        void requestCurrentWeather(Data.City city, MainActivity.APIResponse response);

        void sendWeatherForecastFragment(Data.City city);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cur_weather, container, false);
        getActivity().setTitle("Current Weather");
        city = view.findViewById(R.id.textView);
        temp = view.findViewById(R.id.textView10);
        temp_max = view.findViewById(R.id.textView11);
        temp_min = view.findViewById(R.id.textView12);
        desc = view.findViewById(R.id.textView13);
        humid = view.findViewById(R.id.textView14);
        w_speed = view.findViewById(R.id.textView15);
        w_degree = view.findViewById(R.id.textView16);
        cloudiness = view.findViewById(R.id.textView17);
        forecast = view.findViewById(R.id.button);
        sun = view.findViewById(R.id.imageView);

        city.setText(p_city.getCity()+","+p_city.getCountry());
        am.requestCurrentWeather(p_city, new MainActivity.APIResponse() {
            @Override
            public void onResponse(@NotNull String res_string) {
                curWeatherResponse = gson.fromJson(res_string, CurWeatherResponse.class);
            }

            @Override
            public void responseOnUI(@NotNull String res_string) {
                temp.setText(curWeatherResponse.getMain().getTemp() + " F");
                temp_max.setText(curWeatherResponse.getMain().getTemp_max() + " F");
                temp_min.setText(curWeatherResponse.getMain().getTemp_min() + " F");
                desc.setText(curWeatherResponse.getWeather().get(0).getDescription());
                humid.setText(curWeatherResponse.getMain().getHumidity() + "%");
                w_speed.setText(curWeatherResponse.getWind().getSpeed() + " miles/hr");
                w_degree.setText(curWeatherResponse.getWind().getDeg() + " degrees");
                cloudiness.setText(curWeatherResponse.getClouds().getAll() + "%");
                Picasso.get().load("http://openweathermap.org/img/wn/"+curWeatherResponse.getWeather().get(0).getIcon()+"@2x.png").into(sun);
            }

            @Override
            public void onError(@NotNull String response) {
            }
        });



        forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.sendWeatherForecastFragment(p_city);
            }
        });

        return view;
    }
}