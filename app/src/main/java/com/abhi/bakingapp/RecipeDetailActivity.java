package com.abhi.bakingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import fragments.IngredientsFragment;
import fragments.RecipeDetailFragment;
import fragments.RecipeStepFragment;
import interfaces.RecipeStepClickedListener;

import static com.abhi.bakingapp.Constants.PLAYER_POSITION;
import static com.abhi.bakingapp.Constants.RECIPE_CLICKED;
import static com.abhi.bakingapp.Constants.RECIPE_STEP_CLICKED;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeStepClickedListener{

    public static final String TAG = RecipeDetailActivity.class.getSimpleName();

    @BindView(R.id.framelayout_recipestep_container)
    @Nullable
    FrameLayout recipeStepFragmentContainer;
    @BindView(R.id.framelayout_container)
    @Nullable
    FrameLayout recipeDetailContainer;


    @BindView(R.id.my_toolbar)
    Toolbar toolbarBackbutton;

    int recipeClicked;
    int recipeStepClicked;
    boolean checkScrenRotation;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity_layout);
        ButterKnife.bind(this);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

        if(savedInstanceState != null){
            checkScrenRotation = true;
            recipeClicked = savedInstanceState.getInt(RECIPE_CLICKED);
            recipeStepClicked = savedInstanceState.getInt(RECIPE_STEP_CLICKED);
        }else{
            checkScrenRotation = false;
        }
         recipeClicked = getIntent().getIntExtra(RECIPE_CLICKED,0);

        toolbarBackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeDetailActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkScrenRotation == false){
            RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.getInstance(recipeClicked);
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_container,recipeDetailFragment).commit();

            if(recipeStepFragmentContainer != null){
                //loading first RecipeDetail object which is ingredient
                if(recipeStepClicked==0){
                    setIngredientsFragment(recipeClicked);
                }else{
                    RecipeStepFragment recipeStepFragment = RecipeStepFragment.getInstance(recipeClicked,recipeStepClicked,0);
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_recipestep_container,recipeStepFragment).commit();
                }

            }

        }else{
            RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.getInstance(recipeClicked);
            long playerPosition = pref.getLong(PLAYER_POSITION,0);
            RecipeStepFragment recipeStepFragment = RecipeStepFragment.getInstance(recipeClicked,recipeStepClicked,playerPosition);

            if(recipeDetailContainer !=null && recipeStepFragmentContainer != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_container,recipeDetailFragment).commit();
                //loading first RecipeDetail object which is ingredient
                if(recipeStepClicked==0){
                    setIngredientsFragment(recipeClicked);
                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_recipestep_container,recipeStepFragment).commit();
                }
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_container,recipeStepFragment).commit();
            }

        }

    }

    private void setIngredientsFragment(int recipeClicked) {
        if(recipeStepFragmentContainer == null){
            IngredientsFragment ingredientsFragment = IngredientsFragment.getInstance(recipeClicked);
            getSupportFragmentManager().beginTransaction().addToBackStack(TAG).replace(R.id.framelayout_container,ingredientsFragment).commit();
        }else{
            IngredientsFragment ingredientsFragment = IngredientsFragment.getInstance(recipeClicked);
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_recipestep_container,ingredientsFragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count>0){
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(RECIPE_CLICKED,recipeClicked);
        outState.putInt(RECIPE_STEP_CLICKED,recipeStepClicked);
        int count = getSupportFragmentManager().getBackStackEntryCount();


//        if(count>0){
//            getSupportFragmentManager().popBackStack();
//        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResponse(int recipePosition, int recipeStepClicked) {
        this.recipeClicked = recipePosition;
        this.recipeStepClicked = recipeStepClicked;
        if(recipeStepClicked == 0){
            //clicked ingredients
            setIngredientsFragment(recipePosition);

        }else{
            //clicked recipe step
            RecipeStepFragment recipeStepFragment = RecipeStepFragment.getInstance(recipePosition,recipeStepClicked,0);
            if(recipeStepFragmentContainer != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_recipestep_container,recipeStepFragment,RecipeStepFragment.TAG).commit();
            }else{
                getSupportFragmentManager().beginTransaction().addToBackStack(TAG).replace(R.id.framelayout_container,recipeStepFragment).commit();
            }

        }
    }

}
