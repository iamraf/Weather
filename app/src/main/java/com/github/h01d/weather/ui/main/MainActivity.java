package com.github.h01d.weather.ui.main;

/*
    Copyright 2019 Raf
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.h01d.weather.data.local.preference.PreferencesManager;
import com.github.h01d.weather.data.remote.model.Forecast;
import com.github.h01d.weather.ui.location.LocationActivity;
import com.github.h01d.weather.ui.settings.SettingsActivity;
import com.github.h01d.weather.util.TimeUtils;
import com.github.h01d.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView
{
    @BindView(R.id.a_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.a_main_constraint)
    ConstraintLayout mainConstraint;
    @BindView(R.id.a_main_layout)
    ConstraintLayout mainLayout;
    @BindView(R.id.a_main_bottom)
    ConstraintLayout bottomLayout;
    @BindView(R.id.a_main_error_layout)
    ConstraintLayout errorLayout;
    @BindView(R.id.a_main_image)
    ImageView mainImage;
    @BindView(R.id.a_main_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.a_main_progress)
    ProgressBar progressBar;
    @BindView(R.id.a_main_error)
    TextView errorText;
    @BindView(R.id.a_main_degrees)
    TextView degreesText;
    @BindView(R.id.a_main_date)
    TextView dateText;
    @BindView(R.id.a_main_status)
    TextView statusText;
    @BindView(R.id.a_main_wind)
    TextView windText;
    @BindView(R.id.a_main_humidity)
    TextView humidityText;
    @BindView(R.id.a_main_summary)
    TextView summaryText;
    @BindView(R.id.a_main_daily)
    Button dailyButton;
    @BindView(R.id.a_main_hourly)
    Button hourlyButton;

    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new MainAdapter(this));

        PreferencesManager.init(getApplicationContext());

        mMainPresenter = new MainPresenter(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        mMainPresenter.dispose();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        mMainPresenter.loadWeather();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.m_main_location:
                startActivity(new Intent(MainActivity.this, LocationActivity.class));
                return true;
            case R.id.m_main_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateUI(Forecast forecast)
    {
        if(forecast != null)
        {
            if(forecast.getCurrently() != null)
            {
                mainLayout.setVisibility(View.VISIBLE);

                degreesText.setText((int) forecast.getCurrently().getTemperature() + "°");
                dateText.setText(TimeUtils.timestampToDate(forecast.getCurrently().getTime()));
                statusText.setText(forecast.getCurrently().getSummary());
                windText.setText((int) forecast.getCurrently().getWindSpeed() + (forecast.getFlags().getUnits().equals("ca") ? " km/h" : (forecast.getFlags().getUnits().equals("si") ? " m/s" : " mp/h")));
                humidityText.setText((int) (forecast.getCurrently().getHumidity() * 100) + "%");

                if(forecast.getCurrently().getIcon() != null)
                {
                    switch(forecast.getCurrently().getIcon())
                    {
                        case "partly-cloudy-day":
                        case "partly-cloudy-night":
                            toolbar.setBackground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyClear)));
                            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyClear));
                            mainConstraint.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyClear));
                            bottomLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherTerrainClear));
                            mainImage.setImageResource(R.drawable.partly_cloudy);
                            break;
                        case "rain":
                        case "sleet":
                        case "thunderstorm":
                            toolbar.setBackground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyRain)));
                            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyRain));
                            mainConstraint.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyRain));
                            bottomLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherTerrainRain));
                            mainImage.setImageResource(R.drawable.rain);
                            break;
                        case "cloudy":
                        case "fog":
                        case "wind":
                        case "tornado":
                            toolbar.setBackground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyRain)));
                            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyRain));
                            mainConstraint.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyRain));
                            bottomLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherTerrainRain));
                            mainImage.setImageResource(R.drawable.cloudy);
                            break;
                        case "snow":
                        case "hail":
                            toolbar.setBackground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkySnow)));
                            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkySnow));
                            mainConstraint.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkySnow));
                            bottomLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherTerrainSnow));
                            mainImage.setImageResource(R.drawable.snow);
                            break;
                        default:
                            toolbar.setBackground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyClear)));
                            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyClear));
                            mainConstraint.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherSkyClear));
                            bottomLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.weatherTerrainClear));
                            mainImage.setImageResource(R.drawable.clear);
                    }

                    if(forecast.getHourly() != null && forecast.getHourly().getData() != null)
                    {
                        ((MainAdapter) recyclerView.getAdapter()).setData(forecast.getHourly().getData());
                        summaryText.setText(forecast.getHourly().getSummary());

                        hourlyButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                        dailyButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGray));

                        hourlyButton.setOnClickListener(v ->
                        {
                            ((MainAdapter) recyclerView.getAdapter()).setData(forecast.getHourly().getData());
                            summaryText.setText(forecast.getHourly().getSummary());

                            hourlyButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                            dailyButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGray));
                        });
                    }
                    else
                    {
                        hourlyButton.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "No Available", Toast.LENGTH_SHORT).show());
                    }

                    if(forecast.getDaily() != null && forecast.getDaily().getData() != null)
                    {
                        if(forecast.getHourly() == null || forecast.getHourly().getData() == null)
                        {
                            ((MainAdapter) recyclerView.getAdapter()).setData(forecast.getDaily().getData());
                            summaryText.setText(forecast.getDaily().getSummary());

                            dailyButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                            hourlyButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGray));
                        }

                        dailyButton.setOnClickListener(v ->
                        {
                            ((MainAdapter) recyclerView.getAdapter()).setData(forecast.getDaily().getData());
                            summaryText.setText(forecast.getDaily().getSummary());

                            dailyButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                            hourlyButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGray));
                        });
                    }
                    else
                    {
                        dailyButton.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "No Available", Toast.LENGTH_SHORT).show());
                    }

                    if((forecast.getHourly() == null || forecast.getHourly().getData() == null) && (forecast.getDaily() == null || forecast.getDaily().getData() == null))
                    {
                        hourlyButton.setVisibility(View.GONE);
                        dailyButton.setVisibility(View.GONE);
                    }
                }
            }
            else
            {
                showError("Could not load weather");
            }
        }
        else

        {
            showError("Could not load weather");
        }

    }

    @Override
    public void isLoading(boolean flag)
    {
        if(flag)
        {
            progressBar.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            mainLayout.setVisibility(View.GONE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateTitle(String title)
    {
        toolbar.setTitle(title);
    }

    @Override
    public void showError(String error)
    {
        errorText.setText(error);
        errorLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
    }
}
