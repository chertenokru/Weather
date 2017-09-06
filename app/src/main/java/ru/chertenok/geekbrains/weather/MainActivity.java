package ru.chertenok.geekbrains.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import ru.chertenok.geekbrains.weather.datasource.WeatherSourceManager;


public class MainActivity extends AppCompatActivity implements IonFinishLoad{
    IWeather weather;
    Spinner spinner;
    TextView weatherText;
    ArrayAdapter<String> adapterCity;
    SharedPreferences pref;
    final String SETTING_NAME = "setting";
    final String LAST_CITY_ATTR_NAME = "last_city";
    TextView textView;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null)
        {
                 textView.setText(getString(R.string.last_request) + data.getExtras().getString(WeatherDetailActivity.RESULT_PARAM_STRING));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // способ 2 запрета поворота
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner);
        textView = (TextView) findViewById(R.id.view_last);
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
        if (savedInstanceState !=null)
        {
            setSpinner(savedInstanceState.getString(WeatherDetailActivity.DETAIL_PARAM_STRING,null));
            textView.setText(savedInstanceState.getString(WeatherDetailActivity.RESULT_PARAM_STRING));
        } else
        if (pref.contains(LAST_CITY_ATTR_NAME))
        {
            setSpinner(pref.getString(LAST_CITY_ATTR_NAME,""));
        }

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WeatherDetailActivity.class);
                intent.putExtra(WeatherDetailActivity.DETAIL_PARAM_STRING,spinner.getSelectedItem().toString());
                startActivityForResult(intent,1);

            }
        });

    }

    private void setSpinner(String text) {
        int i = adapterCity.getPosition(text);
        if (i>-1) spinner.setSelection(i);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(WeatherDetailActivity.DETAIL_PARAM_STRING,spinner.getSelectedItem().toString());
        outState.putString(WeatherDetailActivity.RESULT_PARAM_STRING,textView.getText().toString());

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
