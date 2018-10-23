package com.abhi.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import models.Ingredient;
import models.Recipe;
import models.SingletonClass;

import static com.abhi.bakingapp.Constants.RECIPE_CLICKED;

/**
 * Created by ABHI on 10/22/2018.
 */

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context = null;
    private int appWidgetId;
    Recipe recipe;

    List<String> ingredientsInList = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();

    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        int recipeClicked = intent.getIntExtra(RECIPE_CLICKED,0);
        if(SingletonClass.getsInstance().getRecipes() != null)
        recipe = SingletonClass.getsInstance().getRecipes().get(recipeClicked);
        Log.d("AppWidgetId", String.valueOf(appWidgetId));
        //load recipe
    }

    private void updateWidgetListView()
    {
        if(recipe != null){
            ingredients = recipe.getIngredients();
            List<String> ingredientsInList = new ArrayList<>();
            for(Ingredient ingredient :ingredients){
                ingredientsInList.add(ingredient.getIngredient());
            }
            this.ingredientsInList = ingredientsInList;
        }

    }
    @Override
    public void onCreate() {
        updateWidgetListView();
    }

    @Override
    public void onDataSetChanged() {
        updateWidgetListView();
    }

    @Override
    public void onDestroy() {
        ingredientsInList.clear();
    }

    @Override
    public int getCount() {
        return ingredientsInList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d("WidgetCreatingView", "WidgetCreatingView");
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.listview_row_item);

        Log.d("Loading", ingredientsInList.get(position));
        remoteView.setTextViewText(R.id.listview_tv, ingredientsInList.get(position));

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
