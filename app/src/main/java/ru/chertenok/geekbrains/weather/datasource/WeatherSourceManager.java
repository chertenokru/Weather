package ru.chertenok.geekbrains.weather.datasource;

import android.content.Context;

import ru.chertenok.geekbrains.weather.IWeather;

/**
 * Created by 13th on 01-Sep-17.
 */

public class WeatherSourceManager {
    public enum WeatherDateSourceType{PropertyData,YandexData};

    private static IWeather iWeather = null;
    private static WeatherDateSourceType weatherDateSourceType;

    public static IWeather getWeatherObj( Context context)
    {
        if (iWeather != null)  return iWeather;
        else return getWeatherObj(WeatherDateSourceType.PropertyData,context);
    }


    public static IWeather getWeatherObj(WeatherDateSourceType weatherDateSource, Context context) {
        if (iWeather != null && weatherDateSource == WeatherSourceManager.weatherDateSourceType)  return iWeather;
        else
        {
            switch (weatherDateSource) {
                case PropertyData: iWeather = new ResourceData(context);
                    break;
                case YandexData: iWeather = new YandexData(context);
                    break;
            }
            if (iWeather != null) WeatherSourceManager.weatherDateSourceType = weatherDateSource;
            return  iWeather;
        }
    }

}
