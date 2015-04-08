package com.resoluciones.legajos.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.resoluciones.legajos.R;
import com.resoluciones.legajos.dialogs.ResolucionDialog;
import com.resoluciones.legajos.fragments.CardBackFragment;
import com.resoluciones.legajos.fragments.CardFrontFragment;
import com.resoluciones.legajos.model.Acta;
import com.resoluciones.legajos.model.Infraccion;

public class ActaActivity extends ActionBarActivity implements FragmentManager.OnBackStackChangedListener, ResolucionDialog.ResolucionCallback {

    private Handler mHandler = new Handler();
    private Acta acta;
    private Infraccion infraccion;
    private boolean mShowingBack = false;
    private String tag;
    private ResolucionDialog resolucionDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acta);

        tag = getIntent().getStringExtra("TAG");
        resolucionDialog = new ResolucionDialog(this, this);
        acta = resolucionDialog.getActaForTag(tag);
        infraccion = resolucionDialog.getInfraccionForTag(tag);

        if (savedInstanceState == null) {
            CardFrontFragment cardFront = new CardFrontFragment();
            cardFront.setActa(acta);
            getFragmentManager()
                .beginTransaction()
                .add(R.id.container, cardFront)
                .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }
        getFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.add(Menu.NONE, R.id.action_flip, Menu.NONE,
                mShowingBack ? R.string.action_photo : R.string.action_info);
        item.setIcon(mShowingBack ? R.drawable.ic_action_photo : R.drawable.ic_action_info);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, LegajoActivity.class));
                return true;
            case R.id.action_flip:
                flipCard();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        mShowingBack = true;

        CardBackFragment cardBack = new CardBackFragment();
        cardBack.setActa(acta);
        cardBack.setInfraccion(infraccion);

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.container, cardBack)
                .addToBackStack(null)
                .commit();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();
            }
        });

    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        invalidateOptionsMenu();
    }

    public void resolverInfraccion(View view) {
        view.setTag(tag);
        resolucionDialog.getDialogForView(view).show();
    }

    public void onRadioButtonClicked(View view) {
        resolucionDialog.onRadioButtonClicked(view);
    }

    @Override
    public void callbackReturn() {
        startActivity(new Intent(this, LegajoActivity.class));
    }
}
