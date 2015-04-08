package com.resoluciones.legajos.activities;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.resoluciones.legajos.R;
import com.resoluciones.legajos.util.ApplicationHelper;
import com.resoluciones.legajos.util.SaveSharedPreference;
import com.squareup.picasso.Picasso;

public class CaratulaActivity extends ActionBarActivity {

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF startPoint = new PointF();
    PointF midPoint = new PointF();
    float oldDist = 1f;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caratula);

        try {
            image = (ImageView) findViewById(R.id.acta_imagen);

            Picasso.with(getApplicationContext()).load(ApplicationHelper.i().getString(ApplicationHelper.API_BASE_URL) + "/Caratula?token=" + SaveSharedPreference.getAccessToken(getApplicationContext()) + "&legajo=" + SaveSharedPreference.getLegajo(getApplicationContext()).getId()
            ).into(image);
            image.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ImageView view = (ImageView) v;
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            savedMatrix.set(matrix);
                            startPoint.set(event.getX(), event.getY());
                            mode = DRAG;
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            oldDist = spacing(event);
                            if (oldDist > 10f) {
                                savedMatrix.set(matrix);
                                midPoint(midPoint, event);
                                mode = ZOOM;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_POINTER_UP:
                            mode = NONE;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (mode == DRAG) {
                                matrix.set(savedMatrix);
                                matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
                            } else if (mode == ZOOM) {
                                float newDist = spacing(event);
                                if (newDist > 10f) {
                                    matrix.set(savedMatrix);
                                    float scale = newDist / oldDist;
                                    matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                                }
                            }
                            break;
                    }
                    view.setImageMatrix(matrix);
                    return true;
                }

                @SuppressLint("FloatMath")
                private float spacing(MotionEvent event) {
                    float x = event.getX(0) - event.getX(1);
                    float y = event.getY(0) - event.getY(1);
                    return FloatMath.sqrt(x * x + y * y);
                }

                private void midPoint(PointF point, MotionEvent event) {
                    float x = event.getX(0) + event.getX(1);
                    float y = event.getY(0) + event.getY(1);
                    point.set(x / 2, y / 2);
                }
            });
        } catch (Exception e) {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
