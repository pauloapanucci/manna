package com.manna.temperature;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    TextView temperature;
    ImageView backgroundImage;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        temperature = (TextView) view.findViewById(R.id.home_temp);
        temperature.setText(this.getArguments().getString("temperature"));

        backgroundImage = (ImageView) view.findViewById(R.id.background);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                backgroundImage.animate().rotationBy(360).withEndAction(this).setDuration(1500).setInterpolator(new LinearInterpolator()).start();
            }
        };
        backgroundImage.animate().rotationBy(360).withEndAction(runnable).setDuration(1500).setInterpolator(new LinearInterpolator()).start();

        System.out.println("Home updated with temperature " + this.getArguments().getString("temperature"));
        return view;
    }

    public void modifyText(String text) {
        if (temperature != null)
            temperature.setText(text);
    }
}
