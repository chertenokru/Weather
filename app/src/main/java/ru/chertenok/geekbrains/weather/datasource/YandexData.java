package ru.chertenok.geekbrains.weather.datasource;

import android.content.Context;

import java.util.List;

import ru.chertenok.geekbrains.weather.IWeather;
import ru.chertenok.geekbrains.weather.IonFinishLoad;
import ru.chertenok.geekbrains.weather.R;

/**
 * Created by 13th on 30-Aug-17.
 */



public class YandexData implements IWeather {
    private final Context context;
    private final String name;

    public YandexData(Context context) {
        this.context = context;
        name = context.getString(R.string.yandex);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void getCityList(final IonFinishLoad onFinishLoad) {
//  https://yandex.ru/yaca/geo.c2n
        new Thread(new Runnable() {
            @Override
            public void run() {


                onFinishLoad.finish (null, IonFinishLoad.Operations.loadCity);
            }
        }).start();
    }

    @Override
    public void getWeatherForCity(String city, IonFinishLoad onFinishLoad) {
//  https://export.yandex.ru/bar/reginfo.xml?region=1
    }
}
