package edu.pims.thriftstore;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import edu.pims.thriftstore.Adapter.ContactUs;

public class ContactActivity extends AppCompatActivity {

    EditText phoneInput, emailInput, messageInput;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize the input fields
        phoneInput = findViewById(R.id.phoneInput);
        emailInput = findViewById(R.id.emailInput);
        messageInput = findViewById(R.id.messageInput);

        // Set up the button click listener
        findViewById(R.id.sendMessageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    private void sendMessage() {
        String phone = phoneInput.getText().toString();
        String email = emailInput.getText().toString();
        String message = messageInput.getText().toString();

        // Validate input fields
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to Firestore in 'contact_us' collection
        ContactUs contactUs = new ContactUs(phone, email, message);
        db.collection("contact_us")
                .add(contactUs)
                .addOnSuccessListener(documentReference -> {
                    // Firestore operation was successful
                    Toast.makeText(ContactActivity.this, "Message Sent Successfully", Toast.LENGTH_SHORT).show();

                    // Optionally, clear the input fields after submission
                    phoneInput.setText("");
                    emailInput.setText("");
                    messageInput.setText("");
                })
                .addOnFailureListener(e -> {
                    // If Firestore operation fails
                    Toast.makeText(ContactActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
