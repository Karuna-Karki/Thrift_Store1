package edu.pims.thriftstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register_activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    EditText usernameedit, emailedit, addressedit, passwordedit;
    Button registerbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        usernameedit = findViewById(R.id.username);
        emailedit = findViewById(R.id.email);
        addressedit = findViewById(R.id.address);
        passwordedit = findViewById(R.id.password);
        registerbutton = findViewById(R.id.registerbtn);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameedit.getText().toString().trim();
                String email = emailedit.getText().toString().trim();
                String address = addressedit.getText().toString().trim();
                String password = passwordedit.getText().toString().trim();


                if (username.isEmpty()) {
                    Toast.makeText(register_activity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (address.isEmpty()) {
                    Toast.makeText(register_activity.this, "Please enter your address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(register_activity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty() || password.length() < 6) {
                    Toast.makeText(register_activity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If all checks pass, register the user
                registerUser(username, address,  email, password);
            }
        });
    }

    private  void registerUser(String username, String address, String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,task -> {
                    if(task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        storeUserData(user.getUid(), username, address, email);
                        Toast.makeText(register_activity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(register_activity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Log.w("RegisterActivity","createUserWithEmail:Failure",task.getException());
                        Toast.makeText(register_activity.this, "Failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void storeUserData(String userId, String username, String address, String email){
        Map<String, Object> user = new HashMap<>();
        user.put("Username",username);
        user.put("Address",address);
        user.put("Email",email);

        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid ->{
                    Log.d("Register","User data successfully done");
                    Toast.makeText(register_activity.this,"Register Sucessfull",Toast.LENGTH_SHORT).show();
                });
    }
}