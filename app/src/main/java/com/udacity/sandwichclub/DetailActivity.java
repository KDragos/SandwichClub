package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.List;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView akaTV = findViewById(R.id.also_known_tv);
        akaTV.setText(GetStringFromList(sandwich.getAlsoKnownAs()));

        TextView ingredientTV = findViewById(R.id.ingredients_tv);
        ingredientTV.setText(GetStringFromList(sandwich.getIngredients()));

        TextView originTV = findViewById(R.id.origin_tv);
        originTV.setText(sandwich.getPlaceOfOrigin());

        TextView ingredientsTV = findViewById(R.id.description_tv);
        ingredientsTV.setText(sandwich.getDescription());
    }

    private String GetStringFromList(List<String> input) {
        String output = "";
        for(int i = 0; i < input.size(); i++ ) {
            output += input.get(i);
            if(i < input.size() - 1) {
                output += ", ";
            }
        }
        return output;

    }
}
