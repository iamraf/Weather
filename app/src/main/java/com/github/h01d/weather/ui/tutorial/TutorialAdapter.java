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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.h01d.weather.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorialAdapter extends SliderViewAdapter<TutorialAdapter.TutorialViewHolder>
{
    public TutorialAdapter()
    {

    }

    @Override
    public TutorialViewHolder onCreateViewHolder(ViewGroup parent)
    {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutorial_item, parent, false);

        return new TutorialViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(TutorialViewHolder viewHolder, int position)
    {
        switch(position)
        {
            case 1:
                viewHolder.image.setImageResource(R.drawable.tutorial1);
                break;
            case 2:
                viewHolder.image.setImageResource(R.drawable.tutorial2);
                break;
            case 3:
                viewHolder.image.setImageResource(R.drawable.tutorial3);
                break;
            case 4:
                viewHolder.image.setImageResource(R.drawable.tutorial4);
                break;
            case 5:
                viewHolder.image.setImageResource(R.drawable.tutorial5);
                break;
            case 6:
                viewHolder.image.setImageResource(R.drawable.tutorial6);
                break;
            case 7:
                viewHolder.image.setImageResource(R.drawable.tutorial7);
                break;
            default:
                viewHolder.image.setImageResource(R.drawable.tutorial0);
                break;
        }
    }

    @Override
    public int getCount()
    {
        return 8;
    }

    class TutorialViewHolder extends SliderViewAdapter.ViewHolder
    {
        @BindView(R.id.l_tutorial_image)
        ImageView image;

        TutorialViewHolder(View itemView)
        {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}