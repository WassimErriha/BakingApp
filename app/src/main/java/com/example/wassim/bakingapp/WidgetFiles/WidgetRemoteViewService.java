package com.example.wassim.bakingapp.WidgetFiles;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetRemoteViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ListProvider(this.getApplicationContext(), intent));
    }
}
