package edu.pims.thriftstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class CartActivity extends AppCompatActivity {

    private TableLayout cartTable;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize views
        cartTable = findViewById(R.id.cartTable);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Fetch and display cart items from Firestore
        fetchCartItems();

        // Handle the checkout button click
        checkoutButton.setOnClickListener(v -> proceedToCheckout());
    }

    private void fetchCartItems() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Cart")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve each item's details
                            String name = document.getString("name");
                            int quantity = document.getLong("quantity").intValue();
                            double price = document.getDouble("price");
                            double total = price * quantity;

                            // Add each cart item to the table layout
                            addCartItemToTable(name, quantity, price, total);
                        }
                    } else {
                        Toast.makeText(CartActivity.this, "Failed to load cart items", Toast.LENGTH_SHORT).show();
                    }
                });

        ImageView logoImageView = findViewById(R.id.imageView);

        // Set an OnClickListener for the logo ImageView
        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MainActivity when the logo is clicked
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addCartItemToTable(String name, int quantity, double price, double total) {
        // Inflate a new table row for each item
        LayoutInflater inflater = LayoutInflater.from(this);
        TableRow row = (TableRow) inflater.inflate(R.layout.cart_item_row, cartTable, false);

        // Set item data in the row
        ((TextView) row.findViewById(R.id.itemName)).setText(name);
        ((TextView) row.findViewById(R.id.itemQuantity)).setText(String.valueOf(quantity));
        ((TextView) row.findViewById(R.id.itemPrice)).setText("Rs. " + price);
        ((TextView) row.findViewById(R.id.itemTotal)).setText("Rs. " + total);

        // Add the row to the cart table
        cartTable.addView(row);
    }


    private void proceedToCheckout() {
        // Proceed to payment or checkout activity
        Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
        startActivity(intent);
    }




}
