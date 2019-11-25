package com.ruchajoshi.bakingapplication.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.ruchajoshi.bakingapplication.R;
import com.ruchajoshi.bakingapplication.fragments.StepDetailFragment;
import com.ruchajoshi.bakingapplication.models.Recipe;
import com.ruchajoshi.bakingapplication.models.Step;
import com.ruchajoshi.bakingapplication.utilities.Constant;

import butterknife.ButterKnife;

public class PlayerActivity extends AppCompatActivity {

    private Step mStep;
    private Recipe mRecipe;
    private int mStepIndex;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //ButterKnife.bind(PlayerActivity.this);

        getRecipeAndStepIndex();
        setTitle(mRecipe.getmName());
        showUpButton();

        if (savedInstanceState == null) {

            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            mStep = mRecipe.getmSteps().get(mStepIndex);
            stepDetailFragment.setStep(mStep);
            stepDetailFragment.setStepIndex(mStepIndex);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }

    }

    private void getRecipeAndStepIndex() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Constant.EXTRA_STEP_INDEX)) {
                // Get the correct step index from the intent
                Bundle b = intent.getBundleExtra(Constant.EXTRA_STEP_INDEX);
                mStepIndex = b.getInt(Constant.EXTRA_STEP_INDEX);
            }
            if (intent.hasExtra(Constant.RECIPE)) {
                // Get the recipe from the intent
                Bundle b = intent.getBundleExtra(Constant.RECIPE);
                mRecipe = b.getParcelable(Constant.RECIPE);
            }
        }
    }

    private void showUpButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
