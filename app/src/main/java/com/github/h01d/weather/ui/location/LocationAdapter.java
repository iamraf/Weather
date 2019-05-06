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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.h01d.weather.data.local.database.entity.Location;
import com.github.h01d.weather.data.local.preference.PreferencesManager;
import com.github.h01d.weather.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>
{
    private List<Location> data = new ArrayList<>();
    private LocationAdapterListener listener;

    public LocationAdapter(LocationAdapterListener listener)
    {
        this.listener = listener;
    }

    public void setLocations(List<Location> locations)
    {
        data = locations;
        notifyDataSetChanged();
    }

    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);

        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationViewHolder holder, int position)
    {
        final Location location = data.get(position);

        holder.text.setText(location.getName() + ", " + location.getCountry());

        if(PreferencesManager.getSavedLocation() != null && PreferencesManager.getSavedLocation().getName().equals(location.getName()))
        {
            holder.selected.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.selected.setVisibility(View.GONE);
        }

        holder.delete.setOnClickListener(v -> listener.onDeleted(location));
        holder.itemView.setOnClickListener(v -> listener.onSelected(location));
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.i_location_text)
        TextView text;
        @BindView(R.id.i_location_selected)
        ImageView selected;
        @BindView(R.id.i_location_delete)
        ImageView delete;

        LocationViewHolder(View itemView)
        {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    interface LocationAdapterListener
    {
        void onSelected(Location location);

        void onDeleted(Location location);
    }
}
