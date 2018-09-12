package com.infinity.weather.appel.weatherapp.api_collections;

import com.infinity.weather.appel.weatherapp.models.CurrentWeather;
import com.infinity.weather.appel.weatherapp.models.forecast.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherApi {

    //"weather?q=Dhaka,bd&units=metric&appid=e384f9ac095b2109c751d95296f8ea76"
    @GET()
    Call<CurrentWeather> getCurrentWeatherCall(@Url String url);

    //"forecast/daily?q=Dhaka,bd&units=metric&appid=e384f9ac095b2109c751d95296f8ea76"
    @GET()
    Call<Forecast> getForecastCall(@Url String url);
}
