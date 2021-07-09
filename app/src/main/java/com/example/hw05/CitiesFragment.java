package com.example.hw05;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesFragment extends Fragment {

    ICities am;
    RecyclerView cities_view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ICities) {
            am = (ICities) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    interface ICities{

        void sendCurWeatherFragment(Data.City city);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        getActivity().setTitle("Cities");
        cities_view = view.findViewById(R.id.cities_view);
        cities_view.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        cities_view.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(cities_view.getContext(),
                llm.getOrientation());
        cities_view.addItemDecoration(dividerItemDecoration);
        cities_view.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item_1, parent, false);
                return new RecyclerView.ViewHolder(view){};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                TextView city = holder.itemView.findViewById(R.id.text1);
                Data.City cur = Data.cities.get(position);
                city.setText(cur.getCity() + "," + cur.getCountry());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        am.sendCurWeatherFragment(cur);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return Data.cities.size();
            }
        });
        return view;
    }
}