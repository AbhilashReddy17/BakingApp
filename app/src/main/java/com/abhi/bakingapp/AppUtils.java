package com.abhi.bakingapp;

import android.content.Context;
import android.util.Log;

import java.util.List;

import interfaces.ResponseListener;
import interfaces.RetroApi;
import models.Recipe;
import models.SingletonClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ABHI on 10/18/2018.
 */

public class AppUtils {
    public static final String TAG = AppUtils.class.getSimpleName();

    public static  AppUtils sInstance ;

    public static AppUtils getsInstance(){
        if(sInstance == null) sInstance = new AppUtils();
        return sInstance;
    }
    public void makeRecipes(final ResponseListener callback){
        RetroApi apiInterface = Controller.getClient().create(RetroApi.class);
        apiInterface.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: recived the Recipe");
                    List<Recipe> recipes = response.body();
                   SingletonClass.getsInstance().setRecipes(recipes);
                   callback.onResponse();
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "onFailure: grabbing Recipe");

            }
        });
    }

    public int getRecipeImage(String resName, Context context){

       String drawableName = resName.toLowerCase(); //coverting all uppercase letters to lower case letters
        drawableName =  drawableName.replaceAll(" ","_"); //replacing spaces with the _ as drawable folder name doesnt have spaces in our project we saved spaces with _
        return  context.getResources().getIdentifier(drawableName,"drawable", context.getPackageName());

    }

}
