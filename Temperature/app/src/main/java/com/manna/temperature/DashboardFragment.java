package com.manna.temperature;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DashboardFragment extends Fragment {

    private static TextView min, max, mean;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        min = (TextView) view.findViewById(R.id.value_minimum);
        max = (TextView) view.findViewById(R.id.value_maximum);
        mean = (TextView) view.findViewById(R.id.value_mean);

        min.setText(String.format(this.getResources().getString(R.string.temp), this.getArguments().getFloat("min")));
        max.setText(String.format(this.getResources().getString(R.string.temp), this.getArguments().getFloat("max")));
        mean.setText(String.format(this.getResources().getString(R.string.temp), this.getArguments().getFloat("mean")));
        System.out.println("Dashboard updated with min " + this.getArguments().getFloat("min") + " max " + this.getArguments().getFloat("max") + " mean " + this.getArguments().getFloat("mean"));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void modifyText(String maxValue, String minValue, String meanValue) {
        min.setText(maxValue);

        max.setText(minValue);

        mean.setText(meanValue);
    }
}
