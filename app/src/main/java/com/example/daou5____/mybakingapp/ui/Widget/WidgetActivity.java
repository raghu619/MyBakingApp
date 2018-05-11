package com.example.daou5____.mybakingapp.ui.Widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.daou5____.mybakingapp.R;
import com.example.daou5____.mybakingapp.api.Network;
import com.example.daou5____.mybakingapp.api.OnRequestFinishedListener;
import com.example.daou5____.mybakingapp.data.database.Database;
import com.example.daou5____.mybakingapp.data.models.Ingredients;
import com.example.daou5____.mybakingapp.data.models.Recipes;
import com.example.daou5____.mybakingapp.data.models.Widget;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class WidgetActivity extends Activity implements OnRequestFinishedListener {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    ProgressDialog dialog;
    private ArrayList<Recipes> recipes;
    private Spinner spinner;


    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = WidgetActivity.this;

            int position=spinner.getSelectedItemPosition();
            Widget model=new Widget(recipes.get(position).getName(),
                    (ArrayList<Ingredients>) recipes.get(position).getIngredients());

            Database db=new Database(WidgetActivity.this);
            db.insertItem(model,mAppWidgetId);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RecipeWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public WidgetActivity() {
        super();
    }


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setResult(RESULT_CANCELED);

        setContentView(R.layout.widget_confriguration);

        spinner= (Spinner) findViewById(R.id.spinner);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.show();

        findViewById(R.id.add_widget).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        Network.getRecipes(this);


    }

    @Override
    public void onFailure(String message) {
        dialog.dismiss();
        Toast.makeText(this, "There is a problem try again later ! or make sure you are connected to internet"
                , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Response<List<Recipes>> response) {
        dialog.dismiss();
        recipes = (ArrayList<Recipes>) response.body();

        String[]values= new  String [recipes.size()];
        for(int i=0; i < recipes.size();i++)
        {
            values[i]=recipes.get(i).getName();
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }
}

