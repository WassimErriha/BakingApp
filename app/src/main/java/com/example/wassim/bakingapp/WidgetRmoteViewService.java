package com.example.wassim.bakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetRmoteViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return (new ListProvider(this.getApplicationContext(), intent));
    }
}
