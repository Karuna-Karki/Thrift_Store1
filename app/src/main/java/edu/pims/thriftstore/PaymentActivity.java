package edu.pims.thriftstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        // Initialize the Pay button
        payButton = findViewById(R.id.payButton);

        // Set up the button click listener
        payButton.setOnClickListener(v -> {
            // Navigate to ThankyouActivity using an Intent
            Intent intent = new Intent(PaymentActivity.this, ThankyouActivity.class);
            startActivity(intent);
        });
    }
}
