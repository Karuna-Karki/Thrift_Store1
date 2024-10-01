package edu.pims.thriftstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText emailtext, passwordtext;
    Button loginBtn;
    TextView edittext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        emailtext = findViewById(R.id.lemail);
        passwordtext = findViewById(R.id.lpassword);
        loginBtn = findViewById(R.id.loginbtn);
        edittext = findViewById(R.id.registerpage);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailtext.getText().toString();
                String password = passwordtext.getText().toString();
                loginUser(email, password);
            }
        });
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, register_activity.class);
                startActivity(intent);
            }
        });
    }

    private  void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Success",Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(LoginActivity.this, ItemProduct.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}