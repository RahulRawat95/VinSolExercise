package com.example.android.vinsolexcersice;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> strings;
    private Adapter adapter;

    private long animationDelay = 1000;
    private int spacing = 10;
    private int spanCount = 4;
    private int height = 85, width = 85;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent() != null) {
            Intent intent = getIntent();
            spacing = intent.getIntExtra("SPACING", 0);
            animationDelay = intent.getIntExtra("SPEED", 100);
            height = width = intent.getIntExtra("SIZE", 0);
        }

        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);

        if (savedInstanceState != null) {
            strings = savedInstanceState.getStringArrayList("STRINGS");
        } else {
            strings = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                strings.add(String.valueOf(i+1));
            }
        }

        adapter = new Adapter(this, strings, new Adapter.Callback() {
            @Override
            public void callback(int pos, String string) {
                strings.remove(pos);
                adapter.notifyItemRemoved(pos);
            }
        }, convertDpToPixel(height), convertDpToPixel(width));

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setRemoveDuration(animationDelay);
        defaultItemAnimator.setMoveDuration(animationDelay);

        recyclerView.setItemAnimator(defaultItemAnimator);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                int row = position / spanCount;
                int column = position % spanCount;
                if (position == -1) {
                    position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
                }

                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public int convertDpToPixel(int dp) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("STRINGS", strings);
    }
}