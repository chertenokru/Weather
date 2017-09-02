package ru.chertenok.geekbrains.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import ru.chertenok.geekbrains.weather.datasource.WeatherSourceManager;


public class WeatherDetail extends AppCompatActivity implements IonFinishLoad {
    public final static String DETAIL_PARAM_STRING = "city";
    public final static String RESULT_PARAM_STRING = "last";
    private IWeather weather;
    private TextView textView;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        textView = (TextView) findViewById(R.id.detail_text);
        Intent intent = getIntent();
        weather = WeatherSourceManager.getWeatherObj(getApplicationContext());
        if (intent != null) {
            s = intent.getExtras().getString(DETAIL_PARAM_STRING, getString(R.string.not_found));
            weather.getWeatherForCity(s, this);
        } else
            textView.setText(getString(R.string.weather_text) + ": "+getString(R.string.not_found));

        Button button_share = (Button) findViewById(R.id.button_share);
        Button button_return = (Button) findViewById(R.id.button_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, textView.getText());
                intent1.setType("text/plain");

                if (getPackageManager().queryIntentActivities(intent1, 0).size() > 0) {
                    startActivity(intent1);
                } else
                    Toast.makeText(getApplicationContext(), R.string.error_send, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish(String[] data, Operations operations) {
        if (operations == Operations.loadWeatherForCity) {
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                b.append(data[i]);
            }
            textView.setText(getString(R.string.weather_text) + " " + s + " : " + b.toString());
            Intent intent = new Intent();
            intent.putExtra(RESULT_PARAM_STRING,textView.getText());
            setResult(RESULT_OK,intent);
        }
    }
}
