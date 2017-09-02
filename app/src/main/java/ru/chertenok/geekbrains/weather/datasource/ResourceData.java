package ru.chertenok.geekbrains.weather.datasource;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.chertenok.geekbrains.weather.IWeather;
import ru.chertenok.geekbrains.weather.IonFinishLoad;
import ru.chertenok.geekbrains.weather.R;

/**
 * Created by 13th on 29-Aug-17.
 */

public class ResourceData implements IWeather {
    private final Context context;
    private final List<String> cityList;
    private final List<String> weatherList;
    private final String name;


    public ResourceData(Context context) {
        this.context = context;
        name = context.getString(R.string.resourceDate);
        cityList = new ArrayList<String>(Arrays.asList(context.getResources().getStringArray(R.array.city)));
        weatherList = new ArrayList<String>(Arrays.asList(context.getResources().getStringArray(R.array.city_value)));

        if (cityList.size() != weatherList.size())
            new Exception("Число городов и значений погоды не совпадает !");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void getCityList(IonFinishLoad onFinishLoad) {
        if (onFinishLoad != null)
            onFinishLoad.finish(cityList.toArray(new String[cityList.size()]), IonFinishLoad.Operations.loadCity);
    }

    @Override
    public void getWeatherForCity(String city, IonFinishLoad onFinishLoad) {
        int i = cityList.indexOf(city);
        if (onFinishLoad != null)
            onFinishLoad.finish(new String[]{((i > -1) ? weatherList.get(cityList.indexOf(city)) : "")}, IonFinishLoad.Operations.loadWeatherForCity);
    }


}

