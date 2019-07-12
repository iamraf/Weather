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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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
    private LocationManager mLocationManager;

    private final int PERMISSION_CODE = 69;
    private final int LOCATION_CODE = 420;

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

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

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

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
            }
            else
            {
                if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                {
                    mGoogleMap.setMyLocationEnabled(true);

                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener()
                    {
                        @Override
                        public void onLocationChanged(android.location.Location location)
                        {
                            if(location != null)
                            {
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
                            }
                            else
                            {
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40.6401, 22.9444))); //Thessaloniki
                                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
                            }

                            mLocationManager.removeUpdates(this);
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle)
                        {

                        }

                        @Override
                        public void onProviderEnabled(String s)
                        {

                        }

                        @Override
                        public void onProviderDisabled(String s)
                        {

                        }
                    });
                }
                else
                {
                    new AlertDialog.Builder(MapActivity.this)
                            .setTitle("Location")
                            .setMessage("Location is disabled. Would you like turn it on?")
                            .setPositiveButton("YES", (dialogInterface, i) ->
                            {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(intent, LOCATION_CODE);
                            })
                            .setNegativeButton("NO", (dialogInterface, i) ->
                            {
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40.6401, 22.9444))); //Thessaloniki
                                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
                            })
                            .show();
                }
            }
        });
    }

    @Override
    public void locationAdded(boolean isSuccessful, String error)
    {
        if(isSuccessful)
        {
            Toast.makeText(getApplicationContext(), "New location added", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LOCATION_CODE)
        {
            if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {
                loadMapFragment();
            }
            else
            {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40.6401, 22.9444))); //Thessaloniki
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    loadMapFragment();
                }
            }
            else
            {
                Toast.makeText(MapActivity.this, "Failed to grand permissions.", Toast.LENGTH_SHORT).show();

                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40.6401, 22.9444))); //Thessaloniki
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
            }
        }
    }
}
