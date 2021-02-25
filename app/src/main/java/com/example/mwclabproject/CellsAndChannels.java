package com.example.mwclabproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.sqrt;

public class CellsAndChannels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cells_and_channels);
    }

    public void calculateButton(View view) {
        TextView output = findViewById(R.id.cellsAndChannelsOutput);
        EditText areaInput = findViewById(R.id.areaTextInput);
        EditText radiusInput = findViewById(R.id.radiusTextInput);
        EditText frequencyInput = findViewById(R.id.frequencyTextInput);
        EditText factorInput = findViewById(R.id.factorTextInput);

        RadioButton macrocell = findViewById(R.id.macrocell);
        RadioButton microcell = findViewById(R.id.microcell);

        if (macrocell.isChecked() || microcell.isChecked()) {
            float area = Float.parseFloat(areaInput.getText().toString());
            float radius = Float.parseFloat(radiusInput.getText().toString());
            float frequency = Float.parseFloat(frequencyInput.getText().toString());
            float factor = Float.parseFloat(factorInput.getText().toString());

            int i, j, k = 0;
            boolean flag = false;

            while (k * k <= factor) {
                k++;
            }

            for (i = 0, j = k - i; i <= k && j >= 0; i++, j--) {
                if (factor == i * i + j * j + i * j) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                float number_of_cells = (float) (area / (1.5 * sqrt(3) * radius * radius));
                float number_of_channels = frequency / factor;
                float channel_capacity_and_concurrent_calls = number_of_cells * number_of_channels;

                String text = "Number of cells required = " + number_of_cells + "\n"
                        + "Number of channels per cell = " + number_of_channels + "\n"
                        + "Total channel capacity = " + channel_capacity_and_concurrent_calls + "\n"
                        + "Total number of possible concurrent calls = " + channel_capacity_and_concurrent_calls;

                output.setText(text);
            } else {
                Toast.makeText(
                        this, "Value of Frequency reuse factor is invalid!", Toast.LENGTH_LONG
                ).show();
            }
        }
        else {
            Toast.makeText(this, "Select a cell type!", Toast.LENGTH_LONG).show();
        }
    }
}