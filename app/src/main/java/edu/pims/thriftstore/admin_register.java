package edu.pims.thriftstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class admin_register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    EditText usernameEdit, emailEdit, addressEdit, passwordEdit;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        usernameEdit = findViewById(R.id.username);
        emailEdit = findViewById(R.id.email);
        addressEdit = findViewById(R.id.address);
        passwordEdit = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerbtn);

        registerButton.setOnClickListener(v -> {
            String username = usernameEdit.getText().toString().trim();
            String email = emailEdit.getText().toString().trim();
            String address = addressEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();

            // Validate input fields
            if (username.isEmpty() || address.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.length() < 6) {
                Toast.makeText(admin_register.this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show();
                return;
            }

            registerAdmin(username, address, email, password);
        });
    }

    private void registerAdmin(String username, String address, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        storeAdminData(user.getUid(), username, address, email);
                        Toast.makeText(admin_register.this, "Admin Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(admin_register.this, admin_login.class));
                        finish();
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(admin_register.this, "Registration Failed: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void storeAdminData(String userId, String username, String address, String email) {
        Map<String, Object> admin = new HashMap<>();
        admin.put("Username", username);
        admin.put("Address", address);
        admin.put("Email", email);
        admin.put("isAdmin", true);  // Flag to indicate admin status

        // Save admin data in the "admins" collection
        db.collection("admins").document(userId)
                .set(admin)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(admin_register.this, "Admin Data Stored Successfully", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(admin_register.this, "Failed to store admin data", Toast.LENGTH_SHORT).show()
                );
    }
}
