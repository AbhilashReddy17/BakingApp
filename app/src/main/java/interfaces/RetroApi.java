package interfaces;

import java.util.List;

import models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ABHI on 10/17/2018.
 */

public interface RetroApi {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
