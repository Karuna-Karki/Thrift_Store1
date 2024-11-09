package edu.pims.thriftstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class PaymentActivity extends AppCompatActivity {

    EditText editAmount;
    Button btnPayment;
    Button cashbtn;  // Add this line

    String clientId = "AesbSIsS7W8_7ApTJ2r8CP60YFVSKNYH1axYj7LGHu4RA1YE52s8o1Ocs4PFetWWkXgiLYr6V469AioW";

    public static PayPalConfiguration configuration;

    // Initialize the launcher to handle PayPal payment result
    private final ActivityResultLauncher<Intent> paymentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    PaymentConfirmation confirm = result.getData().getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirm != null) {
                        // Payment success, handle the confirmation details here
                        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                    }
                } else if (result.getResultCode() == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
                    // Handle invalid payment result
                    Toast.makeText(this, "Invalid Payment", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        editAmount = findViewById(R.id.editamount);
        btnPayment = findViewById(R.id.btnpayment);
        cashbtn = findViewById(R.id.cashbtn); // Initialize the cash button

        configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientId);

        // Start PayPal Service
        Intent serviceIntent = new Intent(this, PayPalService.class);
        serviceIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(serviceIntent);

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment();
            }
        });

        // Set onClickListener for cashbtn to navigate to ThankYouActivity
        cashbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thankYouIntent = new Intent(PaymentActivity.this, ThankyouActivity.class);
                startActivity(thankYouIntent);
            }
        });
    }

    private void getPayment() {
        String amounts = editAmount.getText().toString();

        PayPalPayment payment = new PayPalPayment(new BigDecimal(amounts), "USD", "Your Store Name", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payment);

        // Launch the PayPal payment activity
        paymentActivityResultLauncher.launch(intent);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
