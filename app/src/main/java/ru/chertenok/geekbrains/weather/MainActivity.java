package ru.chertenok.geekbrains.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import ru.chertenok.geekbrains.weather.datasource.ResourceData;
import ru.chertenok.geekbrains.weather.datasource.WeatherSourceManager;


public class MainActivity extends AppCompatActivity implements IonFinishLoad{
    IWeather weather;
    Spinner spinner;
    TextView weatherText;
    ArrayAdapter<String> adapterCity;
    SharedPreferences pref;
    final String SETTING_NAME = "setting";
    final String LAST_CITY_ATTR_NAME = "last_city";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null)
        {
            TextView textView = (TextView) findViewById(R.id.view_last);
            textView.setText(getString(R.string.last_request) + data.getExtras().getString(WeatherDetail.RESULT_PARAM_STRING));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //getResources().getStringArray(R.array.city);
        weather = WeatherSourceManager.getWeatherObj(WeatherSourceManager.WeatherDateSourceType.PropertyData,getApplicationContext());

        pref = getSharedPreferences(SETTING_NAME,MODE_PRIVATE);
        adapterCity = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        weather.getCityList(this);

        spinner.setAdapter(adapterCity);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  weather.getWeatherForCity(spinner.getSelectedItem().toString(),MainActivity.this);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString(LAST_CITY_ATTR_NAME,spinner.getSelectedItem().toString());
                edit.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                weatherText.setText("");

            }
        });
        if (pref.contains(LAST_CITY_ATTR_NAME))
        {
            String s = pref.getString(LAST_CITY_ATTR_NAME,null);
            int i = adapterCity.getPosition(s);
            if (i>-1) spinner.setSelection(i);
        }

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WeatherDetail.class);
                intent.putExtra(WeatherDetail.DETAIL_PARAM_STRING,spinner.getSelectedItem().toString());
                startActivityForResult(intent,1);

            }
        });

    }



    @Override
    public void finish(String[] data, Operations operations) {
        if (operations == Operations.loadCity)
        {
            adapterCity.clear();
            adapterCity.addAll(data);
        }
     //   if (operations == Operations.loadWeatherForCity)
     //   {
     //       StringBuilder s = new StringBuilder();
      //      for (int i = 0; i < data.length; i++) {
      //          s.append(data[i]);
     //       }
     //       weatherText.setText(s);

     //   }

    }
}
