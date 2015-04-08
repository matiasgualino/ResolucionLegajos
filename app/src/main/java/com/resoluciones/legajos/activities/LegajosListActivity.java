package com.resoluciones.legajos.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.reflect.TypeToken;
import com.resoluciones.legajos.R;
import com.resoluciones.legajos.adapters.LegajoAdapter;
import com.resoluciones.legajos.core.ActaConsumer;
import com.resoluciones.legajos.model.Legajo;
import com.resoluciones.legajos.util.ApplicationHelper;
import com.resoluciones.legajos.util.JsonUtil;
import com.resoluciones.legajos.util.SaveSharedPreference;

import java.lang.reflect.Type;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LegajosListActivity extends ActionBarActivity {

    private Activity mActivity;
    RecyclerView recList;
    LegajoAdapter la;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legajos_list);
        mActivity = this;

        recList = (RecyclerView) findViewById(R.id.legajo_cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        loadLegajosList();
    }


    void loadLegajosList() {
        Type listType = new TypeToken<List<Legajo>>(){}.getType();
        List<Legajo> legajosList = (List<Legajo>) JsonUtil.getInstance().fromJson(getIntent().getStringExtra("legajos"), listType);
        la = new LegajoAdapter(legajosList);
        recList.setAdapter(la);
    }

    public void seleccionarLegajo(View view) {
        ActaConsumer consumer = new ActaConsumer();
        consumer.getLegajo(view.getTag().toString(), new Callback<Legajo>() {
            @Override
            public void success(Legajo legajo, Response response) {
                SaveSharedPreference.saveLegajo(mActivity, legajo);
                ApplicationHelper.i().setLegajo(legajo);
                startActivity(new Intent(mActivity, LegajoActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
                new MaterialDialog.Builder(mActivity)
                        .title("ERROR")
                        .content(error.getMessage())
                        .positiveText("OK")
                        .show();
            }
        });
    }
}
