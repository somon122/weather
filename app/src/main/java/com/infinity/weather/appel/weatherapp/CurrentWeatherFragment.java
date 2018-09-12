package com.infinity.weather.appel.weatherapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.infinity.weather.appel.weatherapp.api_collections.RetrofitClient;
import com.infinity.weather.appel.weatherapp.api_collections.WeatherApi;
import com.infinity.weather.appel.weatherapp.models.CurrentWeather;
import com.infinity.weather.appel.weatherapp.models.Weather;
import com.squareup.picasso.Picasso;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CurrentWeatherFragment extends Fragment {
    WeatherApi weatherApi;
    TextView ctemp1, city, date, details,minTemp,maxTemp,windspeed,winddeg,humidity,windpres;
    ImageView imageView;
    ToggleButton tglBtn;
    String url="weather?q=Dhaka,bd&units=metric&appid=e384f9ac095b2109c751d95296f8ea76";
    String units="C";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        ctemp1 = view.findViewById(R.id.ctemp);
        imageView = view.findViewById(R.id.tempim);
        city = view.findViewById(R.id.cityName);
        humidity=view.findViewById(R.id.humidity);
        details = view.findViewById(R.id.details);
        date = view.findViewById(R.id.dateTime);
        minTemp=view.findViewById(R.id.tempMin);
        maxTemp=view.findViewById(R.id.tempMax);
        windspeed=view.findViewById(R.id.windSpeed);
        winddeg=view.findViewById(R.id.windDeg);
        windpres=view.findViewById(R.id.windPressure);
        tglBtn=view.findViewById(R.id.toggleButton);
        getCurrentWeather(url);
        return view;
    }


    public void getCurrentWeather(String current) {
        weatherApi = RetrofitClient.getRetrofitClient().create(WeatherApi.class);
        Call<CurrentWeather> weatherApiWeatherCall = weatherApi.getCurrentWeatherCall(current);

        weatherApiWeatherCall.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                CurrentWeather weather = response.body();
                //Toast.makeText(getContext(), "" + weather.getMain().getTemp(), Toast.LENGTH_LONG).show();
                ctemp1.setText(String.valueOf(weather.getMain().getTemp()) + "°" + units);
                maxTemp.setText("Max:"+String.valueOf(weather.getMain().getTempMax()).toString() + "°" + units);
                minTemp.setText("Min:"+String.valueOf(weather.getMain().getTempMin()).toString() + "°" + units);
                humidity.setText("Hum:"+String.valueOf(weather.getMain().getHumidity()).toString()+"%");
                windspeed.setText("Speed:"+String.valueOf(weather.getWind().getSpeed()).toString()+"km");
                winddeg.setText("Deg:"+String.valueOf(weather.getWind().getDeg()).toString()+"°");
                windpres.setText("Press:"+String.valueOf(weather.getMain().getPressure()).toString()+"hPa");
                city.setText(weather.getName() + "," + weather.getSys().getCountry());
                date.setText(dateformat(weather.getDt()).toString());

                java.util.List<Weather> weatherList = weather.getWeather();
                for (Weather obj : weatherList) {
                    String icon = String.valueOf(obj.getIcon());
                    Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png").into(imageView);
                    details.setText(String.valueOf(obj.getDescription()));
                }

            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                //Picasso.get().load("http://openweathermap.org/img/w/10d.png").into(imageView);
            }

            public String dateformat(int timestamp) {
                //"EEE, MMM d, ''yy"  Wed, Jul 4, '01
                //"h:mm a"    12:08 PM
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(timestamp * 1000L);
                String date = DateFormat.format("EEE, MMM d, yyyy", cal).toString();

                return date;
            }
        });
        tglBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tglBtn.isChecked()){
                    getCurrentWeather("weather?q=Dhaka,bd&units=metric&appid=e384f9ac095b2109c751d95296f8ea76");
                    units="C";
                }
                else {
                    getCurrentWeather("weather?q=Dhaka,bd&units=imperial&appid=e384f9ac095b2109c751d95296f8ea76");
                    units="F";
                }
            }
        });



    }
}
