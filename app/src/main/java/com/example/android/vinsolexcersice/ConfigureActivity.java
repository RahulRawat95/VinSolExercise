package com.example.android.vinsolexcersice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class ConfigureActivity extends AppCompatActivity {

    EditText speedEt, sizeEt, spacingEt;
    FloatingActionButton nextFab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        speedEt = findViewById(R.id.speed_et);
        sizeEt = findViewById(R.id.size_et);
        spacingEt = findViewById(R.id.spacing_et);
        nextFab = findViewById(R.id.fab);

        nextFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int speed, size, spacing;
                try {
                    speed = Integer.parseInt(speedEt.getText().toString());
                } catch (Exception e) {
                    speed = 100;
                }

                try {
                    size = Integer.parseInt(sizeEt.getText().toString());
                } catch (Exception e) {
                    size = 80;
                }

                try {
                    spacing = Integer.parseInt(spacingEt.getText().toString());
                } catch (Exception e) {
                    spacing = 0;
                }

                Intent intent = new Intent(ConfigureActivity.this, MainActivity.class);
                intent.putExtra("SPEED", speed);
                intent.putExtra("SPACING", spacing);
                intent.putExtra("SIZE", size);
                startActivity(intent);
            }
        });
    }
}