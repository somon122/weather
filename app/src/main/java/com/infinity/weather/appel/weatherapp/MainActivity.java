package com.infinity.weather.appel.weatherapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final java.util.List<Fragment> fragmentList = new ArrayList<>();
    ViewPager vp;
    Fragment one, two;
    TabLayout tabLayout;
    SectionPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewPager();
    }

    private void setupViewPager() {
        adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new CurrentWeatherFragment());
        adapter.addFragment(new ForecastWeatherFragment());

        vp = findViewById(R.id.viewPager);
        vp.setAdapter(adapter);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vp);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.white));

        tabLayout.getTabAt(0).setText("Current weather");
        tabLayout.getTabAt(1).setText("5 Days Forecast");
    }

    public class SectionPageAdapter extends FragmentPagerAdapter {


        public SectionPageAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int i) {

            switch (i) {
                case 0:
                    if(one == null)
                        one = new CurrentWeatherFragment();
                    return one;
                case 1:
                    if(two == null)
                        two= new ForecastWeatherFragment();
                    return two;
            }
            return null;
        }

        @Override
        public int getCount() {

            return fragmentList.size();
        }

        public void addFragment(Fragment fragment) {

            fragmentList.add(fragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.searchcity, menu);
        MenuItem item = menu.findItem(R.id.serachItem);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Type city name with country code");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                int pos = vp.getCurrentItem();
                Fragment activeFragment = adapter.getItem(pos);
                if(pos == 0)
                    ((CurrentWeatherFragment)activeFragment).getCurrentWeather("weather?q="+s+"&units=metric&appid=e384f9ac095b2109c751d95296f8ea76");
                else
                    ((ForecastWeatherFragment)activeFragment).getForecastWeather("forecast/daily?q="+s+"&units=metric&appid=e384f9ac095b2109c751d95296f8ea76");


                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}
