package com.github.h01d.weather.ui.tutorial;

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

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.h01d.weather.R;
import com.github.h01d.weather.data.local.preference.PreferencesManager;
import com.smarteist.autoimageslider.SliderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorialActivity extends AppCompatActivity
{
    @BindView(R.id.a_tutorial_toolbar)
    Toolbar toolbar;
    @BindView(R.id.a_tutorial_slider)
    SliderView sliderView;
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        ButterKnife.bind(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);

        toolbar.setTitle("Tutorial");
        toolbar.setBackgroundColor(Color.WHITE);
        setSupportActionBar(toolbar);

        sliderView.setSliderAdapter(new TutorialAdapter());
    }

    @Override
    public void onBackPressed()
    {
        Toast.makeText(getApplicationContext(), "Click finish to exit tutorial", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.tutorial_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.m_tutorial_close:
                PreferencesManager.setFirstTime();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
