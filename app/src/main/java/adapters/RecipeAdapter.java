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
import interfaces.RecipeClickedListener;
import models.Recipe;

/**
 * Created by ABHI on 10/18/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    Context context;
    List<Recipe> recipes;
    RecipeClickedListener callback;
    public RecipeAdapter(Context context, List<Recipe> recipes, RecipeClickedListener callback){
        this.context = context;
        this.recipes = recipes;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_recyclerview_item,parent,false);
        RecipeHolder holder = new RecipeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, final int position) {
        holder.itemText.setText(recipes.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onResponse(position);
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

        @BindView(R.id.item_text_id)
        TextView itemText;
        View itemView;
        public RecipeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
