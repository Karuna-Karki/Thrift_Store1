package edu.pims.thriftstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class ThankyouActivity extends AppCompatActivity {

    private Button continueShoppingButton, donateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thankyou);

        // Initialize buttons
        continueShoppingButton = findViewById(R.id.continueShoppingButton);
        donateButton = findViewById(R.id.donateButton);

        // Set click listener for Continue Shopping button
        continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ItemProduct page
                Intent intent = new Intent(ThankyouActivity.this, ItemProduct.class);
                startActivity(intent);
            }
        });

        // Set click listener for Donate button
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Contact page
                Intent intent = new Intent(ThankyouActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        ImageView logoImageView = findViewById(R.id.imageView);

        // Set an OnClickListener for the logo ImageView
        logoImageView.setOnClickListener(v -> {
            // Navigate to MainActivity when the logo is clicked
            Intent intent = new Intent(ThankyouActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
