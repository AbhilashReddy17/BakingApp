package models;

import com.abhi.bakingapp.AppUtils;

import java.util.List;

/**
 * Created by ABHI on 10/18/2018.
 */

public class SingletonClass {
    public static SingletonClass sInstance ;
    List<Recipe> recipes;

    public static SingletonClass getsInstance(){
        if(sInstance == null) sInstance = new SingletonClass();
        return sInstance;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
