package com.abhi.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import static com.abhi.bakingapp.Constants.RECIPE_CLICKED;

public class RecipeWidgetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equalsIgnoreCase(Constants.RECIPE_WIDGET_ACTION_FILTER)){
            int recipeClicked = intent.getExtras().getInt(RECIPE_CLICKED);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName thisWidget = new ComponentName(context.getApplicationContext(), BakingRecipeWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            if (appWidgetIds != null && appWidgetIds.length > 0) {
                for(int widgetId : appWidgetIds){
                    BakingRecipeWidget.updateAppWidget(context, appWidgetManager, widgetId);
                }
            }
        }
    }
}
