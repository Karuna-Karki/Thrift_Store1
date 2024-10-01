package edu.pims.thriftstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import Adapter.ProductAdapter;

public class ItemProduct extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_product);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button0.setOnClickListener(view -> {
            Intent intent = new Intent(ItemProduct.this, CartActivity.class);
            startActivity(intent);
        });

        button1.setOnClickListener(view -> {
            Intent intent = new Intent(ItemProduct.this, CartActivity.class);
            startActivity(intent);
        });

        button2.setOnClickListener(view -> {
            Intent intent = new Intent(ItemProduct.this, CartActivity.class);
            startActivity(intent);
        });
    }

}
