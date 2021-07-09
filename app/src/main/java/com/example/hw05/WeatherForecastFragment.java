package com.example.hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hw05.response.WeatherForecastResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class WeatherForecastFragment extends Fragment {

    private static final String ARG_CITY = "CITY";

    private Data.City p_city;

    TextView title, dt_txt, temp, humid, desc;

    ImageView sun;

    RecyclerView forecastView;

    IWeatherForecast am;

    Gson gson = new Gson();

    WeatherForecastResponse weatherForecastResponse;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IWeatherForecast) {
            am = (IWeatherForecast) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    interface IWeatherForecast{

        void requestWeatherForecast(Data.City city, MainActivity.APIResponse response);

    }

    public static WeatherForecastFragment newInstance(Data.City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        getActivity().setTitle("Weather Forecast");
        title = view.findViewById(R.id.textView18);
        title.setText(p_city.getCity()+","+p_city.getCountry());

        forecastView = view.findViewById(R.id.forecastView);
        forecastView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        forecastView.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(forecastView.getContext(),
                llm.getOrientation());
        forecastView.addItemDecoration(dividerItemDecoration);
        am.requestWeatherForecast(p_city, new MainActivity.APIResponse() {
            @Override
            public void onResponse(@NotNull String res_string) {
                weatherForecastResponse = gson.fromJson(res_string, WeatherForecastResponse.class);
            }

            @Override
            public void responseOnUI(@NotNull String res_string) {
                forecastView.setAdapter(new RecyclerView.Adapter() {
                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item_list, parent, false);
                        return new RecyclerView.ViewHolder(view){};
                    }

                    @Override
                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                        dt_txt = holder.itemView.findViewById(R.id.textView19);
                        temp = holder.itemView.findViewById(R.id.textView20);
                        humid = holder.itemView.findViewById(R.id.textView21);
                        desc = holder.itemView.findViewById(R.id.textView22);
                        sun = holder.itemView.findViewById(R.id.imageView2);
                        WeatherForecastResponse.List list = weatherForecastResponse.getList().get(position);
                        dt_txt.setText(list.getDt_txt());
                        temp.setText(list.getMain().getTemp() + "F"
                                + "      Max: " + list.getMain().getTemp_max() + "F"
                                + "      Min: " + list.getMain().getTemp_min() + "F"
                        );
                        humid.setText("Humidity: " + list.getMain().getHumidity() + "%");
                        desc.setText(list.getWeather().get(0).getDescription());
                        Picasso.get().load("http://openweathermap.org/img/wn/"+list.getWeather().get(0).getIcon()+"@2x.png").into(sun);
                    }

                    @Override
                    public int getItemCount() {
                        return weatherForecastResponse.getList().size();
                    }
                });
            }

            @Override
            public void onError(@NotNull String response) {
            }
        });
        return view;
    }
}