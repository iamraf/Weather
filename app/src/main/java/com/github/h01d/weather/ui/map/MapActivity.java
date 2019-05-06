package com.github.h01d.weather.ui.map;

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
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.h01d.weather.data.local.database.DatabaseManager;
import com.github.h01d.weather.data.local.database.entity.Location;
import com.github.h01d.weather.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity implements MapView
{
    @BindView(R.id.a_map_toolbar)
    Toolbar toolbar;
    @BindView(R.id.a_map_searchview)
    MaterialSearchView searchView;
    @OnClick(R.id.a_map_floating)
    void save()
    {
        if(mSelectedLocation != null)
        {
            mPresenter.saveLocation(mSelectedLocation);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please select location", Toast.LENGTH_SHORT).show();
        }
    }

    private MapPresenter mPresenter;
    private GoogleMap mGoogleMap;
    private Location mSelectedLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ButterKnife.bind(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);

        toolbar.setTitle("Add location");
        toolbar.setBackgroundColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(final String query)
            {
                if(query.length() != 0)
                {
                    Geocoder geocoder = new Geocoder(getApplicationContext());

                    try
                    {
                        if(geocoder.getFromLocationName(query, 1).size() != 0)
                        {
                            final Address address = geocoder.getFromLocationName(query, 1).get(0);
                            final LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                            if(address.getLocality() != null && !address.getLocality().isEmpty())
                            {
                                mGoogleMap.addMarker(new MarkerOptions().position(latLng));
                                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                                toolbar.setTitle(address.getLocality());

                                mSelectedLocation = new Location(address.getLocality(), address.getCountryName(), latLng.latitude, latLng.longitude);
                            }
                            else
                            {
                                Toast.makeText(getBaseContext(), "Location not available", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(), "Location not available", Toast.LENGTH_LONG).show();
                        }
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                searchView.closeSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });

        mPresenter = new MapPresenter(this, DatabaseManager.getInstance(getApplication()));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        mPresenter.dispose();
    }

    @Override
    public void onBackPressed()
    {
        if(searchView.isSearchOpen())
        {
            searchView.closeSearch();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.m_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void loadMapFragment()
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.a_map_fragment);
        mapFragment.getMapAsync(googleMap ->
        {
            mGoogleMap = googleMap;

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40.6401, 22.9444))); //Thessaloniki
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));

            mGoogleMap.setOnMapLongClickListener(latLng ->
            {
                mGoogleMap.clear();

                Geocoder geocoder = new Geocoder(getApplicationContext());

                try
                {
                    if(geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).size() != 0)
                    {
                        final Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);

                        if(address != null && address.getLocality() != null && !address.getLocality().isEmpty())
                        {
                            mGoogleMap.addMarker(new MarkerOptions().position(latLng));
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                            toolbar.setTitle(address.getLocality());

                            mSelectedLocation = new Location(address.getLocality(), address.getCountryName(), latLng.latitude, latLng.longitude);
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(), "Location not available", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Location not available", Toast.LENGTH_LONG).show();
                    }
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            });
        });
    }

    @Override
    public void locationAdded(boolean isSuccessful, String error)
    {
        if(isSuccessful)
        {
            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "New location added", Toast.LENGTH_SHORT).show());
            finish();
        }
        else
        {
            runOnUiThread(() -> Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show());
        }
    }
}
