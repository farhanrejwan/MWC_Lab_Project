package com.example.mwclabproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.log10;

public class PredictPathLoss extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_path_loss);
    }

    public void calculateButton(View view) {
        TextView output = findViewById(R.id.pathLossOutput);
        EditText frequencyInput = findViewById(R.id.carrierFrequencyTextInput);
        EditText transmitterHeightInput = findViewById(R.id.transmitterHeightTextInput);
        EditText receiverHeightInput = findViewById(R.id.receiverHeightTextInput);
        EditText distanceInput = findViewById(R.id.distanceTextInput);

        RadioButton smallMedium = findViewById(R.id.smallMedium);
        RadioButton large = findViewById(R.id.large);
        RadioButton urban = findViewById(R.id.urban);
        RadioButton suburban = findViewById(R.id.suburban);
        RadioButton open = findViewById(R.id.open);

        float frequency = Float.parseFloat(frequencyInput.getText().toString());
        float transmitterHeight = Float.parseFloat(transmitterHeightInput.getText().toString());
        float receiverHeight = Float.parseFloat(receiverHeightInput.getText().toString());
        float distance = Float.parseFloat(distanceInput.getText().toString());
        boolean flag = true;

        if (frequency < 150  ||  frequency > 1500) {
            flag = false;

            Toast.makeText(
                    this, "Carrier frequency should be from 150 MHz to 1500 MHz", Toast.LENGTH_LONG
            ).show();
        }
        if (transmitterHeight < 30  ||  transmitterHeight > 300) {
            flag = false;

            Toast.makeText(
                    this, "Height of transmitting antenna should be from 30m to 300m",
                    Toast.LENGTH_LONG
            ).show();
        }
        if (receiverHeight < 1  ||  receiverHeight > 10) {
            flag = false;

            Toast.makeText(
                    this, "Height of receiving antenna should be from 1m to 10m", Toast.LENGTH_LONG
            ).show();
        }
        if (distance < 1  ||  distance > 20) {
            flag = false;

            Toast.makeText(
                    this, "Propagation distance should be from 1km to 20km", Toast.LENGTH_LONG
            ).show();
        }

        if (flag) {
            float Ahr = 0, Lu, Ls, Lo;
            String text = null;

            if (smallMedium.isChecked()) {
                Ahr = (float) ((1.1 * log10(frequency) - 0.7) * receiverHeight - 1.56 * log10(frequency) + 0.8);
            }
            else if (large.isChecked()) {
                if (frequency <= 300) {
                    Ahr = (float) (8.29 * log10(1.54 * receiverHeight) * log10(1.54 * receiverHeight) - 1.1);
                }
                else {
                    Ahr = (float) (3.2 * log10(11.75 * receiverHeight) * log10(11.75 * receiverHeight) - 4.97);
                }
            }
            else {
                flag = false;

                Toast.makeText(this, "Choose a city size!", Toast.LENGTH_LONG).show();
            }

            if (flag) {
                Lu = (float) (69.55 + 26.16 * log10(frequency) - 13.82 * log10(transmitterHeight) - Ahr
                        + (44.9 - 6.55 * log10(transmitterHeight)) * log10(distance));

                if (urban.isChecked()) {
                    text = "Path loss = " + Lu;
                }
                else if (suburban.isChecked()) {
                    Ls = (float) (Lu - 2 * log10(frequency / 28) * log10(frequency / 28) - 5.4);

                    text = "Path loss = " + Ls;
                }
                else if (open.isChecked()) {
                    Lo = (float) (Lu - 4.78 * log10(frequency) * log10(frequency)
                            - 18.733 * log10(frequency) - 40.98);

                    text = "Path loss = " + Lo;
                }
                else {
                    flag = false;

                    Toast.makeText(this, "Choose an area type!", Toast.LENGTH_LONG).show();
                }

                if (flag) {
                    output.setText(text);
                }
            }
        }
    }
}