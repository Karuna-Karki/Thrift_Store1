package edu.pims.thriftstore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.pims.thriftstore.CartActivity;
import edu.pims.thriftstore.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;
    private FirebaseFirestore db;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.db = FirebaseFirestore.getInstance();  // Initialize Firestore instance
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Load product image using Glide
        Glide.with(context)
                .load(product.getImageUrl())
                .into(holder.productImage);

        // Set product details
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText(product.getPrice());

        // Add to cart button click event
        holder.addToCartButton.setOnClickListener(v -> {
            String quantityStr = holder.productQuantity.getText().toString();
            if (!quantityStr.isEmpty()) {
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(product.getPrice().replace("Rs. ", ""));
                double total = price * quantity;

                // Prepare data to store in Firestore
                Map<String, Object> cartItem = new HashMap<>();
                cartItem.put("name", product.getName());
                cartItem.put("quantity", quantity);
                cartItem.put("price", price);
                cartItem.put("total", total);

                // Add item to Firestore under "Cart" collection
                db.collection("Cart")
                        .add(cartItem)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(context, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, CartActivity.class));
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Failed to add item to cart", Toast.LENGTH_SHORT).show();
                        });

            } else {
                Toast.makeText(context, "Please enter quantity", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, productDescription, productPrice;
        EditText productQuantity;
        Button addToCartButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage1);
            productName = itemView.findViewById(R.id.productName1);
            productDescription = itemView.findViewById(R.id.productDescription1);
            productPrice = itemView.findViewById(R.id.productPrice1);
            productQuantity = itemView.findViewById(R.id.productQuantity1);
            addToCartButton = itemView.findViewById(R.id.button1);  // Button ID referenced here
        }
    }
}
