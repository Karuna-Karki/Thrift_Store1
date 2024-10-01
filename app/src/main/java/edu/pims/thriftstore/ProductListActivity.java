package edu.pims.thriftstore;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import Adapter.ProductAdapter;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productRecyclerView = findViewById(R.id.productRecyclerView);

        // Initialize product list
        productList = new ArrayList<>();
        productList.add(new Product("Product 1", "Description 1", "$10.99", R.drawable.product1));
        productList.add(new Product("Product 2", "Description 2", "$15.99", R.drawable.product2));
        // Add more products as needed

        // Set up RecyclerView with adapter
        productAdapter = new ProductAdapter(this, productList);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        productRecyclerView.setAdapter(productAdapter);
    }
}
