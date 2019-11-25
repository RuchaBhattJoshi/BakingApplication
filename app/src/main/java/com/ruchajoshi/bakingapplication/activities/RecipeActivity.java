package com.ruchajoshi.bakingapplication.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.ruchajoshi.bakingapplication.api.BakingHelper;
import com.ruchajoshi.bakingapplication.api.BakingService;
import com.ruchajoshi.bakingapplication.utilities.Constant;
import com.ruchajoshi.bakingapplication.utilities.GridAutofitLayoutManager;
import com.ruchajoshi.bakingapplication.R;
import com.ruchajoshi.bakingapplication.models.Recipe;
import com.ruchajoshi.bakingapplication.adapters.RecipeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ruchajoshi.bakingapplication.utilities.Constant.GRID_COLUMN_WIDTH;


public class RecipeActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener {

    private RecipeAdapter mRecipeAdapter;
    private BakingService mBakingService;

    GridAutofitLayoutManager layoutManager;

    @BindView(R.id.recipesList)
    RecyclerView recipesListRecyclerView;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(RecipeActivity.this);
        mBakingService = BakingHelper.getInstance(RecipeActivity.this);
        layoutManager = new GridAutofitLayoutManager(
                this, GRID_COLUMN_WIDTH);
        getAllRecipes();
    }

    private void getAllRecipes() {

        Call<List<Recipe>> call = mBakingService.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mRecipeAdapter = new RecipeAdapter(response.body(),getApplicationContext(),RecipeActivity.this);
                recipesListRecyclerView.setLayoutManager(layoutManager);
                recipesListRecyclerView.setAdapter(mRecipeAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onRecipeItemClicked(Recipe recipe) {
        Bundle b = new Bundle();
        b.putParcelable(Constant.RECIPE, recipe);

        Intent intent = new Intent(RecipeActivity.this, RecipeDetailsActivity.class);
        intent.putExtra(Constant.RECIPE,b);
        startActivity(intent);
    }
}
