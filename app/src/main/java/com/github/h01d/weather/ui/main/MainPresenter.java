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

import com.github.h01d.weather.data.local.preference.PreferencesManager;
import com.github.h01d.weather.data.remote.ApiClient;
import com.github.h01d.weather.util.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter
{
    private MainView mainView;

    private CompositeDisposable disposable;

    public MainPresenter(MainView mainView)
    {
        this.mainView = mainView;

        disposable = new CompositeDisposable();
    }

    public void loadWeather()
    {
        mainView.isLoading(true);

        if(PreferencesManager.getSavedLocation() != null)
        {
            disposable.add(ApiClient.getClient().getForecast(Constants.API_KEY, PreferencesManager.getSavedLocation().getLatitude(), PreferencesManager.getSavedLocation().getLongitude(), PreferencesManager.getLanguage(), PreferencesManager.getUnits(), "minutely, alerts")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(forecast ->
                    {
                        mainView.isLoading(false);
                        mainView.updateUI(forecast);
                        mainView.updateTitle(PreferencesManager.getSavedLocation().getName() + ", " + PreferencesManager.getSavedLocation().getCountry());
                    }, throwable ->
                    {
                        mainView.isLoading(false);
                        mainView.showError("Could not load weather");
                    }));
        }
        else
        {
            mainView.isLoading(false);
            mainView.updateTitle("Weather");
            mainView.showError("Please select or add a location");
        }
    }

    public void dispose()
    {
        disposable.dispose();
    }
}
