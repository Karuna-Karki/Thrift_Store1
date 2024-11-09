package edu.pims.thriftstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class admin_login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    EditText emailText, passwordText;
    Button loginBtn;
    TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailText = findViewById(R.id.lemail);
        passwordText = findViewById(R.id.lpassword);
        loginBtn = findViewById(R.id.loginbtn);
        registerText = findViewById(R.id.registerpage);

        loginBtn.setOnClickListener(v -> {
            String email = emailText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();
            loginAdmin(email, password);
        });

        registerText.setOnClickListener(v -> {
            Intent intent = new Intent(admin_login.this, admin_register.class);
            startActivity(intent);
        });
    }

    private void loginAdmin(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(admin_login.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Try to sign in with Firebase Auth
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        // Access the "admins" collection to verify if this user is an admin
                        db.collection("admins").document(userId).get()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        DocumentSnapshot document = task1.getResult();
                                        if (document.exists() && Boolean.TRUE.equals(document.getBoolean("isAdmin"))) {
                                            // User is an admin, allow login
                                            Toast.makeText(admin_login.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(admin_login.this, admin_dashboard.class));
                                            finish();
                                        } else {
                                            // User is not an admin
                                            Toast.makeText(admin_login.this, "Not an Admin Account", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Failed to retrieve admin data
                                        Toast.makeText(admin_login.this, "Failed to retrieve admin data", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // Authentication failed
                        Toast.makeText(admin_login.this, "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
