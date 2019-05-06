package com.github.h01d.weather.ui.location;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.h01d.weather.data.local.database.DatabaseManager;
import com.github.h01d.weather.data.local.database.entity.Location;
import com.github.h01d.weather.data.local.preference.PreferencesManager;
import com.github.h01d.weather.R;
import com.github.h01d.weather.ui.map.MapActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationActivity extends AppCompatActivity implements LocationView, LocationAdapter.LocationAdapterListener
{
    @BindView(R.id.a_location_toolbar)
    Toolbar toolbar;
    @BindView(R.id.a_location_recycler)
    RecyclerView locationRecycler;
    @BindView(R.id.a_location_error)
    TextView errorMessage;
    @OnClick(R.id.a_location_floating)
    void startMaps()
    {
        startActivity(new Intent(LocationActivity.this, MapActivity.class));
    }

    private LocationPresenter mPresenter;
    private LocationAdapter mLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        ButterKnife.bind(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);

        toolbar.setTitle("Select location");
        toolbar.setBackgroundColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLocationAdapter = new LocationAdapter(this);
        locationRecycler.setLayoutManager(new LinearLayoutManager(this));
        locationRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        locationRecycler.setAdapter(mLocationAdapter);

        mPresenter = new LocationPresenter(this, DatabaseManager.getInstance(getApplication()));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        mPresenter.dispose();
    }

    @Override
    public void updateUI(List<Location> locations)
    {
        if(locations.isEmpty())
        {
            locationRecycler.setVisibility(View.GONE);
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("You have no saved locations");
        }
        else
        {
            errorMessage.setVisibility(View.GONE);

            locationRecycler.setVisibility(View.VISIBLE);
            mLocationAdapter.setLocations(locations);
        }
    }

    @Override
    public void onSelected(Location location)
    {
        PreferencesManager.setSavedLocation(location);
        finish();
    }

    @Override
    public void onDeleted(Location location)
    {
        new AlertDialog.Builder(LocationActivity.this)
                .setTitle("Delete location")
                .setMessage("Are you sure you want to delete " + location.getName() + "?")
                .setPositiveButton("YES", (dialog, which) -> mPresenter.deleteLocation(location))
                .setNegativeButton("NO", null)
                .show();
    }
}
