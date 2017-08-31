package com.manna.temperature;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

public class Visualizer extends FragmentActivity {
/*
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fr;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    fr = new HomeFragment();
                    fr.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container, fr, "home");
                    fragmentTransaction.addToBackStack("home");
                    fragmentTransaction.commit();
                    getSupportFragmentManager().executePendingTransactions();
                    currentFragment = fr;
                    return true;
                }
                case R.id.navigation_dashboard: {
                    fr = new DashboardFragment();
                    fr.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container, fr);
                    fragmentTransaction.addToBackStack("dashboard");
                    fragmentTransaction.commit();
                    getSupportFragmentManager().executePendingTransactions();
                    currentFragment = fr;
                    return true;
                }
                case R.id.navigation_notifications: {
                    fr = new NotificationFragment();
                    fragmentTransaction.replace(R.id.fragment_container, fr);
                    fragmentTransaction.addToBackStack("navigation");
                    fragmentTransaction.commit();
                    getSupportFragmentManager().executePendingTransactions();
                    currentFragment = fr;
                    return true;
                }
            }
            return false;
        }

    };
*/
    private final static String format = "com.manna.temperature";
    private final static String token = "A1E-etVdy6HQOYuHhw9EkdAMyb9hTt3R9C";
    private final static String variable = "59a053a17625427aa3dbb7a8";
    private final static String url = "http://things.ubidots.com/api/v1.6/";
    private static Fragment currentFragment = new HomeFragment();
    private static Bundle bundle = new Bundle();

    private boolean celsius = true;
    String temperature = "";
    private float min = 0, mean = 0, max = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizer);

        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        TextView textStatisticalAdvertisement = (TextView) findViewById(R.id.info_get);
//        Typeface face = Typeface.createFromAsset(getAssets(), "Montserrat-Light.otf");
//        textStatisticalAdvertisement.setTypeface(face);

        ImageButton dashButton = (ImageButton) findViewById(R.id.dashboardButton);
        dashButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment fr;
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fr = new DashboardFragment();
                fr.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, fr);
                fragmentTransaction.addToBackStack("dashboard");
                fragmentTransaction.commit();
                getSupportFragmentManager().executePendingTransactions();
                currentFragment = fr;
            }
        });

        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment fr;
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fr = new HomeFragment();
                fr.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, fr, "home");
                fragmentTransaction.addToBackStack("home");
                fragmentTransaction.commit();
                getSupportFragmentManager().executePendingTransactions();
                currentFragment = fr;
            }
        });

        ImageButton degreeButton = (ImageButton) findViewById(R.id.degreeButton);
        degreeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Float tempTemperature = Float.valueOf(temperature.replace(",","."));
                if(celsius){
                    celsius = false;
                    temperature = String.format(Locale.getDefault(), "%.1f", ((tempTemperature*9)/5)+32);
                    mean = ((mean*9)/5)+32;
                    min = ((min*9)/5)+32;
                    max = ((max*9)/5)+32;

                    bundle.putFloat("min", min);
                    bundle.putFloat("max", max);
                    bundle.putFloat("mean", mean);
                    bundle.putString("temperature", temperature);

                    v.setBackgroundResource(R.drawable.fahrenheitdegree80x80);

                    if (currentFragment.getClass().toString().equals(HomeFragment.class.toString())) {
                        Fragment fr;
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fr = new HomeFragment();
                        fr.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, fr, "home");
                        fragmentTransaction.addToBackStack("home");
                        fragmentTransaction.commit();
                        currentFragment = fr;
                    }else if (currentFragment.getClass().toString().equals(DashboardFragment.class.toString())) {
                        Fragment fr;
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fr = new DashboardFragment();
                        fr.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, fr, "dashboard");
                        fragmentTransaction.addToBackStack("dashboard");
                        fragmentTransaction.commit();
                        currentFragment = fr;
                    }

                }else{
                    celsius = true;
                    temperature = String.format(Locale.getDefault(), "%.1f", (tempTemperature-32) * 5/9);
                    mean = ((mean-32) * 5)/9;
                    min = ((min-32) * 5)/9;
                    max = ((max-32) * 5)/9;

                    bundle.putFloat("min", min);
                    bundle.putFloat("max", max);
                    bundle.putFloat("mean", mean);
                    bundle.putString("temperature", temperature);

                    v.setBackgroundResource(R.drawable.celsiusdegree80x80);

                    if (currentFragment.getClass().toString().equals(HomeFragment.class.toString())) {
                        Fragment fr;
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fr = new HomeFragment();
                        fr.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, fr, "home");
                        fragmentTransaction.addToBackStack("home");
                        fragmentTransaction.commit();
                        currentFragment = fr;
                    }else if (currentFragment.getClass().toString().equals(DashboardFragment.class.toString())) {
                        Fragment fr;
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fr = new DashboardFragment();
                        fr.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, fr, "dashboard");
                        fragmentTransaction.addToBackStack("dashboard");
                        fragmentTransaction.commit();
                        currentFragment = fr;
                    }
                }
            }
        });

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getStats();
                            }
                        });
                        Thread.sleep(1000 * 60 * 5);
                    }
                } catch (InterruptedException e) {

                }
            }
        };
        t.start();
    }

    private void getStats() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Calendar cal = Calendar.getInstance();
        String end = String.valueOf(cal.getTime().getTime());
        cal.add(Calendar.HOUR, -24);
        String start = String.valueOf(cal.getTime().getTime());

        final String statsUrl = url + "variables/" + variable + "/statistics/mean,min,max/" + start + "/" + end + "/?token=" + token + "&format=json";
        final String tempUrl = url + "variables/" + variable + "/?token=" + token + "&format=json";

        JsonObjectRequest stats = new JsonObjectRequest(Request.Method.GET, statsUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(format, response.toString());
                try {

                    mean = Float.parseFloat(response.getString("mean"));
                    min = Float.parseFloat(response.getString("min"));
                    max = Float.parseFloat(response.getString("max"));

                    bundle.putFloat("min", min);
                    bundle.putFloat("max", max);
                    bundle.putFloat("mean", mean);

                    if (currentFragment.getClass().toString().equals(DashboardFragment.class.toString())) {
                        Fragment fr;
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fr = new DashboardFragment();
                        fr.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, fr, "dashboard");
                        fragmentTransaction.addToBackStack("dashboard");
                        fragmentTransaction.commit();
                        currentFragment = fr;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d(format, error.toString());
            }
        });

        requestQueue.add(stats);

        stats.setRetryPolicy(new DefaultRetryPolicy(5000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        JsonObjectRequest temp = new JsonObjectRequest(Request.Method.GET, tempUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(format, response.toString());
                try {

                    JSONObject lastReading = response.getJSONObject("last_value");

                    temperature = String.format(Locale.getDefault(), "%.1f", Float.parseFloat(lastReading.getString("value")));
                    bundle.putString("temperature", temperature);

                    if (currentFragment.getClass().toString().equals(HomeFragment.class.toString())) {
                        Fragment fr;
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fr = new HomeFragment();
                        fr.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, fr, "home");
                        fragmentTransaction.addToBackStack("home");
                        fragmentTransaction.commit();
                        currentFragment = fr;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d(format, error.toString());
            }
        });

        temp.setRetryPolicy(new DefaultRetryPolicy(5000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(temp);
    }
}
