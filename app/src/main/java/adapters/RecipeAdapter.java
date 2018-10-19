package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhi.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import models.Recipe;

/**
 * Created by ABHI on 10/18/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    Context context;
    List<Recipe> recipes;
    public RecipeAdapter(Context context,List<Recipe> recipes){
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_card_layout,parent,false);
        RecipeHolder holder = new RecipeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        holder.recipeTitle.setText(recipes.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(recipes != null)
        return recipes.size();
        return 0;
    }

    public class RecipeHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.recipe_title_id)
        TextView recipeTitle;
        View itemView;
        public RecipeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
