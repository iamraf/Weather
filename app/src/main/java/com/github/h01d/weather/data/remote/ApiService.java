package com.github.h01d.weather.data.remote;

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

import com.github.h01d.weather.data.remote.model.Forecast;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService
{
    @GET("forecast/{key}/{latitude},{longitude}")
    Flowable<Forecast> getForecast(@Path("key") String key,
                                   @Path("latitude") double latitude,
                                   @Path("longitude") double longitude,
                                   @Query("lang") String language,
                                   @Query("units") String units,
                                   @Query("exclude") String exclude);
}