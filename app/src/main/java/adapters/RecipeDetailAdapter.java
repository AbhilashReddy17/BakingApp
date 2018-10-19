package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhi.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import models.Recipe;

/**
 * Created by ABHI on 10/18/2018.
 */

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailHolder>{
    Recipe recipe;
    Context context;
    public RecipeDetailAdapter(Context context,Recipe recipe) {
        this.context = context;
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public RecipeDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipedetails_recyclerview_item,parent,false);
        RecipeDetailHolder holder = new RecipeDetailHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailHolder holder, int position) {

        if(position == 0){
            holder.itemText.setText(context.getResources().getString(R.string.firstItem)); // first item always reserved for ingredients
        }else{
            holder.itemText.setText(context.getResources().getString(R.string.notfirstItem,position)
                    +recipe.getSteps().get(position-1).getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        return recipe.getSteps().size()+1; //+1 for the first ingredient item
    }

    public class RecipeDetailHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_text_id)
        TextView itemText;
        View itemView;
        public RecipeDetailHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }
}
}
