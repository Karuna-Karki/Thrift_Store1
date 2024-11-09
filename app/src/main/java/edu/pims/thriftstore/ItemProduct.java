package edu.pims.thriftstore;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import edu.pims.thriftstore.Adapter.ProductAdapter;
import edu.pims.thriftstore.Adapter.Product;

public class ItemProduct extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_product);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);

        // Find the shopping cart icon by ID
        ImageView shoppingCartIcon = findViewById(R.id.imageView3);

        // Set an onClickListener for the cart icon
        shoppingCartIcon.setOnClickListener(v -> {
            // Navigate to CartActivity when the icon is clicked
            Intent intent = new Intent(ItemProduct.this, CartActivity.class);
            startActivity(intent);
        });

        ImageView logoImageView = findViewById(R.id.imageView);

        // Set an OnClickListener for the logo ImageView
        logoImageView.setOnClickListener(v -> {
            // Navigate to MainActivity when the logo is clicked
            Intent intent = new Intent(ItemProduct.this, MainActivity.class);
            startActivity(intent);
        });

        fetchProductData();
    }

    private void fetchProductData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        productList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            productList.add(product);
                        }
                        productAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ItemProduct.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
