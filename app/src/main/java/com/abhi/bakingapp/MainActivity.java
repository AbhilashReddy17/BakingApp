package com.abhi.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import adapters.RecipeAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.ResponseListener;
import models.SingletonClass;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.recipe_reclerview_id) RecyclerView recyclerView;
    @BindView(R.id.main_activity_progressbar_id)
    ProgressBar progressBar;
    RecipeAdapter recipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //checking for the internet connectivity
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            AppUtils.getsInstance().makeRecipes(new ResponseListener() {
                @Override
                public void onResponse() {
                    progressBar.setVisibility(View.GONE);
                    setRecyclerViewAdapter();
                }
            });
        }else{
            Toast.makeText(this, "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
        }


    }

    public void setRecyclerViewAdapter(){
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recipeAdapter = new RecipeAdapter(this, SingletonClass.getsInstance().getRecipes());
        recyclerView.setAdapter(recipeAdapter);
    }
}
