package ru.chertenok.geekbrains.weather;

/**
 * Created by 13th on 29-Aug-17.
 */

public interface IWeather {
    String getName();
    void getCityList(IonFinishLoad onFinishLoad);
    void getWeatherForCity(String city,IonFinishLoad onFinishLoad);
}
