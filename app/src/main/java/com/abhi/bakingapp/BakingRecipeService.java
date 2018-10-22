package com.abhi.bakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ABHI on 10/22/2018.
 */

public class BakingRecipeService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetRemoteViewsFactory(this.getApplicationContext(), intent));
    }
}
