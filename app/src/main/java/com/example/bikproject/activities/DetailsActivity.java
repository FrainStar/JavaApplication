package com.example.bikproject.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.example.bikproject.R;
import com.example.bikproject.models.adapters.ProductCart;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.UUID;

import kotlin.Triple;

public class DetailsActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private CollectionReference cartCollection;
    private String name;
    private String price;
//    private String description;
    private Intent firstPageIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        FirebaseApp.initializeApp(DetailsActivity.this);

        Intent intent = getIntent();
        TextView priceView = findViewById(R.id.price);
        TextView nameProductView = findViewById(R.id.nameProduct);
//        TextView descriptionView = findViewById(R.id.description);
        Button addInCartButton = findViewById(R.id.buttonAddInCart);
        firstPageIntent = new Intent(this, MainPageActivity.class);


        if (intent != null) {
            name = intent.getStringExtra("name");
            price = intent.getStringExtra("price");
//            description = intent.getStringExtra("description");
            nameProductView.setText(name);
            priceView.setText(price);
//            descriptionView.setText(description);
        }

        addInCartButton.setOnClickListener(v -> {
            startActivity(firstPageIntent);
            finish();
        });
    }

    private void addInCart(FirebaseUser currentUser) {
        cartCollection = firestore.collection("cart");
        DocumentReference cartRef = cartCollection.document(currentUser.getUid());

        String productId = generateUUID();
        DocumentReference productRef = cartRef.collection("products").document(productId);

        ProductCart product = new ProductCart(name, Double.parseDouble(price));
        productRef.set(product)
                .addOnSuccessListener(aVoid -> createDialog());
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.succec_buy)
                .setPositiveButton(R.string.sign_up_succefull_messasges_ok_button, (dialog, id) ->
                        startActivity(new Intent(DetailsActivity.this, MainPageActivity.class)))
                .create()
                .show();
    }
}
