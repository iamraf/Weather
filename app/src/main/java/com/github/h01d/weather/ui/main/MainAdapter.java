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

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.h01d.weather.data.remote.model.Currently;
import com.github.h01d.weather.data.remote.model.Data;
import com.github.h01d.weather.util.TimeUtils;
import com.github.h01d.weather.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.DailyViewHolder>
{
    private List<?> data;
    private Context context;

    public MainAdapter(Context context)
    {
        this.context = context;
    }

    public void setData(List<?> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);

        return new MainAdapter.DailyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.DailyViewHolder holder, int position)
    {
        if(data.get(position) instanceof Data)
        {
            final Data item = (Data) data.get(position);

            holder.dateText.setText(TimeUtils.timestampToDay(item.getTime()));
            holder.degreesText.setText((int) item.getTemperatureLow() + "° " + (int) item.getTemperatureHigh() + "°");
            holder.iconText.setText(context.getResources().getIdentifier(item.getIcon().replace("-", "_"), "string", context.getPackageName()));
        }
        else if(data.get(position) instanceof Currently)
        {
            final Currently item = (Currently) data.get(position);

            holder.dateText.setText(TimeUtils.timestampToTime(item.getTime()));
            holder.degreesText.setText((int) item.getTemperature() + "°");
            holder.iconText.setText(context.getResources().getIdentifier(item.getIcon().replace("-", "_"), "string", context.getPackageName()));
        }
    }

    @Override
    public int getItemCount()
    {
        return data == null ? 0 : data.size();
    }

    class DailyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.i_data_date)
        TextView dateText;
        @BindView(R.id.i_data_degrees)
        TextView degreesText;
        @BindView(R.id.i_data_icon)
        TextView iconText;

        DailyViewHolder(View itemView)
        {
            super(itemView);

            ButterKnife.bind(this, itemView);

            iconText.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/weathericons.ttf"));
        }
    }
}
