package ru.chertenok.geekbrains.weather;

/**
 * Created by 13th on 30-Aug-17.
 */

public interface IonFinishLoad {
    public enum Operations {loadCity,loadWeatherForCity};
    void finish(String[] data,Operations operations);
}
